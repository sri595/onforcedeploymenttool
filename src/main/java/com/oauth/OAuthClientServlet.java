package com.oauth;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domain.CustomerMasterDetails;
import com.domain.EnvironmentInformationDO;
import com.ds.salesforce.dao.comp.CustomerMasterDetailsDAO;
import com.ds.salesforce.dao.comp.EnvironmentInformationDAO;
import com.services.component.FDGetSFoAuthHandleService;
import com.util.Constants;
import com.util.SFoAuthHandle;
import com.util.oauth.AuthAccessDO;
import com.util.oauth.AuthUserInfoDO;

/**
 * Servlet parameters
 */

@WebServlet(name = "OAuthClientServlet", urlPatterns = { "/OAuthClientServlet/*", "/OAuthClientServlet" })
public class OAuthClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(OAuthClientServlet.class);

	private String clientId = null;
	private String clientSecret = null;
	private String redirectUri = null;
	private String environment = null;
	private String tokenUrl = null;
	private String authorizationCode = null;
	private String idURL = null;
	private String homeURL = null;
	private String envId = null;
	private String baseToken = null;
	private String sfClientAuthType;
	private EnvironmentInformationDO baseEnvDO;
	private AuthAccessDO authAccessDO;
	private AuthUserInfoDO authUserInfoDO;
	private EnvironmentInformationDO envTargetDO;
	EnvironmentInformationDO envBaseDO;

	public void init() throws ServletException {
		/*
		 * clientId = this.getInitParameter("clientId"); clientSecret =
		 * this.getInitParameter("clientSecret"); redirectUri =
		 * this.getInitParameter("redirectUri"); environment =
		 * this.getInitParameter("environment");
		 */

		Properties p = new Properties();

		try {
			p.load(getServletContext().getResourceAsStream("/WEB-INF/properties/config.properties"));

		} catch (Exception e) {
			// TODO: handle exception
		}

		clientId = p.getProperty("nonbaseclientclientId");
		clientSecret = p.getProperty("nonbaseclientclientSecret");
		redirectUri = p.getProperty("nonbaseclientredirectUri");
		environment = p.getProperty("nonbaseclientenvironment");

	}

	private PostMethod getPostRequest(HttpServletRequest request) {
		try {
			tokenUrl = getEnvironment() + "/services/oauth2/token";
			System.out.println("token URL : " + tokenUrl);
			String code = request.getParameter("code");
			authorizationCode = code;
			System.out.println("Auth successful, got Authorization code: {} " + code);

			PostMethod post = new PostMethod(tokenUrl);
			post.addParameter("code", code);
			post.addParameter("grant_type", "authorization_code");
			post.addParameter("client_id", clientId);
			post.addParameter("client_secret", clientSecret);
			post.addParameter("redirect_uri", redirectUri);
			return post;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void processPostRequest(PostMethod post) {
		try {

			Properties p = new Properties();

			try {
				p.load(getServletContext().getResourceAsStream("/WEB-INF/properties/config.properties"));

			} catch (Exception e) {
				// TODO: handle exception
			}
			
			HttpClient httpclient = new HttpClient();


			/*	HttpConnectionManager conManager = httpclient.getHttpConnectionManager();
				httpclient.getHostConfiguration().setProxy(p.getProperty("quotaguardserverurl"), 9293);
				HttpState state = new HttpState();
				state.setProxyCredentials(null, null,
						new UsernamePasswordCredentials(p.getProperty("quotaguardusername"), p.getProperty("quotaguardpassword")));
				httpclient.setState(state);*/

			

			httpclient.executeMethod(post);
			JSONObject authResponse = new JSONObject(
					new JSONTokener(new InputStreamReader(post.getResponseBodyAsStream())));
			String accessToken = authResponse.getString("access_token");
			String instanceUrl = authResponse.getString("instance_url");
			String refreshToken = authResponse.getString("refresh_token");
			setAuthAccessDO(accessToken, refreshToken, instanceUrl);
			System.out.println("refreshToken....." + refreshToken);
			setIdURL(authResponse.getString("id"));
			System.out.println("idURL---------" + getIdURL());
			LOG.info("Auth Response: {} ", authResponse.toString(2));
		} catch (JSONException e) {
			LOG.error("Error while getting JSONObject from AuthResponse: {} ", e.getMessage());
		} catch (Exception e) {
			LOG.error("Error while Oauth with Salesforce: {} ", e.getMessage());
		} finally {
			post.releaseConnection();
		}
	}

	private void setAuthAccessDO(String accessToken, String refreshToken, String instanceUrl) {
		AuthAccessDO authAccessDO = new AuthAccessDO(accessToken, refreshToken, instanceUrl);
		setAuthAccessDO(authAccessDO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		System.out.println(" State value: " + request.getParameter("state"));
		//System.out.println(" Session Id value: " + request.getParameter("sid"));
		String sessionId=request.getParameter("sid");

		try {
			processStateParam(request.getParameter("state"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// prepare and get post request
		PostMethod post = getPostRequest(request);
		// process post request
		try {

			processPostRequest(post);
			System.out.println(" After ProcessPost Request: ");

			getUserDetails(request, response);
			System.out.println(" After getUserDetails Request: ");

			updateEnvs(request, response);
			System.out.println(" After updateEnvs Request: ");

			System.out.println("Final Context Path: " + request.getContextPath());
			EnvironmentInformationDO envDO = null;
			String str1 = "";

			if (getSfClientAuthType().equals(Constants.SFClientSelfAuthType)) {
				envDO = getEnvTargetDO();
				SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService.getSFoAuthHandle(envDO,
						Constants.CustomBaseOrgID);
				String ParentsessionId = sfBaseHandle.getEnterpriseConnection().getSessionHeader().getSessionId();
				String homeURL = envDO.getServerURL();

				str1 = homeURL + "/" + Constants.jspURL + "?sid=" + ParentsessionId + "&retURL=/" + getEnvId();
			} else if (getSfClientAuthType().equals(Constants.SFClientServerAuthType)) {
				System.out.println("Server");
				// envDO = getEnvTargetDO();
				EnvironmentInformationDO envBaseDO = processStateParam1(request.getParameter("state"));

				// connecting To Base and Getting Session ID Through SOAP API
				SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService.getSFoAuthHandle(envBaseDO,
						Constants.CustomBaseOrgID);

				// connecting To Base and Getting Session ID Through SOAP API
				SFoAuthHandle sfBaseHandle1 = fdGetSFoAuthHandleService.getSFoAuthHandle(envBaseDO,
						Constants.CustomBaseOrgID);
				String ParentsessionId = sfBaseHandle1.getEnterpriseConnection().getSessionHeader().getSessionId();
				String homeURL = envBaseDO.getServerURL();

				str1 = homeURL + "/" + Constants.jspURL + "?sid=" + ParentsessionId + "&retURL=/" + getEnvId();
			}

			response.sendRedirect(str1);

		} catch (Exception e) {
			response.sendRedirect("/errorClient.jsp");
		}
	}

	public void getUserDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("session id in getUserDetails.." + request.getSession());

		HttpSession session = request.getSession();
		

		String id = (String) session.getAttribute("id");
		
		System.out.println(" id.." + id);

		String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
		
		System.out.println("ACCESS_TOKEN.." + accessToken);

		String instanceURL = (String) request.getSession().getAttribute(Constants.INSTANCE_URL);
		
		System.out.println(" instanceurl.." + instanceURL);

		HttpClient httpclient = new HttpClient();
		GetMethod get = new GetMethod(idURL);
		// set the token in the header

		get.setRequestHeader("Authorization", "OAuth " + getAuthAccessDO().getAccessToken());

		// set the SOQL as a query param
		NameValuePair[] params = new NameValuePair[1];

		params[0] = new NameValuePair("oauth_token", getAuthAccessDO().getAccessToken());
		get.setQueryString(params);
		// System.out.println("Accessing ID URL---" + get.getURI().toString());
		try {
			httpclient.executeMethod(get);
			System.out.println(" status - " + get.getStatusCode());
			if (get.getStatusCode() == HttpStatus.SC_OK) {
				// Now lets use the standard java json classes to work with the
				// results
				try {
					JSONObject jsonResponse = new JSONObject(
							new JSONTokener(new InputStreamReader(get.getResponseBodyAsStream())));
					System.out.println("Auth Response: {} " + jsonResponse.toString(2));

					String userName = new String(
							(String) jsonResponse.get("username"));
					String orgId = new String(
							(String) jsonResponse.get("organization_id"));
					String userId = new String(
							(String) jsonResponse.get("user_id"));
					String display_name = new String(
							(String) jsonResponse.get("display_name"));
					String email = new String(
							(String) jsonResponse.get("email"));

					AuthUserInfoDO authUserInfoDO = new AuthUserInfoDO(
							userName, userId, orgId, display_name, email);
					setAuthUserInfoDO(authUserInfoDO);

				} catch (JSONException e) {
					e.printStackTrace();
					LOG.error("Error while getting JSONObject from the records {} ", e.getMessage());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateEnvs(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		try {

			// getTargetEnvDO
			EnvironmentInformationDO envTargetDO = new EnvironmentInformationDO(getEnvId(),
					getAuthUserInfoDO().getOrgId() + "_" + getAuthUserInfoDO().getUserId(),
					getAuthUserInfoDO().getUserName(), getAuthUserInfoDO().getOrgId(),
					getAuthAccessDO().getAccessToken(), getAuthAccessDO().getInstanceUrl(),
					getAuthAccessDO().getRefreshToken(), "");

			setEnvTargetDO(envTargetDO);

			// if the base connection exists
			if (getSfClientAuthType().equals(Constants.SFClientSelfAuthType)) {

				System.out.println("Environment ID...." + getEnvId());
				System.out.println(
						"OrgIDplusUserId...." + getAuthUserInfoDO().getOrgId() + "_" + getAuthUserInfoDO().getUserId());
				System.out.println("Username..... " + getAuthUserInfoDO().getUserName());
				System.out.println("Org ID......" + getAuthUserInfoDO().getOrgId());
				System.out.println("Acess Token....... " + getAuthAccessDO().getAccessToken());
				System.out.println("Instance URL...... " + getAuthAccessDO().getInstanceUrl());
				System.out.println("RefreshToken...... " + getAuthAccessDO().getRefreshToken());
				// prepare the base connection
				System.out.println("self.....");
				
				
				Calendar enddate = Calendar.getInstance();
				enddate.add(Calendar.DATE, 90);

				Calendar startdate = Calendar.getInstance();
				startdate.setTime(new Date());

				String active_features = "QuickDeploy;GetPackages;Commit;Validate;ValidateAll;Deploy;DeployAll;Retrieve";

				// create customer Master Record By Using Details of JSON
				// Response
				CustomerMasterDetails customerMasterDetails = new CustomerMasterDetails(
						getAuthUserInfoDO().getOrgId(), getAuthUserInfoDO()
								.getDisplayName(), getAuthUserInfoDO()
								.getEmail(), "", "", true, startdate, enddate,
						active_features, null, null);
				SFoAuthHandle sfcusHandle = new SFoAuthHandle(Constants.USERID,
						Constants.PASSWORD, Constants.Server_URL, "");
				CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();
				customerMasterDetailsDAO.insert(customerMasterDetails,
						sfcusHandle);
				
				SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService.getSFoAuthHandle(envTargetDO,
						Constants.CustomBaseOrgID);
				System.out.println(sfBaseHandle.toString());
				// update the environment table
				updateEnvInformation(sfBaseHandle, request, envTargetDO);
			} else if (getSfClientAuthType().equals(Constants.SFClientServerAuthType)) {
				// prepare the base connection
				EnvironmentInformationDO envBaseDO = getBaseEnvDO();
				SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService.getSFoAuthHandle(envBaseDO,
						Constants.CustomBaseOrgID);
				// update the environment table
				updateEnvInformation(sfBaseHandle, request, envTargetDO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Error while getting JSONObject from the records {} ", e.getMessage());
			throw new ServletException("Error while getting JSONObject from the records: ", e);
		}

	}

	public void updateEnvInformation(SFoAuthHandle sfBaseHandle, HttpServletRequest request,
			EnvironmentInformationDO envDO) {
		if (sfBaseHandle != null) {

			System.out
					.println("session Id: " + sfBaseHandle.getEnterpriseConnection().getSessionHeader().getSessionId());
			saveTokens(envDO, sfBaseHandle);

		} else {
			System.out.println("Base Env is not working");
		}
	}

	public void preparePostReq1(HttpServletRequest request, String url) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
		String instanceURL = (String) request.getSession().getAttribute(Constants.INSTANCE_URL);
		HttpClient httpclient = new HttpClient();
		System.out.println("rediurecting URL: " + url);
		GetMethod get = new GetMethod(url);
		get.addRequestHeader("Authorization", accessToken);
		// set the token in the header
		// get.setRequestHeader("Authorization", "OAuth " + accessToken);

		// set the SOQL as a query param
		NameValuePair[] params = new NameValuePair[1];

		params[0] = new NameValuePair("oauth_token", accessToken);
		get.setQueryString(params);
		// System.out.println("Accessing ID URL---" + get.getURI().toString());
		try {
			httpclient.executeMethod(get);
			System.out.println(" rediurecting status - " + get.getStatusCode());
			if (get.getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("great Work");
			} else {
				System.out.println("Bad Work");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void preparePostReq(HttpServletRequest request) {
		try {
			processStateParam(request.getParameter("state"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tokenUrl = getEnvironment() + "/services/oauth2/token";
		String code = request.getParameter("code");
		authorizationCode = code;
		System.out.println("Auth successful, got Authorization code: {} " + code);
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(tokenUrl);
		post.addParameter("code", code);
		post.addParameter("grant_type", "authorization_code");
		post.addParameter("client_id", clientId);
		post.addParameter("client_secret", clientSecret);
		post.addParameter("redirect_uri", redirectUri);
	}

	private void saveTokens(EnvironmentInformationDO envDO, SFoAuthHandle sfBaseHandle) {
		EnvironmentInformationDAO envDAO = new EnvironmentInformationDAO();
		envDAO.update(envDO, sfBaseHandle);
	}

	/**
	 * 
	 * @param Passing
	 *            state Parameter which consists of BaseOrg Details
	 */
	public void processStateParam(String stateParam) throws Exception {
		// String state =
		// "self|L|a0Wj0000003lq2PEAQ|00Dj0000001tsUfEAI|https://na16.salesforce.com|00Dj0000001tsUf!AR8AQKissf9QT5fSIjY4m7HItzF7TJCT73YOATort7iNtA_K_jMmp_xavqKHSwVqfcbauqnmh5fsTTCWM4MKPbe.KEbB4b_a";
		EnvironmentInformationDO envDO = null;
		String delim = "|";
		StringTokenizer st = new StringTokenizer(stateParam, delim);
		boolean isclientAuthServerFlag = false;
		try {
			if (st.hasMoreTokens()) {
				String envAuthType = st.nextToken();
				if (envAuthType.equals(Constants.SFClientSelfAuthType)) {
					setSfClientAuthType(envAuthType);
					String env = st.nextToken();
					if (env.equals("L")) {
						setEnvironment(Constants.LoginEnv);
					} else if (env.equals("T")) {
						setEnvironment(Constants.TestEnv);
					}
					String envId = st.nextToken();
					setEnvId(envId);
					isclientAuthServerFlag = true;
				} else if (envAuthType.equals(Constants.SFClientServerAuthType)) {
					setSfClientAuthType(envAuthType);
					String env = st.nextToken();
					if (env.equals("L")) {
						setEnvironment(Constants.LoginEnv);
					} else if (env.equals("T")) {
						setEnvironment(Constants.TestEnv);
					}
					String envId = st.nextToken();
					setEnvId(envId);
					isclientAuthServerFlag = false;
				} else {
					isclientAuthServerFlag = false;
				}
			}
		} catch (Exception e) {
			isclientAuthServerFlag = false;
			e.printStackTrace();
		}
		if (!isclientAuthServerFlag) {
			String orgId = st.nextToken();
			String instanceURL = st.nextToken();
			String serverURL = instanceURL;
			String token = st.nextToken();
			setBaseToken(token);
			String refreshToken = st.nextToken();
			setHomeURL(instanceURL);

			envDO = new EnvironmentInformationDO(orgId, token, serverURL, "", refreshToken, "");
			setBaseEnvDO(envDO);
			System.out.println("sssss" + envDO.toString());
		}
	}

	public EnvironmentInformationDO processStateParam1(String stateParam) {
		// String state =
		// "self|L|a0Wj0000003lq2PEAQ|00Dj0000001tsUfEAI|https://na16.salesforce.com|00Dj0000001tsUf!AR8AQKissf9QT5fSIjY4m7HItzF7TJCT73YOATort7iNtA_K_jMmp_xavqKHSwVqfcbauqnmh5fsTTCWM4MKPbe.KEbB4b_a";
		EnvironmentInformationDO envDO = null;
		String delim = "|";
		StringTokenizer st = new StringTokenizer(stateParam, delim);
		boolean isclientAuthServerFlag = false;
		try {
			if (st.hasMoreTokens()) {
				String envAuthType = st.nextToken();
				if (envAuthType.equals(Constants.SFClientSelfAuthType)) {
					setSfClientAuthType(envAuthType);
					String env = st.nextToken();
					if (env.equals("L")) {
						setEnvironment(Constants.LoginEnv);
					} else if (env.equals("T")) {
						setEnvironment(Constants.TestEnv);
					}
					String envId = st.nextToken();
					setEnvId(envId);
					isclientAuthServerFlag = true;
				} else if (envAuthType.equals(Constants.SFClientServerAuthType)) {
					setSfClientAuthType(envAuthType);
					String env = st.nextToken();
					if (env.equals("L")) {
						setEnvironment(Constants.LoginEnv);
					} else if (env.equals("T")) {
						setEnvironment(Constants.TestEnv);
					}
					String envId = st.nextToken();
					setEnvId(envId);
					isclientAuthServerFlag = false;
				} else {
					isclientAuthServerFlag = false;
				}
			}
		} catch (Exception e) {
			isclientAuthServerFlag = false;
			e.printStackTrace();
		}
		if (!isclientAuthServerFlag) {
			String orgId = st.nextToken();
			String instanceURL = st.nextToken();
			String serverURL = instanceURL;
			String token = st.nextToken();
			setBaseToken(token);
			String refreshToken = st.nextToken();
			setHomeURL(instanceURL);

			envDO = new EnvironmentInformationDO(orgId, token, serverURL, "", refreshToken, "");

		}
		return envDO;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getHomeURL() {
		return homeURL;
	}

	public void setHomeURL(String homeURL) {
		this.homeURL = homeURL;
	}

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

	public String getBaseToken() {
		return baseToken;
	}

	public void setBaseToken(String baseToken) {
		this.baseToken = baseToken;
	}

	public String getSfClientAuthType() {
		return sfClientAuthType;
	}

	public void setSfClientAuthType(String sfClientAuthType) {
		this.sfClientAuthType = sfClientAuthType;
	}

	public EnvironmentInformationDO getBaseEnvDO() {
		return baseEnvDO;
	}

	public void setBaseEnvDO(EnvironmentInformationDO baseEnvDO) {
		this.baseEnvDO = baseEnvDO;
	}

	public AuthAccessDO getAuthAccessDO() {
		return authAccessDO;
	}

	public void setAuthAccessDO(AuthAccessDO authAccessDO) {
		this.authAccessDO = authAccessDO;
	}

	public String getIdURL() {
		return idURL;
	}

	public void setIdURL(String idURL) {
		this.idURL = idURL;
	}

	public AuthUserInfoDO getAuthUserInfoDO() {
		return authUserInfoDO;
	}

	public void setAuthUserInfoDO(AuthUserInfoDO authUserInfoDO) {
		this.authUserInfoDO = authUserInfoDO;
	}

	public EnvironmentInformationDO getEnvTargetDO() {
		return envTargetDO;
	}

	public void setEnvTargetDO(EnvironmentInformationDO envTargetDO) {
		this.envTargetDO = envTargetDO;
	}

	public EnvironmentInformationDO getEnvBaseDO() {
		return envBaseDO;
	}

	public void setEnvBaseDO(EnvironmentInformationDO envBaseDO) {
		this.envBaseDO = envBaseDO;
	}

}