package com.bitbucket.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import netscape.javascript.JSException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bitbucket.domain.GitRepositoryDO;
import com.bitbucket.ds.GitRepositoryDAO;
import com.bitbucket.util.Constants;
import com.bitbucket.util.SFoAuthHandle;
import com.domain.EnvironmentDO;
import com.domain.EnvironmentInformationDO;
import com.domain.RepositoryClient;
import com.domain.RepositoryServer;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.ds.salesforce.dao.comp.EnvironmentInformationDAO;
import com.ds.salesforce.dao.comp.RepositoryClientDAO;
import com.ds.salesforce.dao.comp.RepositoryServerDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.services.component.FDGetSFoAuthHandleService;

/**
 * Servlet implementation class OAuthServlet
 */
@WebServlet(name = "OAuthServletBitBucket", urlPatterns = {
		"/OAuthServletBitBucket/*", "/OAuthServletBitBucket" }, initParams = {
		// clientId is 'Consumer Key' in the Remote Access UI
		@WebInitParam(name = "clientId", value = "9nevKHn7BRTGL3zkCQ"),
		// clientSecret is 'Consumer Secret' in the Remote Access UI
		@WebInitParam(name = "clientSecret", value = "f6ZNukEbJxXbLdMhrabQvXgtEdgeLvSf"),
		// This must be identical to 'Callback URL' in the Remote Access UI
		@WebInitParam(name = "redirectUri", value = "https://onforcerm.herokuapp.com/OAuthServletBitBucket/url"),
		@WebInitParam(name = "environment", value = "https://bitbucket.org"), })
public class OAuthServletBitBucket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	SFoAuthHandle sfHandle = null;

	private static final Logger LOG = LoggerFactory
			.getLogger(OAuthServletBitBucket.class);

	private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	private static final String INSTANCE_URL = "INSTANCE_URL";

	private String clientId = null;
	private String clientSecret = null;
	private String redirectUri = null;
	private String environment = null;
	private String authUrl = null;
	private String tokenUrl = null;
	private String authorizationCode = null;
	private String id = null;
	private String repositortId = null;
	private String orgId = null;
	private String homeURL = null;
	private String OrgToken = null;
	private String orgRefreshToken = null;
	private String type = null;
	private String typeRepositoryorenvironment = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OAuthServletBitBucket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		clientId = this.getInitParameter("clientId");
		clientSecret = this.getInitParameter("clientSecret");
		redirectUri = this.getInitParameter("redirectUri");
		environment = this.getInitParameter("environment");

		try {
			authUrl = environment + "/site/oauth2/authorize?" + "&client_id="
					+ clientId + "&response_type=code";
		} catch (Exception e) {
			LOG.error("Error while creating AuthURL: {} ", e.getMessage());
			throw new ServletException("Error while creating AuthURL:", e);
		}
		tokenUrl = "https://bitbucket.org/site/oauth2/access_token";
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	public void getuserDetails(HttpServletRequest request, String refreshToken,
			String state) throws ServletException, IOException {

		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		HttpSession session = request.getSession();
		String accessToken = (String) session.getAttribute("ACCESS_TOKEN");

		HttpClient httpclient = new HttpClient();
		GetMethod get = new GetMethod("https://api.bitbucket.org/1.0/user");
		// set the token in the header
		get.setRequestHeader("Authorization", "OAuth " + accessToken);

		// set the SOQL as a query param
		NameValuePair[] params = new NameValuePair[1];

		params[0] = new NameValuePair("access_token", accessToken);
		get.setQueryString(params);
		System.out.println("URL---" + get.getURI().toString());
		try {
			httpclient.executeMethod(get);
			System.out.println(" status - " + get.getStatusCode());
			if (get.getStatusCode() == HttpStatus.SC_OK) {
				// Now lets use the standard java json classes to work with the
				// results
				try {
					JSONObject jsonResponse = new JSONObject(
							new JSONTokener(new InputStreamReader(
									get.getResponseBodyAsStream())));

					System.out.println("Json Response"
							+ jsonResponse.toString());
					String username = jsonResponse.getJSONObject("user")
							.getString("username");

					System.out.println("getType()" + getType());

					// connecting To Base and Getting Session ID Through SOAP
					// API
					String destinationOrgId = null;
					String serverURL = null;
					String token = null;
					String refreshToken1 = null;
					EnvironmentDO envDo1 = null;

					if (getType().equals("cr")) {
						System.out.println("In Client Repository Oauth");

						EnvironmentInformationDO envBaseClientDO = getSFHandleParamsFromStateParamforClient(request
								.getParameter("state"));
						com.util.SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
								.getSFoAuthHandle(envBaseClientDO,
										Constants.CustomBaseOrgID);

						RepositoryClientDAO repositoryClientDAO = new RepositoryClientDAO();
						List<Object> repositoryList = repositoryClientDAO
								.findById(getRepositortId(), sfBaseHandle);

						for (Iterator iterator = repositoryList.iterator(); iterator
								.hasNext();) {
							RepositoryClient reClient = (RepositoryClient) iterator
									.next();
							reClient.setUsername(username);
							reClient.setAccessToken(accessToken);
							reClient.setRefreshToken(refreshToken);
							repositoryClientDAO.updateBitUsernameandPassword(
									reClient, sfBaseHandle);

						}

					}

					if (getType().equals("ce")) {
						System.out
								.println("In Client Environment Oauth BitBucket");

						EnvironmentInformationDO envBaseClientDO = getSFHandleParamsFromStateParamforClient(request
								.getParameter("state"));
						com.util.SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
								.getSFoAuthHandle(envBaseClientDO,
										Constants.CustomBaseOrgID);
						EnvironmentInformationDAO environmentDAO = new EnvironmentInformationDAO();
						List<Object> envDestinationList = environmentDAO
								.findByEnvId(getRepositortId(), sfBaseHandle);
						EnvironmentInformationDO envDo2 = null;
						for (Iterator iterator = envDestinationList.iterator(); iterator
								.hasNext();) {

							System.out.println("In environment List");
							envDo2 = (EnvironmentInformationDO) iterator.next();

							envDo2.setBitBucketUsername(username);
							envDo2.setBitBucketAccessToken(accessToken);
							envDo2.setBitBucketRefreshToken(refreshToken);
							environmentDAO.updateBitUsernameandPassword(envDo2,
									sfBaseHandle);
							

						}

					} else {
						EnvironmentDO envBaseDO = getSFHandleParamsFromStateParam(request
								.getParameter("state"));
						com.util.SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
								.getSFoAuthHandle(envBaseDO,
										Constants.BaseOrgID);

						RepositoryServerDAO repositoryServerDAO = new RepositoryServerDAO();
						List<Object> repositoryList = repositoryServerDAO
								.findById(getRepositortId(), sfBaseHandle);

						for (Iterator iterator = repositoryList.iterator(); iterator
								.hasNext();) {
							RepositoryServer reServer = (RepositoryServer) iterator
									.next();
							reServer.setUsername(username);
							reServer.setAccessToken(accessToken);
							reServer.setRefreshToken(refreshToken);
							repositoryServerDAO.updateBitUsernameandPassword(
									reServer, sfBaseHandle);

						}
					}
					GitRepositoryDO gitRepositoryDO = new GitRepositoryDO(
							username, "", username, accessToken, refreshToken,
							getRepositortId(), destinationOrgId, serverURL,
							token, refreshToken1);

					GitRepositoryDAO gitRepositoryDAO = new GitRepositoryDAO();
					sfHandle = new SFoAuthHandle(Constants.USERID,
							Constants.PASSWORD, Constants.Server_URL, "");
					String[] ids = new String[1];

					List<Object> list = gitRepositoryDAO.findByGitUsername(
							username, sfHandle);
					if (list.size() > 0) {
						for (Iterator iterator = list.iterator(); iterator
								.hasNext();) {
							GitRepositoryDO object = (GitRepositoryDO) iterator
									.next();
							ids[0] = object.getId();
							gitRepositoryDAO.deleteRecords(ids, sfHandle);

						}
						gitRepositoryDAO.insert(gitRepositoryDO, sfHandle);

					} else {

						gitRepositoryDAO.insert(gitRepositoryDO, sfHandle);
					}

				} catch (JSONException e) {
					LOG.error(
							"Error while getting JSONObject from the records {} ",
							e.getMessage());
					throw new ServletException(
							"Error while getting JSONObject from the records: ",
							e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EnvironmentDO getSFHandleParamsFromStateParam(String stateParam) {
		EnvironmentDO envDO = null;
		String delim = "|";
		StringTokenizer st = new StringTokenizer(stateParam, delim);
		if (st.hasMoreTokens()) {
			String env = st.nextToken();
			if (env.equals("L")) {
				setEnvironment(Constants.LoginEnv);
			} else if (env.equals("T")) {
				setEnvironment(Constants.TestEnv);
			}
			String repoId = st.nextToken();
			setRepositortId(repoId);
			String orgId = st.nextToken();
			String serverURL = st.nextToken();
			String token = st.nextToken();
			String refreshToken = st.nextToken();
			try {
				envDO = new EnvironmentDO(orgId, token, serverURL, "",
						refreshToken);
			} catch (Exception e) {
				System.out
						.println("setting to get connetion from invalid connection paramsin oauth...."
								+ e.getMessage());
				throw new SFException("Not a valid connection paramters",
						SFErrorCodes.SF_Not_Valid_Conn_Parameters);

			}
			setHomeURL(serverURL);
		}
		return envDO;
	}

	public EnvironmentInformationDO getSFHandleParamsFromStateParamforClient(
			String stateParam) {
		EnvironmentInformationDO envDO = null;
		String delim = "|";
		StringTokenizer st = new StringTokenizer(stateParam, delim);
		if (st.hasMoreTokens()) {
			String env = st.nextToken();
			if (env.equals("L")) {
				setEnvironment(Constants.LoginEnv);
			} else if (env.equals("T")) {
				setEnvironment(Constants.TestEnv);
			}
			String repositoyId = st.nextToken();
			setRepositortId(repositoyId);
			String orgId = st.nextToken();
			String serverURL = st.nextToken();
			String token = st.nextToken();
			String refreshToken = st.nextToken();
			try {
				envDO = new EnvironmentInformationDO(orgId, token, serverURL,
						"", refreshToken, "");
			} catch (Exception e) {
				System.out
						.println("setting to get connetion from invalid connection paramsin oauth...."
								+ e.getMessage());
				throw new SFException("Not a valid connection paramters",
						SFErrorCodes.SF_Not_Valid_Conn_Parameters);

			}
			setHomeURL(serverURL);
		}
		return envDO;
	}

	public void processStateParam(String stateParam) throws Exception {
		// String state =
		// "L|a0Wj0000003lq2PEAQ|00Dj0000001tsUfEAI|https://na16.salesforce.com|00Dj0000001tsUf!AR8AQKissf9QT5fSIjY4m7HItzF7TJCT73YOATort7iNtA_K_jMmp_xavqKHSwVqfcbauqnmh5fsTTCWM4MKPbe.KEbB4b_a";
		String delim = "|";
		StringTokenizer st = new StringTokenizer(stateParam, delim);
		if (st.hasMoreTokens()) {
			String env = st.nextToken();
			if (env.equals("L")) {
				setEnvironment(Constants.LoginEnv);
			} else if (env.equals("T")) {
				setEnvironment(Constants.TestEnv);
			}
			String repoId = st.nextToken();
			setRepositortId(repoId);
			String orgId = st.nextToken();
			setOrgId(orgId);
			String instanceURL = st.nextToken();
			String token = st.nextToken();

			setOrgToken(token);
			String refreshToken = st.nextToken();
			setOrgRefreshToken(refreshToken);
			setHomeURL(refreshToken);

			String type = st.nextToken();
			setType(type);
			

			System.out.println(env + "~" + repoId + "~" + orgId + "~"
					+ instanceURL + "~" + token + "~" + refreshToken );
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		// Send the user to authorize
		// response.sendRedirect(authUrl);
		if (request.getRequestURI().endsWith("OAuthServletBitBucket")) {
			// Send the user to authorize
			response.sendRedirect(authUrl);
			return;
		} else {

			String code = request.getParameter("code");
			String state = request.getParameter("state");

			System.out.println("State Param..." + state);
			try {
				processStateParam(request.getParameter("state"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			authorizationCode = code;
			System.out.println("Auth successful, got Authorization code: {} "
					+ code);
			System.out.println("Client ID: {} " + clientId);
			tokenUrl = "https://bitbucket.org/site/oauth2/access_token";

			HttpClient httpclient = new HttpClient();
			PostMethod post = new PostMethod(tokenUrl);
			post.addParameter("code", code);
			post.addParameter("grant_type", "authorization_code");
			post.addParameter("client_id", clientId);
			post.addParameter("client_secret", clientSecret);
			// post.addParameter("redirect_uri", redirectUri);

			try {
				httpclient.executeMethod(post);
				try {
					JSONObject authResponse = new JSONObject(new JSONTokener(
							new InputStreamReader(
									post.getResponseBodyAsStream())));

					String accessToken = authResponse.getString("access_token");
					String refreshToken = authResponse
							.getString("refresh_token");

					System.out.println("AccessToken..........." + accessToken);
					System.out
							.println("refreshtoken..........." + refreshToken);
					System.out.println("userid..........." + Constants.USERID);
					System.out.println("password..........."
							+ Constants.PASSWORD);
					System.out.println("serverurl..........."
							+ Constants.Server_URL);

					request.getSession()
							.setAttribute(ACCESS_TOKEN, accessToken);

					// We also get the instance URL from the OAuth response, so
					// set it
					// in the session too
					request.getSession().setAttribute("AuthorizationCode",
							authorizationCode);

					getuserDetails(request, refreshToken, state);

					// connecting To Base and Getting Session ID Through SOAP
					// API
					if (getType().equals("ce") || getType().equals("cr")) {

						EnvironmentInformationDO envBaseDO = getSFHandleParamsFromStateParamforClient(request
								.getParameter("state"));
						com.util.SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
								.getSFoAuthHandle(envBaseDO,
										Constants.CustomBaseOrgID);
						String ParentsessionId = sfBaseHandle
								.getEnterpriseConnection().getSessionHeader()
								.getSessionId();
						String homeURL = envBaseDO.getServerURL();

						String str1 = homeURL + "/" + Constants.jspURL
								+ "?sid=" + ParentsessionId + "&retURL=/"
								+ getRepositortId();

						response.sendRedirect(str1);

					} else {
						EnvironmentDO envBaseDO = getSFHandleParamsFromStateParam(request
								.getParameter("state"));
						com.util.SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
								.getSFoAuthHandle(envBaseDO,
										Constants.BaseOrgID);
						String ParentsessionId = sfBaseHandle
								.getEnterpriseConnection().getSessionHeader()
								.getSessionId();
						String homeURL = envBaseDO.getServerURL();

						String str1 = homeURL + "/" + Constants.jspURL
								+ "?sid=" + ParentsessionId + "&retURL=/"
								+ getRepositortId();

						response.sendRedirect(str1);
					}

				} catch (JSException js) {
					js.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public SFoAuthHandle getSfHandle() {
		return sfHandle;
	}

	public void setSfHandle(SFoAuthHandle sfHandle) {
		this.sfHandle = sfHandle;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getAuthUrl() {
		return authUrl;
	}

	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepositortId() {
		return repositortId;
	}

	public void setRepositortId(String repositortId) {
		this.repositortId = repositortId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getHomeURL() {
		return homeURL;
	}

	public void setHomeURL(String homeURL) {
		this.homeURL = homeURL;
	}

	public String getOrgToken() {
		return OrgToken;
	}

	public void setOrgToken(String orgToken) {
		OrgToken = orgToken;
	}

	public String getOrgRefreshToken() {
		return orgRefreshToken;
	}

	public void setOrgRefreshToken(String orgRefreshToken) {
		this.orgRefreshToken = orgRefreshToken;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLog() {
		return LOG;
	}

	public static String getAccessToken() {
		return ACCESS_TOKEN;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeRepositoryorenvironment() {
		return typeRepositoryorenvironment;
	}

	public void setTypeRepositoryorenvironment(
			String typeRepositoryorenvironment) {
		this.typeRepositoryorenvironment = typeRepositoryorenvironment;
	}

}
