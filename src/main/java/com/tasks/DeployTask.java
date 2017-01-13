package com.tasks;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import com.domain.MultiPleDeploymentDO;
import com.services.component.FDDeployCompService;
import com.util.XMLUtil;

/**
 * 
 * @author DeployTask is Used For Preparing a Task For Deployment and Assigning
 *         to a Thread for unit Process
 *
 */

public class DeployTask implements Runnable {

	private String orgId;
	private String token;
	private String serverURL;
	private String refreshToken;
	private String metadataLogId;
	private boolean isValidate;
	List<MultiPleDeploymentDO> multiPleDeploymentDOs;
	String gitUsername;
	String gitAccessToken;
	String gitURL;
	String bitBucketUsername;
	String bitBucketAccessToken;
	String bitBucketRefreshToken;
	String bitBucketURL;
	String repositoryId;

	public DeployTask(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId) {
		this.metadataLogId = metadataLogId;
		this.orgId = orgId;
		this.token = token;
		this.refreshToken = refreshToken;
		this.serverURL = serverURL;
	}

	/**
	 * 
	 * @param orgId
	 * @param token
	 * @param serverURL
	 * @param refreshToken
	 * @param metadataLogId
	 * @param isValidate
	 */
	public DeployTask(String orgId, String token, String serverURL,
			String refreshToken, String metadataLogId, boolean isValidate,
			String repositoryId,String bitBucketURL,String gitURL) {
		this.metadataLogId = metadataLogId;
		this.orgId = orgId;
		this.token = token;
		this.refreshToken = refreshToken;
		this.serverURL = serverURL;
		this.isValidate = isValidate;
		this.repositoryId = repositoryId;
		this.bitBucketURL=bitBucketURL;
		this.gitURL=gitURL;

	}

	/**
	 * 
	 * @return
	 */
	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	@Override
	public void run() {
		String errors = null;
		boolean errorFlag = false;
		try {

			processTask(getOrgId(), getToken(), getServerURL(),
					getRefreshToken(), getMetadataLogId(), isValidate(),
					getRepositoryId(),getBitBucketURL(),getGitURL());

		} catch (Exception e) {
			errorFlag = true;
			StringWriter lerrors = new StringWriter();
			e.printStackTrace(new PrintWriter(lerrors));
			errors = lerrors.toString();
		} finally {
			if (errorFlag) {
				XMLUtil.doPreProcessing(getMetadataLogId());

				System.out.println("Deploy Operation Complete for requestId: "
						+ getMetadataLogId() + "\nWith Errors: " + errors);
			} else {
				XMLUtil.doPreProcessing(getMetadataLogId());

				System.out.println("Deploy Operation Complete for requestId: "
						+ getMetadataLogId());
			}
		}
	}

	public synchronized void processTask(String orgId, String token,
			String serverURL, String refreshToken, String metadataLogId,
			boolean isValidate,String repositoryId,String bitBucketURL,String gitURL) {

		System.out.println("MetadataLogId.." + getMetadataLogId());
		FDDeployCompService deployService = new FDDeployCompService();
		deployService.deploy(orgId, token, serverURL, refreshToken,
				metadataLogId, isValidate,repositoryId,bitBucketURL,gitURL);

	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getMetadataLogId() {
		return metadataLogId;
	}

	public void setMetadataLogId(String metadataLogId) {
		this.metadataLogId = metadataLogId;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getGitUsername() {
		return gitUsername;
	}

	public void setGitUsername(String gitUsername) {
		this.gitUsername = gitUsername;
	}

	public String getGitAccessToken() {
		return gitAccessToken;
	}

	public void setGitAccessToken(String gitAccessToken) {
		this.gitAccessToken = gitAccessToken;
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

	public String getBitBucketURL() {
		return bitBucketURL;
	}

	public void setBitBucketURL(String bitBucketURL) {
		this.bitBucketURL = bitBucketURL;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

}