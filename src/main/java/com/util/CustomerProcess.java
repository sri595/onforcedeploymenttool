package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.domain.CustomerActivityDetails;
import com.domain.CustomerMasterDetails;
import com.ds.salesforce.dao.comp.CustomerActivityDetailsDAO;
import com.exception.PermissionException;
import com.exception.SFErrorCodes;

public class CustomerProcess {

	public void process(CustomerMasterDetails customerMasterDetails,
			String action, int count, String envSourceId,
			String destinationsourceId, SFoAuthHandle sfHandle,
			SFoAuthHandle sfServerHandle,
			CustomerMasterDetails customerMasterDetailsServer) {

		double d = (double) count;

		Calendar startdate = customerMasterDetails.getContractstartdate();
		Calendar enddate = customerMasterDetails.getContractenddate();
		Date d1 = startdate.getTime();
		Date d2 = enddate.getTime();
		String check = "false";
		Double current_count = customerMasterDetails.getCurrent_count();
		Double limit = customerMasterDetails.getLimit();
		Date currentdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		String active_features = customerMasterDetails.getActive_features();
		StringTokenizer st = new StringTokenizer(active_features, ";");
		String[] arr = new String[8];
		int count1 = 0;
		while (st.hasMoreTokens()) {
			arr[count1] = st.nextToken();
			count1++;

		}
		System.out.println("Array Lenght" + arr.length);
		System.out.println("Customer Limit" + customerMasterDetails.getLimit());
		System.out.println("Customer Current Count"
				+ customerMasterDetails.getCurrent_count());

		for (int i = 0; i < arr.length; i++) {

			if (arr[i].equalsIgnoreCase(action)) {
				check = "true";
				break;
			} else {
				check = "false";
			}

		}

		if (currentdate.after(d2)) {

			throw new PermissionException("PermissionDenied",
					SFErrorCodes.Permission_Error);
		}

		else if ((current_count > limit) && (check.equals("true"))) {

			System.out.println("block1");
			throw new PermissionException(
					"Current Limit Exceeded for compennets but feature is enabled",
					SFErrorCodes.Permission_Error);
		} else if ((current_count < limit) && (check.equals("false"))) {

			System.out.println("block2");

			throw new PermissionException(
					"Current Limit not  Exceeded for compennets but feature is disabled",
					SFErrorCodes.Permission_Error);
		} else

		{
			CustomerActivityDetails customerActivityDetails = new CustomerActivityDetails(
					customerMasterDetails.getId(), action,
					customerMasterDetails.getOrgId(), envSourceId,
					destinationsourceId, "", cal, d);
			CustomerActivityDetails customerActivityDetails1 = new CustomerActivityDetails(
					customerMasterDetailsServer.getId(), action,
					customerMasterDetails.getOrgId(), envSourceId,
					destinationsourceId, "", cal, d);


			CustomerActivityDetailsDAO customerActivityDetailsDAO = new CustomerActivityDetailsDAO();
			customerActivityDetailsDAO
					.insert(customerActivityDetails, sfHandle);

			if (customerMasterDetailsServer != null) {
				customerActivityDetailsDAO.insert1(customerActivityDetails1,
						sfServerHandle);
			}

		}
	}

	public void process1(CustomerMasterDetails customerMasterDetails,
			String action, int count, String envSourceId,
			String destinationsourceId, SFoAuthHandle sfHandle) {

		double d = (double) count;

		Calendar startdate = customerMasterDetails.getContractenddate();
		Calendar enddate = customerMasterDetails.getContractenddate();
		Date d1 = startdate.getTime();
		Date d2 = enddate.getTime();
		String check = "false";
		Double current_count = customerMasterDetails.getCurrent_count();
		Double limit = customerMasterDetails.getLimit();
		Date currentdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		String active_features = customerMasterDetails.getActive_features();
		StringTokenizer st = new StringTokenizer(active_features, ";");
		String[] arr = new String[8];
		int count1 = 0;
		while (st.hasMoreTokens()) {
			arr[count1] = st.nextToken();
			count1++;

		}
		System.out.println("Array Lenght" + arr.length);
		System.out.println("Customer Limit" + customerMasterDetails.getLimit());
		System.out.println("Customer Current Count"
				+ customerMasterDetails.getCurrent_count());

		for (int i = 0; i < arr.length; i++) {

			if (arr[i].equalsIgnoreCase(action)) {
				check = "true";
				break;
			} else {
				check = "false";
			}

		}

		if (currentdate.after(d2)) {

			throw new PermissionException("PermissionDenied",
					SFErrorCodes.Permission_Error);
		}

		else if ((current_count > limit) && (check.equals("true"))) {

			System.out.println("block1");
			throw new PermissionException(
					"Current Limit Exceeded for compennets but feature is enabled",
					SFErrorCodes.Permission_Error);
		} else if ((current_count < limit) && (check.equals("false"))) {

			System.out.println("block2");

			throw new PermissionException(
					"Current Limit not  Exceeded for components but feature is disabled",
					SFErrorCodes.Permission_Error);
		} else

		{
			CustomerActivityDetails customerActivityDetails = new CustomerActivityDetails(
					customerMasterDetails.getId(), action,
					customerMasterDetails.getOrgId(), envSourceId,
					destinationsourceId, "", cal, d);

			CustomerActivityDetailsDAO customerActivityDetailsDAO = new CustomerActivityDetailsDAO();
			customerActivityDetailsDAO.insert1(customerActivityDetails,
					sfHandle);

		}
	}
}
