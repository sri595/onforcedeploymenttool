package com.domain;

/**
 * 
 * @author MultiPleDeploymentDO is Used for Parallel Deployment
 *
 */

public class MultiPleDeploymentDO {

	String metadataLog;
	String baseOrg;
	String baseOrgToken;
	String refreshToken;
	String baseOrgURL;
	String validationId;
	String repositoryId;
	String bitURL;
	String gitURL;

	/**
	 * 
	 * @param metadataLog
	 * @param baseOrg
	 * @param baseOrgToken
	 * @param refreshToken
	 * @param baseOrgURL
	 */

	public MultiPleDeploymentDO(String metadataLog, String baseOrg, String baseOrgToken, String refreshToken,
			String baseOrgURL,String repositoryId,String bitURL,String gitURL) {

		this.metadataLog = metadataLog;
		this.baseOrg = baseOrg;
		this.baseOrgToken = baseOrgToken;
		this.refreshToken = refreshToken;
		this.baseOrgURL = baseOrgURL;
		this.repositoryId=repositoryId;
		this.bitURL=bitURL;
		this.gitURL=gitURL;

	}

	/**
	 * 
	 * @param metadataLog
	 * @param baseOrg
	 * @param baseOrgToken
	 * @param refreshToken
	 * @param baseOrgURL
	 * @param validationId
	 */
	public MultiPleDeploymentDO(String metadataLog, String baseOrg, String baseOrgToken, String refreshToken,
			String baseOrgURL, String validationId) {

		this.metadataLog = metadataLog;
		this.baseOrg = baseOrg;
		this.baseOrgToken = baseOrgToken;
		this.refreshToken = refreshToken;
		this.baseOrgURL = baseOrgURL;
		this.validationId = validationId;

	}

	/**
	 * 
	 * @return metadataLog
	 */
	public String getMetadataLog() {
		return metadataLog;
	}

	/**
	 * 
	 * @param metadataLog
	 */
	public void setMetadataLog(String metadataLog) {
		this.metadataLog = metadataLog;
	}

	/**
	 * 
	 * @return baseOrg
	 */
	public String getBaseOrg() {
		return baseOrg;
	}

	/**
	 * 
	 * @param baseOrg
	 */
	public void setBaseOrg(String baseOrg) {
		this.baseOrg = baseOrg;
	}

	/**
	 * 
	 * @return baseOrgToken
	 */
	public String getBaseOrgToken() {
		return baseOrgToken;
	}

	/**
	 * 
	 * @param baseOrgToken
	 */
	public void setBaseOrgToken(String baseOrgToken) {
		this.baseOrgToken = baseOrgToken;
	}

	/**
	 * 
	 * @return refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * 
	 * @param refreshToken
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * 
	 * @return baseOrgURL
	 */
	public String getBaseOrgURL() {
		return baseOrgURL;
	}

	/**
	 * 
	 * @param baseOrgURL
	 */
	public void setBaseOrgURL(String baseOrgURL) {
		this.baseOrgURL = baseOrgURL;
	}

	/**
	 * 
	 * @return validationId
	 */
	public String getValidationId() {
		return validationId;
	}

	/**
	 * 
	 * @param validationId
	 */

	public void setValidationId(String validationId) {
		this.validationId = validationId;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getBitURL() {
		return bitURL;
	}

	public void setBitURL(String bitURL) {
		this.bitURL = bitURL;
	}

	public String getGitURL() {
		return gitURL;
	}

	public void setGitURL(String gitURL) {
		this.gitURL = gitURL;
	}

}
