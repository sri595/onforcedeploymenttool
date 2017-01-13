package com.util.sql;

public class MetadataLogSQLStmts {

	public static String getMetdataLogRecordQuery(String metadataLogId) {

		String sql = "SELECT Id, Name, OFSServer__Action__c,OFSServer__OrganizationId__c, OFSServer__Status__c, OFSServer__Releases__c, OFSServer__ReleaseEnvironment__c,OFSServer__TestLevel__c "
				+ " FROM OFSServer__MetadataLog__c"
				+ " where Id= '"
				+ metadataLogId + "'";
		System.out.println(sql);
		return sql;
	}

	public static String gettestMetdataLogRecordQuery(String metadataLogId) {

		String sql = "SELECT Id, Name, Action__c,ID__c, Name__c, RecordId__c, Script__c,Status__c "
				+ " FROM MetadataLog__c" + " where Id= '" + metadataLogId + "'";
		System.out.println(sql);
		return sql;
	}

	public static String selectRecords(String OrgId, String type) {

		String sql = "SELECT Id " + " FROM OFSServer__MetadataDescription__c"
				+ " where OFSServer__OrganizationId__c= '" + OrgId
				+ "' and OFSServer__Type__c='" + type + "' ";
		System.out.println(sql);
		return sql;
	}
	public static String selectRecords2(String OrgId) {

		String sql = "SELECT Id " + " FROM OFSServer__MetadataDescription__c"
				+ " where OFSServer__OrganizationId__c= '" + OrgId + "' ";
		System.out.println(sql);
		return sql;
	}
	public static String getRefreshMetadata(String metadataLogId) {

		String sql = "SELECT Id, Name, OFSServer__MetadataLog__c,OFSServer__Type__c"
				+ " FROM OFSServer__RefreshMetadata__c"
				+ " where OFSServer__MetadataLog__c= '"
				+ metadataLogId + "'";
		System.out.println(sql);
		return sql;
	}
}
