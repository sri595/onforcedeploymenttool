package com.github.domain;

public class GitRepositoryDO {

	String id;
	String repository_name;
	String repository_uri;
	String repository_username;
	String repositoy_access_token;
	String repository_refresh_token;
	String envId;
	String destinationorg;
	String destinationURL;
	String destinationToken;
	String destinationrefreshToken;

	public GitRepositoryDO(String id, String repository_name,
			String repository_uri, String repository_username,
			String repositoy_access_token, String repository_refresh_token,
			String envId, String destinationorg, String destinationURL,
			String destinationToken, String destinationrefreshToken) {

		this.id = id;
		this.repository_name = repository_name;
		this.repository_uri = repository_uri;
		this.repository_username = repository_username;
		this.repositoy_access_token = repositoy_access_token;
		this.repository_refresh_token = repository_refresh_token;
		this.envId = envId;
		this.destinationorg = destinationorg;
		this.destinationURL = destinationURL;
		this.destinationToken = destinationToken;
		this.destinationrefreshToken = destinationrefreshToken;

	}

	public GitRepositoryDO(String repository_name, String repository_uri,
			String repository_username, String repositoy_access_token,
			String repository_refresh_token, String envId,
			String destinationorg, String destinationURL,
			String destinationToken, String destinationrefreshToken) {

		this.repository_name = repository_name;
		this.repository_uri = repository_uri;
		this.repository_username = repository_username;
		this.repositoy_access_token = repositoy_access_token;
		this.repository_refresh_token = repository_refresh_token;
		this.envId = envId;
		this.destinationorg = destinationorg;
		this.destinationURL = destinationURL;
		this.destinationToken = destinationToken;
		this.destinationrefreshToken = destinationrefreshToken;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRepository_name() {
		return repository_name;
	}

	public void setRepository_name(String repository_name) {
		this.repository_name = repository_name;
	}

	public String getRepository_uri() {
		return repository_uri;
	}

	public void setRepository_uri(String repository_uri) {
		this.repository_uri = repository_uri;
	}

	public String getRepository_username() {
		return repository_username;
	}

	public void setRepository_username(String repository_username) {
		this.repository_username = repository_username;
	}

	public String getRepositoy_access_token() {
		return repositoy_access_token;
	}

	public void setRepositoy_access_token(String repositoy_access_token) {
		this.repositoy_access_token = repositoy_access_token;
	}

	public String getRepository_refresh_token() {
		return repository_refresh_token;
	}

	public void setRepository_refresh_token(String repository_refresh_token) {
		this.repository_refresh_token = repository_refresh_token;
	}

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

	public String getDestinationorg() {
		return destinationorg;
	}

	public void setDestinationorg(String destinationorg) {
		this.destinationorg = destinationorg;
	}

	public String getDestinationURL() {
		return destinationURL;
	}

	public void setDestinationURL(String destinationURL) {
		this.destinationURL = destinationURL;
	}

	public String getDestinationToken() {
		return destinationToken;
	}

	public void setDestinationToken(String destinationToken) {
		this.destinationToken = destinationToken;
	}

	public String getDestinationrefreshToken() {
		return destinationrefreshToken;
	}

	public void setDestinationrefreshToken(String destinationrefreshToken) {
		this.destinationrefreshToken = destinationrefreshToken;
	}

}
