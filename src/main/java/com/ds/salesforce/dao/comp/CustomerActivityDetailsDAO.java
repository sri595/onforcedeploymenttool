package com.ds.salesforce.dao.comp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.domain.CustomerActivityDetails;
import com.domain.CustomerMasterDetails;
import com.domain.DeploymentSettingsDO;
import com.domain.EnvironmentDO;
import com.domain.PackageInformationDO;
import com.ds.salesforce.dao.ISFBaseDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Customer_Activity_Detail__c;
import com.sforce.soap.enterprise.sobject.Customer_Master__c;
import com.sforce.soap.enterprise.sobject.OFSServer__Customer_Activity_Details__c;
import com.sforce.soap.enterprise.sobject.OFSServer__Enviroment__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.Constants;
import com.util.SFoAuthHandle;
import com.util.sql.CustomerMasterSQLStmts;
import com.util.sql.DeploymentSettingsSQLStmts;
import com.util.sql.PackageInformationSQLStmts;

/**
 * 
 * @author DeploySettingsDAO is Used For Performing CRUD Operations for
 *         {@link DeploymentSetting__c}
 *
 */
public class CustomerActivityDetailsDAO implements ISFBaseDAO {

	public CustomerActivityDetailsDAO() {
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
		System.out.println("insereting Customer Activity Details Table :");
		CustomerActivityDetails cusDO = (CustomerActivityDetails) obj;
		Customer_Activity_Detail__c[] record = new Customer_Activity_Detail__c[1];
		try {
			// Get the name of the sObject

			Customer_Activity_Detail__c a = new Customer_Activity_Detail__c();
			a.setOrganization_ID__c(cusDO.getOrgId());
			a.setActivity_Name__c(cusDO.getActivityname());
			a.setCustomer_Master__c(cusDO.getMasterid());
			a.setCount__c(cusDO.getCount());
			a.setSource_Org_Id__c(cusDO.getSourceId());
			a.setTarget_Org_Id__c(cusDO.getDestinationId());
			a.setActivity_Time__c(cusDO.getActivitytime());
			a.setDescription__c(cusDO.getDescription());

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
		System.out.println("inserting Customer Activity Details Table of Server :");
		CustomerActivityDetails cusDO = (CustomerActivityDetails) obj;
		OFSServer__Customer_Activity_Details__c[] record = new OFSServer__Customer_Activity_Details__c[1];
		try {
			// Get the name of the sObject

			OFSServer__Customer_Activity_Details__c a = new OFSServer__Customer_Activity_Details__c();
			a.setOFSServer__Organization_ID__c(cusDO.getOrgId());
			a.setOFSServer__Activity_Name__c(cusDO.getActivityname());
			a.setOFSServer__Customer_Master__c(cusDO.getMasterid());
			a.setOFSServer__Count__c(cusDO.getCount());
			a.setOFSServer__Source_Org_Id__c(cusDO.getSourceId());
			a.setOFSServer__Target_Org_Id__c(cusDO.getDestinationId());
			a.setOFSServer__Activity_Time__c(cusDO.getActivitytime());
			a.setOFSServer__Description__c(cusDO.getDescription());

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
					System.out.println("Created Customer Activity Details  record - Id: "
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

	
	@Override
	public List<Object> findById(String orgId, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

}