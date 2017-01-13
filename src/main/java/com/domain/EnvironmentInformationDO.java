package com.domain;

/**
 * 
 * @author EnvironmentDO Used For Setting Environment Details for All Users
 *
 */
public class EnvironmentInformationDO {

	String id;
	String token;
	String refreshtoken;
	String orgIdPlusUserId;
	String userName;
	String orgId;
	String tokenCodeNonEncrypted;
	String serverURL;
	String authType;
	String gitUsername;
	String gitAccesstoken;
	String gitURL;
	String bitBucketUsername;
	String bitBucketURL;
	String bitBucketAccessToken;
	String bitBucketRefreshToken;

	public EnvironmentInformationDO() {
		super();
	}

	/**
	 * 
	 * @param id
	 *            Primary Key
	 * @param orgIdPlusUserId
	 *            Organisation and UserId Combining
	 * @param userName
	 *            Organisation UserName
	 * @param orgId
	 *            Organisation OrgId
	 * @param token
	 *            Organisation Token
	 * @param serverURL
	 *            Organisation InstanceURL
	 * @param refreshtoken
	 *            Organisation refreshToken
	 */
	public EnvironmentInformationDO(String id, String orgIdPlusUserId,
			String userName, String orgId, String token, String serverURL,
			String refreshtoken, String authType) {
		this.id = id;
		this.orgIdPlusUserId = orgIdPlusUserId;
		this.userName = userName;
		this.orgId = orgId;
		this.token = token;
		this.refreshtoken = refreshtoken;
		this.tokenCodeNonEncrypted = token;
		this.serverURL = serverURL;
		this.authType = authType;
	}

	public EnvironmentInformationDO(String id, String orgIdPlusUserId,
			String userName, String orgId, String token, String serverURL,
			String refreshtoken, String authType, String gitUsername,
			String gitAccesstoken, String gitURL, String bitBucketUsername,
			String bitBucketURL, String bitBucketAccessToken,
			String bitBucketRefreshToken) {
		this.id = id;
		this.orgIdPlusUserId = orgIdPlusUserId;
		this.userName = userName;
		this.orgId = orgId;
		this.token = token;
		this.refreshtoken = refreshtoken;
		this.tokenCodeNonEncrypted = token;
		this.serverURL = serverURL;
		this.authType = authType;
		this.gitUsername = gitUsername;
		this.gitAccesstoken = gitAccesstoken;
		this.gitURL = gitURL;
		this.bitBucketUsername = bitBucketUsername;
		this.bitBucketURL = bitBucketURL;
		this.bitBucketAccessToken = bitBucketAccessToken;
		this.bitBucketRefreshToken = bitBucketRefreshToken;
	}

	/**
	 * 
	 * @param orgId
	 *            Organisation OrgId
	 * @param token
	 *            Organisation Token
	 * @param serverURL
	 *            Organisation InstanceURL
	 */
	public EnvironmentInformationDO(String orgId, String token, String serverURL) {
		this.orgId = orgId;
		this.token = token;
		this.serverURL = serverURL;
	}

	/**
	 * 
	 * @param orgId
	 *            Organisation OrgId
	 * @param token
	 *            Organisation Token
	 * @param serverURL
	 *            Organisation InstanceURL
	 * @param userName
	 *            Organisation UserName
	 * @param refreshtoken
	 *            Organisation refreshToken
	 */
	public EnvironmentInformationDO(String orgId, String token,
			String serverURL, String userName, String refreshtoken,
			String authType) {
		this.orgId = orgId;
		this.token = token;
		this.serverURL = serverURL;
		this.userName = userName;
		this.refreshtoken = refreshtoken;
		this.tokenCodeNonEncrypted = token;
		this.authType = authType;
	}

	/**
	 * 
	 * @return Organisation ID
	 */
	public String getOrgId() {
		return orgId;
	}

	/**
	 * 
	 * @param Setting
	 *            Organisation ID
	 */

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 
	 * @return Organisation Token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 
	 * @param Setting
	 *            organisation Token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 
	 * @return UserName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 
	 * @param Setting
	 *            UserName
	 */

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 
	 * @return Non Encrypted Organisation Token
	 */
	public String getTokenCodeNonEncrypted() {
		return tokenCodeNonEncrypted;
	}

	/**
	 * 
	 * @param Setting
	 *            Non Encrypted Organisation Token
	 */
	public void setTokenCodeNonEncrypted(String tokenCodeNonEncrypted) {
		this.tokenCodeNonEncrypted = tokenCodeNonEncrypted;
	}

	/**
	 * 
	 * @return serverURL
	 */
	public String getServerURL() {
		return serverURL;
	}

	/**
	 * 
	 * @param Setting
	 *            serverURL
	 */

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	/**
	 * 
	 * @return RefreshToken
	 */
	public String getRefreshtoken() {
		return refreshtoken;
	}

	/**
	 * 
	 * @param Setting
	 *            RefreshToken
	 */
	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	/**
	 * 
	 * @return OrganisationId + User Id
	 */
	public String getOrgIdPlusUserId() {
		return orgIdPlusUserId;
	}

	/**
	 * 
	 * @param setting
	 *            OrganisationId + User Id
	 */

	public void setOrgIdPlusUserId(String orgIdPlusUserId) {
		this.orgIdPlusUserId = orgIdPlusUserId;
	}

	/**
	 * return all instance variables values
	 */
	public String toString() {
		return this.orgId + "~" + this.token + "~" + this.serverURL + "~"
				+ this.userName;
	}

	/**
	 * 
	 * @return Primary Key
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param Setting
	 *            Primary Key
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getGitUsername() {
		return gitUsername;
	}

	public void setGitUsername(String gitUsername) {
		this.gitUsername = gitUsername;
	}

	public String getGitAccesstoken() {
		return gitAccesstoken;
	}

	public void setGitAccesstoken(String gitAccesstoken) {
		this.gitAccesstoken = gitAccesstoken;
	}

	public String getGitURL() {
		return gitURL;
	}

	public void setGitURL(String gitURL) {
		this.gitURL = gitURL;
	}

	public String getBitBucketUsername() {
		return bitBucketUsername;
	}

	public void setBitBucketUsername(String bitBucketUsername) {
		this.bitBucketUsername = bitBucketUsername;
	}

	public String getBitBucketURL() {
		return bitBucketURL;
	}

	public void setBitBucketURL(String bitBucketURL) {
		this.bitBucketURL = bitBucketURL;
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

}
