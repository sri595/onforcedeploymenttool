package com.util.sql;

public class RepositoySQLStmts {

	
	public static String getClientRepoQuery(String repoId) {
		String sql = "Select Id, OFSClient__User_Name__c,OFSClient__URL__c, OFSClient__Type__c, OFSClient__Status__c, OFSClient__RefreshToken__c,OFSClient__AccessToken__c "
			    + " FROM OFSClient__Repository__c where Id = '"
				+ repoId + "'";
		return sql;
	}
	public static String getServerRepoQuery(String repoId) {
		String sql = "Select Id, OFSServer__User_Name__c,OFSServer__URL__c, OFSServer__Type__c, OFSServer__Status__c, OFSServer__RefreshToken__c,OFSServer__AccessToken__c "
			    + " FROM OFSServer__Repository__c where Id = '"
				+ repoId + "'";
		return sql;
	}
}
