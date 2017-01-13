package com.util.oauth;

public class AuthUserInfoDO {
	String userName;
	String userId;
	String orgId;
	String displayName;
	String email;

	public AuthUserInfoDO(String userName, String userId,
			String orgId,String displayName,String email) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.orgId = orgId;
		this.email=email;
		this.displayName=displayName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
