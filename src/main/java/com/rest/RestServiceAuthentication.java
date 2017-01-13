package com.rest;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.domain.EnvironmentDO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.services.component.FDGetSFoAuthHandleService;
import com.sforce.soap.metadata.DeployResult;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;
import com.util.Constants;
import com.util.SFoAuthHandle;

@Path("/salesforce")
public class RestServiceAuthentication {

	String accessToken = null;

	@GET
	@Path("/authenticate")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAccessToken(@QueryParam("Username") String Username,
			@QueryParam("Password") String Password) {

		String ClientId = "3MVG9uudbyLbNPZNJeDuIFgdXlPIcEHcPw6nEBDg9qkvuBufUOzJQ.h1pfkhQGqwUCChsAHPMtVrGzEdCC3Va";
		String ClientSecret = "8911696070681472023";
		String tokenURL = "https://login.salesforce.com/services/oauth2/token";
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(tokenURL);

		post.addParameter("grant_type", "password");
		post.addParameter("client_id", ClientId);
		post.addParameter("client_secret", ClientSecret);
		post.addParameter("username", Username);
		post.addParameter("password", Password);
		HttpConnectionManager conManager = httpclient
				.getHttpConnectionManager();
		httpclient.getHostConfiguration().setProxy(
				"us-east-static-02.quotaguard.com", 9293);
		HttpState state = new HttpState();
		state.setProxyCredentials(null, null, new UsernamePasswordCredentials(
				"quotaguard7898", "4ffb21b2078d"));
		httpclient.setState(state);

		try {
			httpclient.executeMethod(post);
			JSONObject authResponse = new JSONObject(new JSONTokener(
					new InputStreamReader(post.getResponseBodyAsStream())));
			accessToken = authResponse.getString("access_token");

			System.out.println(authResponse.toString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return accessToken;
	}

}
