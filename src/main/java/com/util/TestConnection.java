package com.util;

import java.util.Calendar;
import java.util.Date;

import com.domain.CustomerMasterDetails;
import com.ds.salesforce.dao.comp.CustomerMasterDetailsDAO;
import com.sforce.soap.enterprise.sobject.Customer_Activity_Detail__c;
public class TestConnection {
	private static SFoAuthHandle sfHandle = null;

	public static void main(String[] args) {

		sfHandle = new SFoAuthHandle(Constants.USERID, Constants.PASSWORD,
				Constants.Server_URL, "");

		System.out.println("Enterprise connection"
				+ sfHandle.getEnterpriseConnection().toString());
		Calendar startdate = Calendar.getInstance();
		startdate.set(2016, 9, 1);
		Calendar enddate = Calendar.getInstance();
		enddate.set(2016, 11, 30);
String active_features="QuickDeploy;GetPackages;Commit;Validate;ValidateAll;Deploy;DeployAll;Retrieve";
		System.out.println(startdate);
		System.out.println(enddate);
		
		Calendar enddate1 = Calendar.getInstance();
		enddate1.add(Calendar.DATE, 90);

		Calendar startdate1 = Calendar.getInstance();
		startdate.setTime(new Date());
		
		Date currentdate = new Date();


		CustomerMasterDetails customerMasterDetails = new CustomerMasterDetails(
				"00D36000000L6NwEAK", "sriserver", "srikanth@infrascape.com",
				"9052270443", "infrascape technologies limited", true,
				startdate1, enddate1,active_features,null,null);
		
		CustomerMasterDetailsDAO customerMasterDetailsDAO=new CustomerMasterDetailsDAO();
		customerMasterDetailsDAO.insert(customerMasterDetails, sfHandle);
		
		Customer_Activity_Detail__c c=new Customer_Activity_Detail__c();

	}

}
