package com.ds.salesforce.dao.comp;

import java.util.List;

import com.domain.CustomerMasterDetails;
import com.ds.salesforce.dao.ISFBaseDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Customer_Master__c;
import com.sforce.soap.enterprise.sobject.OFSServer__Customer_Master__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.SFoAuthHandle;
import com.util.sql.CustomerMasterSQLStmts;

/**
 * 
 * @author DeploySettingsDAO is Used For Performing CRUD Operations for
 *         {@link DeploymentSetting__c}
 *
 */
public class CustomerMasterDetailsDAO implements ISFBaseDAO {

	public CustomerMasterDetailsDAO() {
		super();
	}

	@Override
	public List<Object> listAll(SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Object obj, SFoAuthHandle sfHandle) {
		// create the records
		System.out.println("inserting Customer Master Details Table :");
		CustomerMasterDetails cusDO = (CustomerMasterDetails) obj;
		Customer_Master__c[] record = new Customer_Master__c[1];
		try {
			// Get the name of the sObject

			Customer_Master__c a = new Customer_Master__c();
			a.setOrganization_ID__c(cusDO.getOrgId());
			a.setContract_Start_Date__c(cusDO.getContractstartdate());
			a.setContract_End_Date__c(cusDO.getContractenddate());
			a.setName(cusDO.getName());
			a.setContact_Name__c(cusDO.getName());

			a.setAddress__c(cusDO.getAddress());
			a.setContact_Email__c(cusDO.getEmail());
			a.setContact_Phone__c(cusDO.getPhoneno());

			a.setActive__c(cusDO.isStatus());
			a.setActive_Feature__c(cusDO.getActive_features());

			record[0] = a;
			commit(record, sfHandle);
		} catch (Exception ce) {
			throw new SFException(ce.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	public boolean insert1(Object obj, SFoAuthHandle sfHandle) {
		// create the records
		System.out
				.println("insereting Release Manager Customer Master Details Table :");
		CustomerMasterDetails cusDO = (CustomerMasterDetails) obj;
		OFSServer__Customer_Master__c[] record = new OFSServer__Customer_Master__c[1];
		try {
			// Get the name of the sObject

			OFSServer__Customer_Master__c a = new OFSServer__Customer_Master__c();
			a.setOFSServer__Organization_ID__c(cusDO.getOrgId());
			a.setOFSServer__Contract_Start_Date__c(cusDO.getContractstartdate());
			a.setOFSServer__Contract_End_Date__c(cusDO.getContractenddate());
			a.setName(cusDO.getName());
			a.setOFSServer__Contact_Name__c(cusDO.getName());

			a.setOFSServer__Address__c(cusDO.getAddress());
			a.setOFSServer__Contact_Email__c(cusDO.getEmail());
			a.setOFSServer__Contact_Phone__c(cusDO.getPhoneno());

			a.setOFSServer__Active__c(cusDO.isStatus());
			a.setOFSServer__Active_Feature__c(cusDO.getActive_features());

			record[0] = a;
			commit(record, sfHandle);
		} catch (Exception ce) {
			throw new SFException(ce.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	@Override
	public boolean update(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub

		return true;
	}

	public boolean commit(SObject[] sobjects, SFoAuthHandle sfHandle) {
		// System.out.println("SAVE--"+sfHandle.getUserId()+"--"+sfHandle.getPasswd());
		try {
			com.sforce.soap.enterprise.SaveResult[] saveResults = sfHandle
					.getEnterpriseConnection().create(sobjects);

			for (SaveResult r : saveResults) {
				if (r.isSuccess()) {
					System.out.println("Created Customer Master  record - Id: "
							+ r.getId());
				} else {
					for (com.sforce.soap.enterprise.Error e : r.getErrors()) {
						System.out.println("-status code-" + e.getStatusCode());
						throw new SFException(e.getMessage() + "-status code-"
								+ e.getStatusCode(),
								SFErrorCodes.SFDeployDetails_Update_Error);
					}
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SFException(e.toString(),
					SFErrorCodes.SFDeployDetails_Update_Error);
		}
		return true;
	}

	@Override
	public boolean delete(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return false;
	}

	public CustomerMasterDetails findByOrgId(String id, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		CustomerMasterDetails retObj = null;
		retObj = null;

		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			System.out.println(" sql : "
					+ CustomerMasterSQLStmts.getOrgCusQuery(id));
			QueryResult queryResults = conn.query(CustomerMasterSQLStmts
					.getOrgCusQuery(id));

			if (queryResults.getSize() > 0) {
				for (int i = 0; i < queryResults.getRecords().length; i++) {
					// cast the SObject to a strongly-typed Contact
					com.sforce.soap.enterprise.sobject.Customer_Master__c locObj = (com.sforce.soap.enterprise.sobject.Customer_Master__c) queryResults
							.getRecords()[i];

					System.out.println(" - Id: " + locObj.getId());
					System.out.println(" - Name : " + locObj.getName());
					System.out.println(" - Active Features : "
							+ locObj.getActive_Feature__c());
					System.out.println(" - Current Count : "
							+ locObj.getCurrent_Count__c());
					System.out.println(" - Limit : "
							+ locObj.getLimit__c());

					retObj = new CustomerMasterDetails(locObj.getId(),
							locObj.getOrganization_ID__c(),
							locObj.getContact_Name__c(),
							locObj.getContact_Email__c(),
							locObj.getContact_Phone__c(),
							locObj.getAddress__c(), locObj.getActive__c(),
							locObj.getContract_Start_Date__c(),
							locObj.getContract_End_Date__c(),
							locObj.getActive_Feature__c(),
							locObj.getCurrent_Count__c(), locObj.getLimit__c());
				}
			} else {

				System.out.println("There are no records size is - : "
						+ queryResults.getSize());

			}
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Query_Error);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Query_Error);
		}
		return retObj;
	}

	public CustomerMasterDetails findByOrgIdServer(String id,
			SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		CustomerMasterDetails retObj = null;
		retObj = null;

		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			System.out.println(" sql : "
					+ CustomerMasterSQLStmts.getOrgCusQueryServer(id));
			QueryResult queryResults = conn.query(CustomerMasterSQLStmts
					.getOrgCusQueryServer(id));

			if (queryResults.getSize() > 0) {
				for (int i = 0; i < queryResults.getRecords().length; i++) {
					// cast the SObject to a strongly-typed Contact
					com.sforce.soap.enterprise.sobject.OFSServer__Customer_Master__c locObj = (com.sforce.soap.enterprise.sobject.OFSServer__Customer_Master__c) queryResults
							.getRecords()[i];

					System.out.println(" - Id: " + locObj.getId());
					System.out.println(" - Name : " + locObj.getName());
					System.out.println(" - Active Features : "
							+ locObj.getOFSServer__Active_Feature__c());
					System.out.println(" - Current Count  : "
							+ locObj.getOFSServer__Current_Count__c());
					System.out.println(" -  Limit  : "
							+ locObj.getOFSServer__Limit__c());

					retObj = new CustomerMasterDetails(locObj.getId(),
							locObj.getOFSServer__Organization_ID__c(),
							locObj.getOFSServer__Contact_Name__c(),
							locObj.getOFSServer__Contact_Email__c(),
							locObj.getOFSServer__Contact_Phone__c(),
							locObj.getOFSServer__Address__c(),
							locObj.getOFSServer__Active__c(),
							locObj.getOFSServer__Contract_Start_Date__c(),
							locObj.getOFSServer__Contract_End_Date__c(),
							locObj.getOFSServer__Active_Feature__c(),
							locObj.getOFSServer__Current_Count__c(),
							locObj.getOFSServer__Limit__c());
				}
			} else {

				System.out.println("There are no records size is - : "
						+ queryResults.getSize());

			}
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Query_Error);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Query_Error);
		}
		return retObj;
	}

	public String findByOrgIdforName(String id,
			SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		String orgName=null;

		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			System.out.println(" sql : "
					+ CustomerMasterSQLStmts.getOrgName(id));
			QueryResult queryResults = conn.query(CustomerMasterSQLStmts
					.getOrgName(id));

			if (queryResults.getSize() > 0) {
				for (int i = 0; i < queryResults.getRecords().length; i++) {
					// cast the SObject to a strongly-typed Contact
					com.sforce.soap.enterprise.sobject.Organization locObj = (com.sforce.soap.enterprise.sobject.Organization) queryResults
							.getRecords()[i];

					System.out.println(" - Id: " + locObj.getId());
					System.out.println(" - Name : " + locObj.getName());
					orgName=locObj.getName();
					
				}
			} else {

				System.out.println("There are no records size is - : "
						+ queryResults.getSize());

			}
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Query_Error);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Query_Error);
		}
		return orgName;
	}
	@Override
	public List<Object> findById(String orgId, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

}