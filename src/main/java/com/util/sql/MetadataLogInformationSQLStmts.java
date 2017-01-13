package com.util.sql;

public class MetadataLogInformationSQLStmts {

	public static String getMetdataLogRecordQuery(String metadataLogId){
		
		String sql = "SELECT Id, Name, OFSClient__Action__c,OFSClient__OrganizationId__c, OFSClient__Status__c "
				+ " FROM OFSClient__MetadataLogInformation__c" + " where Id= '" + metadataLogId + "'";
		System.out.println(sql);
		return sql;
	}
	
	public static String selectRecords(String OrgId,String type) {

		String sql = "SELECT Id "
				+ " FROM OFSClient__MetadataDescriptionInformation__c"
				+ " where OFSClient__OrganizationId__c= '" + OrgId 
				+ "' and OFSClient__Type__c='" + type + "' ";
		System.out.println(sql);
		return sql;
	}
	
	public static String selectRecords2(String OrgId) {

		String sql = "SELECT Id "
				+ " FROM OFSClient__MetadataDescriptionInformation__c"
				+ " where OFSClient__OrganizationId__c= '" + OrgId 
				+ "' ";
		System.out.println(sql);
		return sql;
	}
	
	public static String getRefreshMetadata(String metadataLogId) {

		String sql = "SELECT Id, Name, OFSClient__MetadataLogInformation__c,OFSClient__Type__c"
				+ " FROM OFSClient__RefreshMetadataInformation__c"
				+ " where OFSClient__MetadataLogInformation__c	= '"
				+ metadataLogId + "'";
		System.out.println(sql);
		return sql;
	}
}
