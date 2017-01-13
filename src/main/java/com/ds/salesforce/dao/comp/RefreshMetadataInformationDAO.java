package com.ds.salesforce.dao.comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.domain.MetaBean;
import com.domain.MetadataDescriptionDO;
import com.domain.MetadataLogDO;
import com.domain.RefreshMetadataDO;
import com.domain.RefreshMetadataInformationDO;
import com.ds.salesforce.dao.ISFBaseDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.sobject.OFSServer__MetadataDescription__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.SFoAuthHandle;
import com.util.sql.MetadataLogInformationSQLStmts;
import com.util.sql.MetadataLogSQLStmts;

/**
 * 
 * @author MetadataDescriptionDAO is Used For Performing CRUD Operations for
 *         {@link MetadataDescription__c}
 *
 */
public class RefreshMetadataInformationDAO implements ISFBaseDAO {

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

	public List<RefreshMetadataInformationDO> findById1(String metadataLogId,
			SFoAuthHandle sfHandle) {

		com.sforce.soap.enterprise.sobject.OFSClient__RefreshMetadataInformation__c refreshMetadataInformation__c = null;
		RefreshMetadataInformationDO refreshMetadataDO = null;
		List<RefreshMetadataInformationDO> list = new ArrayList<RefreshMetadataInformationDO>();
		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			QueryResult queryResults = conn
					.query(MetadataLogInformationSQLStmts
							.getRefreshMetadata(metadataLogId));

			System.out.println("No of Records " + queryResults.getSize());

			if (queryResults.getSize() > 0) {
				MetaBean metaBean = null;
				for (int i = 0; i < queryResults.getRecords().length; i++) {
					refreshMetadataInformation__c = (com.sforce.soap.enterprise.sobject.OFSClient__RefreshMetadataInformation__c) queryResults
							.getRecords()[i];

					refreshMetadataDO = new RefreshMetadataInformationDO(
							refreshMetadataInformation__c
									.getOFSClient__Type__c());

					list.add(refreshMetadataDO);
				}
			} else {
				System.out.println(" There are no records size is - : "
						+ queryResults.getSize());

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> findById(String orgId, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

}
