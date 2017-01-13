package com.util.sql;

public class GitRepositorySQlStatements {
	
	
	public static String getDetailsByUsername(String username) {
		String sql = "Select Id, Repository_Username__c,Repository_URI__c, Repository_Refresh_Token__c,"
				+ " Repository_Access_Token__c, Environment__c,BaseOrgURL__c, BaseOrgToken__c,BaseOrganisationId__c,BaseOrg_refreshToken__c"
				+ " FROM Git_Repositories__c where Repository_Username__c = '"
				+ username + "'";
		return sql;
	}

}
