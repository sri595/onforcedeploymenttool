package com.util;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;


public class GitRepoDO {
	private String userName = "skrishna@infrascape.com";
	private String password = "Yarragsa@01";
	private String url = "https://github.com/saiinfra/CustomerTestProject.git";
	private Git git;
	private CredentialsProvider cp;
	String bitBucketUsername;
	String bitBucketAccessToken;
	String bitBucketRefreshToken;
	String bitURL;
	
	public GitRepoDO(String userName,String password, String url ){
		this.userName = userName;
		this.password = password;
		this.url = url;
	}

	public GitRepoDO(String bitBucketUsername,String bitBucketAccessToken, String bitBucketRefreshToken,String bitURL ){
		this.bitBucketUsername = bitBucketUsername;
		this.bitBucketAccessToken = bitBucketAccessToken;
		this.bitBucketRefreshToken = bitBucketRefreshToken;
		this.bitURL=bitURL;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBitBucketUsername() {
		return bitBucketUsername;
	}

	public void setBitBucketUsername(String bitBucketUsername) {
		this.bitBucketUsername = bitBucketUsername;
	}

	public String getBitBucketAccessToken() {
		return bitBucketAccessToken;
	}

	public void setBitBucketAccessToken(String bitBucketAccessToken) {
		this.bitBucketAccessToken = bitBucketAccessToken;
	}

	public String getBitBucketRefreshToken() {
		return bitBucketRefreshToken;
	}

	public void setBitBucketRefreshToken(String bitBucketRefreshToken) {
		this.bitBucketRefreshToken = bitBucketRefreshToken;
	}

	public String getBitURL() {
		return bitURL;
	}

	public void setBitURL(String bitURL) {
		this.bitURL = bitURL;
	}
	
	
}
