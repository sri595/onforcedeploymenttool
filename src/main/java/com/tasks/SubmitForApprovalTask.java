package com.tasks;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.processflow.ProcessStatus;
import com.processflow.ProcessTrack;
import com.request.deploy.GetPackageRequest;
import com.response.deploy.GetPackageResponse;
import com.response.packages.DeletePackageResponse;
import com.services.application.SubmitForApprovalService;
import com.services.application.getpackage.GetPackageAppService;
import com.services.component.release.ReleaseEnvService;
import com.util.Constants;
import com.util.GitRepoDO;
import com.util.Org;

public class SubmitForApprovalTask implements Runnable {

	String metadataLogId;
	String bOrgId;
	String bOrgToken;
	String bOrgURL;
	String bOrgRefreshToken;
	String sOrgId;
	String sOrgToken;
	String sOrgURL;
	String sOrgRefreshToken;
	String tOrgId;
	String tOrgToken;
	String tOrgURL;
	String tOrgRefreshToken;
	String status;
	String pkgId;
	boolean override;
	String gitUsername;
	String gitAccessToken;
	String gitURL;
	String bitBucketUsername;
	String bitBucketAccessToken;
	String bitBucketRefreshToken;
	String bitBucketURL;
	String repositoryId;

	public SubmitForApprovalTask(String bOrgId, String bOrgToken,
			String bOrgURL, String bOrgRefreshToken, String sOrgId,
			String sOrgToken, String sOrgURL, String sOrgRefreshToken,
			String status, String pkgId, String metadataLogId,
			boolean override, String gitUsername, String gitAccessToken,
			String gitURL, String bitBucketUsername,
			String bitBucketAccessToken, String bitBucketRefreshToken,
			String bitBucketURL,String repositoryId) {
		this.metadataLogId = metadataLogId;
		this.bOrgId = bOrgId;
		this.bOrgURL = bOrgURL;
		this.bOrgToken = bOrgToken;
		this.bOrgRefreshToken = bOrgRefreshToken;
		this.sOrgId = sOrgId;
		this.sOrgToken = sOrgToken;
		this.sOrgURL = sOrgURL;
		this.sOrgRefreshToken = sOrgRefreshToken;

		this.status = status;
		this.pkgId = pkgId;
		this.override = override;
		this.gitUsername = gitUsername;
		this.gitAccessToken = gitAccessToken;
		this.gitURL = gitURL;
		this.bitBucketUsername = bitBucketUsername;
		this.bitBucketAccessToken = bitBucketAccessToken;
		this.bitBucketRefreshToken = bitBucketRefreshToken;
		this.bitBucketURL = bitBucketURL;
		this.repositoryId=repositoryId;

	}

	public String getbOrgId() {
		return bOrgId;
	}

	public void setbOrgId(String bOrgId) {
		this.bOrgId = bOrgId;
	}

	public String getbOrgToken() {
		return bOrgToken;
	}

	public void setbOrgToken(String bOrgToken) {
		this.bOrgToken = bOrgToken;
	}

	public String getbOrgURL() {
		return bOrgURL;
	}

	public void setbOrgURL(String bOrgURL) {
		this.bOrgURL = bOrgURL;
	}

	public String getbOrgRefreshToken() {
		return bOrgRefreshToken;
	}

	public void setbOrgRefreshToken(String bOrgRefreshToken) {
		this.bOrgRefreshToken = bOrgRefreshToken;
	}

	@Override
	public void run() {
		String errors = null;
		boolean errorFlag = false;
		try {
			System.out.println("BitBucket Username in Thread"
					+ getBitBucketUsername());

			Org bOrg = new Org(getbOrgId(), getbOrgToken(), getbOrgURL(),
					getbOrgRefreshToken(), Constants.CustomBaseOrgID);
			Org sOrg = new Org(getsOrgId(), getsOrgToken(), getsOrgURL(),
					getsOrgRefreshToken(), Constants.BaseOrgID);

			SubmitForApprovalService subForAppService = new SubmitForApprovalService(
					bOrg, sOrg, getStatus(), getPkgId(), getMetadataLogId(),
					isOverride(), getGitUsername(), getGitAccessToken(),
					getGitURL(), getBitBucketUsername(),
					getBitBucketAccessToken(), getBitBucketRefreshToken(),
					getBitBucketURL(),getRepositoryId());
			subForAppService.initiate();
		} catch (Exception e) {
			errorFlag = true;
			StringWriter lerrors = new StringWriter();
			e.printStackTrace(new PrintWriter(lerrors));
			errors = lerrors.toString();
		} finally {
			if (errorFlag) {
				System.out.println("Package Operation Complete for requestId: "
						+ getPkgId() + "\nWith Errors: " + errors);
			} else {
				System.out.println("Package Operation Complete for requestId: "
						+ getPkgId());
			}
		}
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public String getMetadataLogId() {
		return metadataLogId;
	}

	public void setMetadataLogId(String metadataLogId) {
		this.metadataLogId = metadataLogId;
	}

	public String getsOrgId() {
		return sOrgId;
	}

	public void setsOrgId(String sOrgId) {
		this.sOrgId = sOrgId;
	}

	public String getsOrgToken() {
		return sOrgToken;
	}

	public void setsOrgToken(String sOrgToken) {
		this.sOrgToken = sOrgToken;
	}

	public String getsOrgURL() {
		return sOrgURL;
	}

	public void setsOrgURL(String sOrgURL) {
		this.sOrgURL = sOrgURL;
	}

	public String getsOrgRefreshToken() {
		return sOrgRefreshToken;
	}

	public void setsOrgRefreshToken(String sOrgRefreshToken) {
		this.sOrgRefreshToken = sOrgRefreshToken;
	}

	public String gettOrgId() {
		return tOrgId;
	}

	public void settOrgId(String tOrgId) {
		this.tOrgId = tOrgId;
	}

	public String gettOrgToken() {
		return tOrgToken;
	}

	public void settOrgToken(String tOrgToken) {
		this.tOrgToken = tOrgToken;
	}

	public String gettOrgURL() {
		return tOrgURL;
	}

	public void settOrgURL(String tOrgURL) {
		this.tOrgURL = tOrgURL;
	}

	public String gettOrgRefreshToken() {
		return tOrgRefreshToken;
	}

	public void settOrgRefreshToken(String tOrgRefreshToken) {
		this.tOrgRefreshToken = tOrgRefreshToken;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
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