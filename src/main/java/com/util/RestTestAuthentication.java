package com.util;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RestTestAuthentication {
	
	public static void main(String[] args) {
		
		
		String ClientId="3MVG9uudbyLbNPZNJeDuIFgdXlPIcEHcPw6nEBDg9qkvuBufUOzJQ.h1pfkhQGqwUCChsAHPMtVrGzEdCC3Va";
        String ClientSecret="8911696070681472023";
        String Username="sri.client@gmail.com";
        String Password="infrascape6nIF8237BtVirupWQbGgKxGNev";
        
        HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod("https://login.salesforce.com/services/oauth2/token");
        
        post.addParameter("grant_type", "password");
		post.addParameter("client_id", ClientId);
		post.addParameter("client_secret", ClientSecret);
		post.addParameter("username", Username);
		post.addParameter("password",Password);
		HttpConnectionManager conManager = httpclient.getHttpConnectionManager();
		httpclient.getHostConfiguration().setProxy("us-east-static-02.quotaguard.com", 9293);
		HttpState state = new HttpState();
		state.setProxyCredentials(null, null,
				new UsernamePasswordCredentials("quotaguard7567", "5ffcce244dfa"));
		httpclient.setState(state);
		
		try {
			httpclient.executeMethod(post);
			JSONObject authResponse = new JSONObject(new JSONTokener(
					new InputStreamReader(post.getResponseBodyAsStream())));
			System.out.println(authResponse.toString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
		
		
		
	}

}
