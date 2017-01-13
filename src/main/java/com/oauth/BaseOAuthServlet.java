package com.oauth;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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

import com.domain.CustomerMasterDetails;
import com.domain.DeploymentSettingsDO;
import com.domain.EnvironmentDO;
import com.ds.salesforce.dao.comp.CustomerMasterDetailsDAO;
import com.ds.salesforce.dao.comp.DeploySettingsDAO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.services.component.FDGetSFoAuthHandleService;
import com.util.Constants;
import com.util.SFoAuthHandle;

/**
 * Servlet parameters
 */

@WebServlet(name = "baseoauthservlet", urlPatterns = { "/baseoauthservlet/*",
		"/baseoauthservlet" })
public class BaseOAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory
			.getLogger(BaseOAuthServlet.class);

	private String tokenFilePath = null;
	private String clientId = null;
	private String clientSecret = null;
	private String redirectUri = null;
	private String environment = null;
	private String authUrl = null;
	private String tokenUrl = null;
	private String authorizationCode = null;
	private String idURL = null;
	public String envId = null;

	public void init() throws ServletException {

		Properties p = new Properties();

		try {
			p.load(getServletContext().getResourceAsStream(
					"/WEB-INF/properties/config.properties"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		clientId = p.getProperty("baseclientId");
		clientSecret = p.getProperty("baseclientSecret");
		redirectUri = p.getProperty("baseredirectUri");
		environment = p.getProperty("baseenvironment");

		System.out.println("clientId -- " + clientId + " --clientSecret--"
				+ clientSecret + "--redirectUri--" + redirectUri
				+ "--environment--" + environment);

		try {

			authUrl = environment
					+ "/services/oauth2/authorize?response_type=code&client_id="
					+ clientId + "&redirect_uri="
					+ URLEncoder.encode(redirectUri, "UTF-8");
			System.out.println("Auth URL---" + authUrl);
		} catch (Exception e) {
			LOG.error("Error while creating AuthURL: {} ", e.getMessage());
			throw new ServletException("Error while creating AuthURL:", e);
		}
		tokenUrl = environment + "/services/oauth2/token";
	}

	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		String refreshToken = null;

		String accessToken = (String) request.getSession().getAttribute(
				Constants.ACCESS_TOKEN);
		System.out.println("State Parm" +request.getParameter("state"));
		processStateParam(request.getParameter("state"));
		//String sessionId=request.getParameter("sid");
		//System.out.println("Session Id..."+ sessionId);

		System.out.println("main : " + request.getRequestURI());
		System.out.println("tokenurl : " + tokenUrl);
		System.out.println("authUrl : " + authUrl);
		System.out.println("test: " + request.getRequestURI());
		String instanceUrl = null;
		tokenUrl = getEnvironment() + "/services/oauth2/token";
		String code = request.getParameter("code");
		String BaseOrganizationId = request.getParameter("BaseOrganizationId");
		String BaseOrg = request.getParameter("BaseOrg");
		System.out.println("BaseOrganizationId--------:" + BaseOrganizationId);
		System.out.println("BaseOrg--------:" + BaseOrg);

		System.out.println("Authorization code:" + code);
		authorizationCode = code;
		LOG.info("Auth successful, got Authorization code: {} ", code);
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(tokenUrl);
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

		try {
			httpclient.executeMethod(post);
			JSONObject authResponse = new JSONObject(new JSONTokener(
					new InputStreamReader(post.getResponseBodyAsStream())));
			accessToken = authResponse.getString("access_token");
			instanceUrl = authResponse.getString("instance_url");
			refreshToken = authResponse.getString("refresh_token");
			System.out.println("refreshToken....." + refreshToken);
			// System.out.println("********: "+accessToken +
			// "~"+instanceUrl);
			idURL = authResponse.getString("id");
			System.out.println("idURL---------" + idURL);
			LOG.info("Auth Response: {} ", authResponse.toString(2));
		} catch (JSONException e) {
			LOG.error("Error while getting JSONObject from AuthResponse: {} ",
					e.getMessage());
			throw new ServletException(
					"Error while getting JSONObject from AuthResponse: {} ", e);
		} catch (Exception e) {
			LOG.error("Error while Oauth with Salesforce: {} ", e.getMessage());
			throw new ServletException("Error while Oauth with Salesforce:  ",
					e);
		} finally {
			post.releaseConnection();
		}
		// }
		// Set a session attribute so that other servlets can get the access
		// token
		request.getSession().setAttribute(Constants.ACCESS_TOKEN, accessToken);
		request.getSession().setAttribute(Constants.INSTANCE_URL, instanceUrl);
		request.getSession()
				.setAttribute(Constants.REFRESH_TOKEN, refreshToken);

		System.out.println("RL --"
				+ (String) request.getSession().getAttribute(
						Constants.INSTANCE_URL));

		// We also get the instance URL from the OAuth response, so set it
		// in the session too

		request.getSession().setAttribute("AuthorizationCode",
				authorizationCode);
		request.getSession().setAttribute("idURL", idURL);
		System.out.println("ContextPath :" + request.getContextPath()
				+ "/sfcustomhome");
		// }
		DeploymentSettingsDO deploymentSettingsDO = getIdDetails(request);
		SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
				.getSFoAuthHandle(deploymentSettingsDO.getOrgId(),
						deploymentSettingsDO.getToken(),
						deploymentSettingsDO.getServerUrl(),
						deploymentSettingsDO.getRefreshToken(),
						Constants.BaseOrgID);
		String ParentsessionId = sfBaseHandle.getEnterpriseConnection()
				.getSessionHeader().getSessionId();
		String homeURL = (String) request.getSession().getAttribute(
				Constants.INSTANCE_URL);

		String str1 = homeURL + "/" + Constants.jspURL + "?sid="
				+ ParentsessionId + "&retURL=/" + getEnvId();
		
		

		/*
		 * String str1 = homeURL + "/" + Constants.jspURL + "?sid=" +
		 * ParentsessionId;
		 */
		response.sendRedirect(str1);

		// response.sendRedirect(request.getContextPath() + "/sfcustomhome");
	}

	public DeploymentSettingsDO getIdDetails(HttpServletRequest request)
			throws ServletException, IOException, SFException {
		DeploymentSettingsDO deploymentSettingsDO = null;
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String accessToken = (String) session.getAttribute("ACCESS_TOKEN");
		String instanceURL = (String) request.getSession().getAttribute(
				Constants.INSTANCE_URL);
		String refreshToken = (String) request.getSession().getAttribute(
				Constants.REFRESH_TOKEN);

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
		} catch (Exception e) {
			throw new SFException(e.getMessage(), SFErrorCodes.SF_HTTP_Error);
		}

		if (get.getStatusCode() == HttpStatus.SC_OK) {
			// Now lets use the standard java json classes to work with the
			// results
			try {
				JSONObject jsonResponse = new JSONObject(new JSONTokener(
						new InputStreamReader(get.getResponseBodyAsStream())));
				System.out.println("Auth Response: {} "
						+ jsonResponse.toString(2));

				String orgId = new String(
						(String) jsonResponse.get("organization_id"));

				String email = new String((String) jsonResponse.get("email"));

				String display_name = new String(
						(String) jsonResponse.get("display_name"));

				String userName = new String(
						(String) jsonResponse.get("username"));

				String userId = new String((String) jsonResponse.get("user_id"));

				System.out
						.println("Creating deploymentSettingsDO om CustomAuth : "
								+ orgId
								+ "~"
								+ accessToken
								+ "~"
								+ instanceURL
								+ "~" + refreshToken);
				deploymentSettingsDO = new DeploymentSettingsDO(orgId,
						accessToken, instanceURL, refreshToken);
				saveTokens(deploymentSettingsDO);

				EnvironmentDO envDO = new EnvironmentDO(envId, orgId + "_"
						+ userId, userName, orgId,
						(String) session.getAttribute("ACCESS_TOKEN"),
						(String) session.getAttribute(Constants.INSTANCE_URL),
						(String) session.getAttribute("REFRESH_TOKEN"));
				saveTokens1(envDO);

				String active_features = "QuickDeploy;GetPackages;Commit;Validate;ValidateAll;Deploy;DeployAll;Retrieve";

				Calendar enddate = Calendar.getInstance();
				enddate.add(Calendar.DATE, 90);

				Calendar startdate = Calendar.getInstance();
				startdate.setTime(new Date());

				// create customer Master Record By Using Details of JSON
				// Response
				FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

				SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
						.getSFoAuthHandle(deploymentSettingsDO.getOrgId(),
								deploymentSettingsDO.getToken(),
								deploymentSettingsDO.getServerUrl(),
								deploymentSettingsDO.getRefreshToken(),
								Constants.BaseOrgID);
				CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();
				String orgName=customerMasterDetailsDAO
						.findByOrgIdforName(orgId, sfBaseHandle);

				CustomerMasterDetails customerMasterDetails = new CustomerMasterDetails(
						orgId, orgName, email, "", "", true, startdate,
						enddate, active_features, null, null);
				SFoAuthHandle sfcusHandle = new SFoAuthHandle(Constants.USERID,
						Constants.PASSWORD, Constants.Server_URL, "");
				customerMasterDetailsDAO.insert(customerMasterDetails,
						sfcusHandle);

				CustomerMasterDetails c = customerMasterDetailsDAO
						.findByOrgIdServer(orgId, sfBaseHandle);

				if (c == null) {

					customerMasterDetailsDAO.insert1(customerMasterDetails,
							sfBaseHandle);
				}

			} catch (JSONException e) {
				e.printStackTrace();
				LOG.error(
						"Error while getting JSONObject from the records {} ",
						e.getMessage());
				throw new ServletException(
						"Error while getting JSONObject from the records: ", e);
			} catch (SFException e) {
				throw (e);
			}
			SFoAuthHandle sfBaseHandle = null;

		}
		return deploymentSettingsDO;
	}

	public void saveTokens(DeploymentSettingsDO deploymentSettingsDO)
			throws SFException {
		DeploySettingsDAO deploymentSettingsDAO = new DeploySettingsDAO();
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		try {
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
					.getSFoAuthHandle(deploymentSettingsDO.getOrgId(),
							deploymentSettingsDO.getToken(),
							deploymentSettingsDO.getServerUrl(),
							deploymentSettingsDO.getRefreshToken(),
							Constants.BaseOrgID);

			List deploymentSettingsList = deploymentSettingsDAO.findById(
					deploymentSettingsDO.getOrgId(), sfBaseHandle);
			// If should never occur
			if (deploymentSettingsList.size() == 0) {
				deploymentSettingsDAO
						.insert(deploymentSettingsDO, sfBaseHandle);

			} else {
				DeploymentSettingsDO dsDO = null;
				for (Iterator iterator = deploymentSettingsList.iterator(); iterator
						.hasNext();) {
					dsDO = (DeploymentSettingsDO) iterator.next();
					dsDO.setOrgId(deploymentSettingsDO.getOrgId());
					dsDO.setServerUrl(deploymentSettingsDO.getServerUrl());
					dsDO.setToken(deploymentSettingsDO.getToken());
					dsDO.setRefreshToken(deploymentSettingsDO.getRefreshToken());
					deploymentSettingsDAO.update(dsDO, sfBaseHandle);
				}
			}
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			if (sfBaseHandle != null) {
				sfBaseHandle.nullify();
			}
			sfBaseHandle = null;
		} catch (SFException e) {
			throw (e);
		}
	}

	public void saveTokens1(EnvironmentDO environmentDO) throws SFException {
		EnvironmentDAO deploymentSettingsDAO = new EnvironmentDAO();
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		try {
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			SFoAuthHandle sfBaseHandle = fdGetSFoAuthHandleService
					.getSFoAuthHandle(environmentDO.getOrgId(),
							environmentDO.getToken(),
							environmentDO.getServerURL(),
							environmentDO.getRefreshtoken(),
							Constants.BaseOrgID);

			EnvironmentDAO envDAO = new EnvironmentDAO();
			envDAO.update(environmentDO, sfBaseHandle);

		} catch (SFException e) {
			throw (e);
		}
	}

	public void processStateParam(String stateParam) {

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
		}

	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

}