package com.oauth;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
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

import com.domain.EnvironmentDO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.services.component.FDGetSFoAuthHandleService;
import com.util.Constants;
import com.util.SFoAuthHandle;

/**
 * Servlet parameters
 */

@WebServlet(name = "OAuthServlet", urlPatterns = { "/OAuthServlet/*",
		"/OAuthServlet" })
public class OAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(OAuthServlet.class);

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
	private String sessionId = null;

	public void init() throws ServletException {
		/*
		 * clientId = this.getInitParameter("clientId"); clientSecret =
		 * this.getInitParameter("clientSecret"); redirectUri =
		 * this.getInitParameter("redirectUri"); environment =
		 * this.getInitParameter("environment");
		 */

		Properties p = new Properties();

		try {
			p.load(getServletContext().getResourceAsStream(
					"/WEB-INF/properties/config.properties"));

		} catch (Exception e) {
			// TODO: handle exception
		}

		clientId = p.getProperty("nonbaseclientId");
		clientSecret = p.getProperty("nonbaseclientSecret");
		redirectUri = p.getProperty("nonbaseredirectUri");
		environment = p.getProperty("nonbaseenvironment");

	}

	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String accessToken = null;
		String refreshToken = null;
		String instanceUrl = null;
		PostMethod post = null;
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		Enumeration<String> en = request.getParameterNames();

		while (en.hasMoreElements()) {
			Object objOri = en.nextElement();
			String param = (String) objOri;
			String value = request.getParameter(param);
			System.out.println("Parameter Name is '" + param
					+ "' and Parameter Value is '" + value + "'");
		}
		System.out.println(" State value: " + request.getParameter("state"));
		try {
			processStateParam(request.getParameter("state"));
			tokenUrl = getEnvironment() + "/services/oauth2/token";
			// tokenUrl="https://test.salesforce.com/services/oauth2/token";
			// tokenUrl="https://login.salesforce.com/services/oauth2/token";
			System.out.println("token URL : " + tokenUrl);
			String code = request.getParameter("code");
			authorizationCode = code;
			System.out.println("Auth successful, got Authorization code: {} "
					+ code);
			HttpClient httpclient = new HttpClient();
			System.out.println("TOken URLs :" + tokenUrl);

			post = new PostMethod(tokenUrl);
			post.addParameter("code", code);
			post.addParameter("grant_type", "authorization_code");
			post.addParameter("client_id", clientId);
			post.addParameter("client_secret", clientSecret);
			post.addParameter("redirect_uri", redirectUri);

			Properties p = new Properties();

			try {
				p.load(getServletContext().getResourceAsStream(
						"/WEB-INF/properties/config.properties"));

			} catch (Exception e) {
				// TODO: handle exception
			}

			HttpConnectionManager conManager = httpclient
					.getHttpConnectionManager();
			httpclient.getHostConfiguration().setProxy(
					p.getProperty("quotaguardserverurl"), 9293);
			HttpState state = new HttpState();
			state.setProxyCredentials(
					null,
					null,
					new UsernamePasswordCredentials(p
							.getProperty("quotaguardusername"), p
							.getProperty("quotaguardpassword")));
			httpclient.setState(state);

			httpclient.executeMethod(post);
			JSONObject authResponse = new JSONObject(new JSONTokener(
					new InputStreamReader(post.getResponseBodyAsStream())));
			System.out.println(authResponse.toString());
			accessToken = authResponse.getString("access_token");
			instanceUrl = authResponse.getString("instance_url");
			refreshToken = authResponse.getString("refresh_token");
			System.out.println("refreshToken....." + refreshToken);
			idURL = authResponse.getString("id");
			System.out.println("idURL---------" + idURL);
			request.getSession().setAttribute(Constants.ACCESS_TOKEN,
					accessToken);
			request.getSession().setAttribute(Constants.REFRESH_TOKEN,
					refreshToken);

			request.getSession().setAttribute(Constants.INSTANCE_URL,
					instanceUrl);
			System.out.println("URL --"
					+ (String) request.getSession().getAttribute(
							Constants.INSTANCE_URL));

			// We also get the instance URL from the OAuth response, so set it
			// in the session too

			request.getSession().setAttribute("AuthorizationCode",
					authorizationCode);
			request.getSession().setAttribute("idURL", idURL);

			getIdDetails(request, response);
			System.out.println("Final Context Path: "
					+ request.getContextPath());

			EnvironmentDO envBaseDO = getSFHandleParamsFromStateParam(request
					.getParameter("state"));
			// connecting To Base and Getting Session ID Through SOAP API
			SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
					.getSFoAuthHandle(envBaseDO, Constants.BaseOrgID);
			String ParentsessionId = sfBaseHandle.getEnterpriseConnection()
					.getSessionHeader().getSessionId();
			String homeURL = envBaseDO.getServerURL();

			String str1 = homeURL + "/" + Constants.jspURL + "?sid="
					+ ParentsessionId + "&retURL=/" + getEnvId();

			response.sendRedirect(str1);

			LOG.info("Auth Response: {} ", authResponse.toString(2));
		} catch (JSONException e) {
			LOG.error("Error while getting JSONObject from AuthResponse: {} ",
					e.getMessage());
			if (e.getMessage().contains("access_token")) {

				response.sendRedirect("/error.jsp");
			} else {
				throw new ServletException(
						"Error while getting JSONObject from AuthResponse: {} ",
						e);

			}

		} catch (Exception e) {
			LOG.error("Error while Oauth with Salesforce: {} ", e.getMessage());

			System.out.println("Connection Problem Bae AOuth");
			/*
			 * throw new ServletException("Error while Oauth with Salesforce:  "
			 * , e);
			 */

			response.sendRedirect("/error1.jsp");

		} finally {
			// post.releaseConnection();
		}

		// Set a session attribute so that other servlets can get the access
		// token
	}

	public void getIdDetails(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		HttpSession session = request.getSession();

		String id = (String) session.getAttribute("id");
		String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
		String instanceURL = (String) request.getSession().getAttribute(
				Constants.INSTANCE_URL);
		HttpClient httpclient = new HttpClient();
		GetMethod get = new GetMethod(idURL);
		// set the token in the header

		get.setRequestHeader("Authorization", "OAuth " + accessToken);

		// set the SOQL as a query param
		NameValuePair[] params = new NameValuePair[1];

		params[0] = new NameValuePair("oauth_token", accessToken);
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
							new JSONTokener(new InputStreamReader(
									get.getResponseBodyAsStream())));
					System.out.println("Auth Response: {} "
							+ jsonResponse.toString(2));

					String userName = new String(
							(String) jsonResponse.get("username"));
					String orgId = new String(
							(String) jsonResponse.get("organization_id"));
					String userId = new String(
							(String) jsonResponse.get("user_id"));

					String baseConnString = request.getParameter("state");
					System.out.println("baseConnection : " + baseConnString);
					System.out.println("Current Connection : " + orgId + "~"
							+ accessToken + "~" + instanceURL);
					EnvironmentDO envBaseDO = getSFHandleParamsFromStateParam(request
							.getParameter("state"));

					SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
							.getSFoAuthHandle(envBaseDO, Constants.BaseOrgID);

					String envId = getEnvId();
					String homeURL = getHomeURL();

					if (sfBaseHandle != null) {

						EnvironmentDO envDO = new EnvironmentDO(envId, orgId
								+ "_" + userId, userName, orgId,
								(String) session.getAttribute("ACCESS_TOKEN"),
								(String) session
										.getAttribute(Constants.INSTANCE_URL),
								(String) session.getAttribute("REFRESH_TOKEN"));
						System.out.println("------"
								+ (String) request.getSession().getAttribute(
										Constants.INSTANCE_URL));
						System.out.println("session Id: "
								+ sfBaseHandle.getEnterpriseConnection()
										.getSessionHeader().getSessionId());

						System.out.println("Home_URL: " + homeURL);
						session.setAttribute("Home_URL", homeURL);
						session.setAttribute("Env_Id", envId);
						saveTokens(envDO, sfBaseHandle);
						fdGetSFoAuthHandleService.setSfHandleToNUll();
						if (sfBaseHandle != null) {
							sfBaseHandle.nullify();
						}
						sfBaseHandle = null;
					} else {
						System.out.println("Base Env is not working");
					}
				} catch (JSONException e) {
					// e.printStackTrace();
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

	public void preparePostReq1(HttpServletRequest request, String url) {

		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
		String instanceURL = (String) request.getSession().getAttribute(
				Constants.INSTANCE_URL);
		HttpClient httpclient = new HttpClient();
		System.out.println("redirecting URL: " + url);
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

	public void preparePostReq(HttpServletRequest request) throws Exception {
		processStateParam(request.getParameter("state"));
		tokenUrl = getEnvironment() + "/services/oauth2/token";
		String code = request.getParameter("code");
		authorizationCode = code;
		System.out.println("Auth successful, got Authorization code: {} "
				+ code);
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(tokenUrl);
		post.addParameter("code", code);
		post.addParameter("grant_type", "authorization_code");
		post.addParameter("client_id", clientId);
		post.addParameter("client_secret", clientSecret);
		post.addParameter("redirect_uri", redirectUri);
	}

	private void saveTokens(EnvironmentDO envDO, SFoAuthHandle sfBaseHandle) {
		EnvironmentDAO envDAO = new EnvironmentDAO();
		envDAO.update(envDO, sfBaseHandle);
	}

	/**
	 * 
	 * @param Passing
	 *            state Parameter which consists of BaseOrg Details
	 */
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
			String envId = st.nextToken();
			setEnvId(envId);
			String orgId = st.nextToken();
			String instanceURL = st.nextToken();
			String token = st.nextToken();
			setBaseToken(token);
			String refreshToken = st.nextToken();
			setHomeURL(instanceURL);
			String sessionId = st.nextToken();
			setSessionId(sessionId);
			System.out.println(env + "~" + envId + "~" + orgId + "~"
					+ instanceURL + "~" + token + "~" + refreshToken + "~"
					+ sessionId);
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
			String envId = st.nextToken();
			setEnvId(envId);
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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	

}