package com.ds.salesforce.dao.comp;

import java.util.ArrayList;
import java.util.List;

import com.domain.MetaBean;
import com.domain.MetadataDescriptionDO;
import com.domain.MetadataDescriptionInformationDO;
import com.ds.salesforce.dao.ISFBaseDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.OFSClient__MetadataDescriptionInformation__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.SFoAuthHandle;
import com.util.sql.MetadataLogInformationSQLStmts;
import com.util.sql.MetadataLogSQLStmts;

/**
 * 
 * @author MetadataDescriptionInformationDAO is Used For Performing CRUD
 *         Operations for {@link MetadataDescriptionInformation__c}
 *
 */
public class MetadataDescriptionInformationDAO implements ISFBaseDAO {

	@Override
	public List<Object> listAll(SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean insert(Object obj, SFoAuthHandle sfHandle) {
		return false;
	}

	public boolean insert(MetaBean[] deployObjArr, String metadataLogId,
			String sOrgId, SFoAuthHandle sfHandle) {
		int retVal = 1;

		// create the records
		OFSClient__MetadataDescriptionInformation__c[] records = new OFSClient__MetadataDescriptionInformation__c[deployObjArr.length];
		try {
			for (int i = 0; i < deployObjArr.length; i++) {
				// Get the name of the sObject
				OFSClient__MetadataDescriptionInformation__c a = new OFSClient__MetadataDescriptionInformation__c();
				a.setOFSClient__MetadataLog__c(metadataLogId);
				a.setOFSClient__Name__c(deployObjArr[i].getName());
				a.setOFSClient__Type__c(deployObjArr[i].getType());
				a.setOFSClient__OrganizationId__c(sOrgId);
				a.setOFSClient__LastModifiedById__c(deployObjArr[i]
						.getLastModifiedById());
				a.setOFSClient__LastModifiedByName__c(deployObjArr[i]
						.getLastModifiedByName());

				a.setOFSClient__LastModifiedDate__c(deployObjArr[i]
						.getLastModifiedByDate());

				records[i] = a;
			}
			commit(records, sfHandle);
			if (retVal < 0) {
				return false;
			}
		} catch (Exception ce) {
			// fce.printStackTrace();
			throw new SFException(ce.toString(),
					SFErrorCodes.SFMetadataDescription_Insert_Error);
		}
		return false;
	}

	public int save(SObject[] sobjects, SFoAuthHandle sfHandle) {
		int retVal = 1;

		return retVal;
	}

	@Override
	public boolean update(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> findById(String metadataLogIdName,
			SFoAuthHandle sfHandle) {
		return null;
	}

	public List<MetaBean> findById1(String metadataLogId,
			SFoAuthHandle sfHandle, String orgId, String type) {

		com.sforce.soap.enterprise.sobject.OFSClient__MetadataDescriptionInformation__c metadataDescriptionInformation__c = null;
		List<MetaBean> list = new ArrayList<MetaBean>();
		QueryResult queryResults = null;
		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			conn.setQueryOptions(1000);
			if (type != "" && type != null) {
				queryResults = conn.query(MetadataLogInformationSQLStmts
						.selectRecords(orgId, type));
			} else {
				queryResults = conn.query(MetadataLogInformationSQLStmts
						.selectRecords2(orgId));

			}
			boolean done = false;
			int loopCount = 0;

			while (!done) {

				System.out.println("Records in results set " + loopCount++
						+ " - ");
				MetaBean metaBean = null;

				// Process the query results
				for (int i = 0; i < queryResults.getRecords().length; i++) {

					metadataDescriptionInformation__c = (com.sforce.soap.enterprise.sobject.OFSClient__MetadataDescriptionInformation__c) queryResults
							.getRecords()[i];

					metaBean = new MetaBean(
							metadataDescriptionInformation__c.getId());
					list.add(metaBean);

				}

				if (queryResults.isDone()) {
					done = true;
				} else {
					queryResults = conn.queryMore(queryResults
							.getQueryLocator());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new SFException(e.toString(),
					SFErrorCodes.SFMetadataLog_Query_Error);
		}
		return list;

	}

	@Override
	public boolean commit(SObject[] sobjects, SFoAuthHandle sfHandle) {
		// System.out.println("SAVE--"+sfHandle.getUserId()+"--"+sfHandle.getPasswd());
		try {
			com.sforce.soap.enterprise.SaveResult[] saveResults = sfHandle
					.getEnterpriseConnection().create(sobjects);

			// check the returned results for any errors
			for (int i = 0; i < saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					System.out
							.println(i
									+ ". Successfully created MetadataDescriptionInformation record - Id: "
									+ saveResults[i].getId());
				} else {
					com.sforce.soap.enterprise.Error[] errors = saveResults[i]
							.getErrors();
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < errors.length; j++) {
						sb.append(errors[j].getMessage());
						sb.append("\n");
						System.out.println("ERROR creating record: "
								+ errors[j].getMessage());
					}
					throw new SFException(sb.toString(),
							SFErrorCodes.SFMetadataDescription_Insert_Error);
				}
			}
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFMetadataDescription_Insert_Error);
		}
		return true;
	}

	public boolean deleteRecords(String[] ids, SFoAuthHandle sfHandle) {
		try {
			com.sforce.soap.enterprise.DeleteResult[] deleteResults = sfHandle
					.getEnterpriseConnection().delete(ids);
			for (int i = 0; i < deleteResults.length; i++) {
				com.sforce.soap.enterprise.DeleteResult deleteResult = deleteResults[i];
				if (deleteResult.isSuccess()) {
					System.out.println("Deleted Record ID: "
							+ deleteResult.getId());

				}
			}
		} catch (Exception ce) {
			ce.printStackTrace();
		}
		return true;
	}
}
