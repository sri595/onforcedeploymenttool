package com.util.sql;

public class CustomerMasterSQLStmts {

	public static String getOrgCusQuery(String orgId) {
		String sql = "Select Id,Active__c,Address__c,Contact_Email__c,Contact_Name__c,Contact_Phone__c,Contract_End_Date__c,Contract_Start_Date__c,Organization_ID__c,Active_Feature__c,Current_Count__c,	Limit__c"
				+ " FROM Customer_Master__c where Organization_ID__c = '"
				+ orgId + "'";
		return sql;
	}

	public static String getOrgCusQueryServer(String orgId) {
		String sql = "Select Id,OFSServer__Active__c,OFSServer__Address__c,OFSServer__Contact_Email__c,OFSServer__Contact_Name__c,OFSServer__Contact_Phone__c,OFSServer__Contract_End_Date__c,OFSServer__Contract_Start_Date__c,OFSServer__Organization_ID__c,OFSServer__Active_Feature__c,OFSServer__Current_Count__c,OFSServer__Limit__c"
				+ " FROM OFSServer__Customer_Master__c where OFSServer__Organization_ID__c = '"
				+ orgId + "'";
		return sql;
	}

	public static String getOrgName(String orgId) {
		String sql = "Select Id,Name" + " FROM Organization where Id = '"
				+ orgId + "'";
		return sql;
	}
}
