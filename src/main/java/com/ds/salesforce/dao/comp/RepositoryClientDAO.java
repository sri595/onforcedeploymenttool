package com.ds.salesforce.dao.comp;

import java.util.ArrayList;
import java.util.List;

import com.domain.EnvironmentInformationDO;
import com.domain.RepositoryClient;
import com.ds.salesforce.dao.ISFBaseDAO;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.SFoAuthHandle;
import com.util.sql.EnvironmentSQLStmts;
import com.util.sql.RepositoySQLStmts;

/**
 * 
 * @author EnvironmentDAO is Used For Performing CRUD Operations for
 *         {@link Enviroment__c}
 *
 */

public class RepositoryClientDAO implements ISFBaseDAO {

	public RepositoryClientDAO() {
		super();
	}

	@Override
	public List<Object> findById(String repoId, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		RepositoryClient retObj = null;
		retObj = null;
		List list = new ArrayList();
		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			System.out.println(" sql : "
					+ RepositoySQLStmts.getClientRepoQuery(repoId));
			QueryResult queryResults = conn.query(RepositoySQLStmts
					.getClientRepoQuery(repoId));

			if (queryResults.getSize() > 0) {
				for (int i = 0; i < queryResults.getRecords().length; i++) {
					// cast the SObject to a strongly-typed Contact
					com.sforce.soap.enterprise.sobject.OFSClient__Repository__c locObj = (com.sforce.soap.enterprise.sobject.OFSClient__Repository__c) queryResults
							.getRecords()[i];
					System.out.println(" - Id: " + locObj.getId());
					System.out.println(" - Username: "
							+ locObj.getOFSClient__User_Name__c());
					System.out.println(" - AccessToken: "
							+ locObj.getOFSClient__AccessToken__c());
					System.out.println(" - RefreshToken: "
							+ locObj.getOFSClient__RefreshToken__c());
					System.out.println(" - Repository URL: "
							+ locObj.getOFSClient__URL__c());
					System.out.println(" - Repository Type: "
							+ locObj.getOFSClient__Type__c());

					retObj = new RepositoryClient(locObj.getId(),
							locObj.getOFSClient__User_Name__c(),
							locObj.getOFSClient__AccessToken__c(),
							locObj.getOFSClient__RefreshToken__c(),
							locObj.getOFSClient__URL__c(),
							locObj.getOFSClient__Type__c());
					list.add(retObj);
				}
			} else {
				System.out.println(" There are no records size is - : "
						+ queryResults.getSize());
			}
		} catch (SFException e) {
			throw new SFException(e.toString(), SFErrorCodes.SFRepository_Error);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SFException(e.toString(), SFErrorCodes.SFRepository_Error);
		}
		return list;
	}

	public boolean updateBitUsernameandPassword(Object obj,
			SFoAuthHandle sfHandle) {
		com.sforce.soap.enterprise.sobject.OFSClient__Repository__c newEnvObj = new com.sforce.soap.enterprise.sobject.OFSClient__Repository__c();
		RepositoryClient lObj = (RepositoryClient) obj;
		try {
			newEnvObj.setId(lObj.getId());
			newEnvObj.setOFSClient__User_Name__c(lObj.getUsername());
			newEnvObj.setOFSClient__AccessToken__c(lObj.getAccessToken());
			newEnvObj.setOFSClient__RefreshToken__c(lObj.getRefreshToken());

			com.sforce.soap.enterprise.sobject.OFSClient__Repository__c[] mobj = new com.sforce.soap.enterprise.sobject.OFSClient__Repository__c[1];
			mobj[0] = newEnvObj;
			commit(mobj, sfHandle);
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	@Override
	public List<Object> listAll(SFoAuthHandle sfHandle) {

		return null;
	}

	public boolean updateGitUsernameandPassword(Object obj,
			SFoAuthHandle sfHandle) {
		com.sforce.soap.enterprise.sobject.OFSClient__Repository__c newEnvObj = new com.sforce.soap.enterprise.sobject.OFSClient__Repository__c();
		RepositoryClient lObj = (RepositoryClient) obj;
		try {
			newEnvObj.setId(lObj.getId());
			newEnvObj.setOFSClient__User_Name__c(lObj.getUsername());
			newEnvObj.setOFSClient__AccessToken__c(lObj.getAccessToken());
			com.sforce.soap.enterprise.sobject.OFSClient__Repository__c[] mobj = new com.sforce.soap.enterprise.sobject.OFSClient__Repository__c[1];
			mobj[0] = newEnvObj;
			commit(mobj, sfHandle);
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	@Override
	public boolean insert(Object obj, SFoAuthHandle sfHandle) {
		// create the records
		System.out.println("insereting EnvironmentDAO :");
		EnvironmentInformationDO envDO = (EnvironmentInformationDO) obj;
		OFSClient__EnviromentInformation__c[] record = new OFSClient__EnviromentInformation__c[1];
		try {
			// Get the name of the sObject
			OFSClient__EnviromentInformation__c a = new OFSClient__EnviromentInformation__c();
			a.setOFSClient__Org_ID__c(envDO.getOrgId());
			a.setOFSClient__TokenCode__c(envDO.getToken());
			a.setOFSClient__TokenCodeNonEncrypted__c(envDO.getToken());
			a.setOFSClient__Server_URL__c(envDO.getServerURL());
			a.setOFSClient__Auth_Type__c(envDO.getAuthType());

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
		com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c newEnvObj = new com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c();
		EnvironmentInformationDO lObj = (EnvironmentInformationDO) obj;
		try {
			newEnvObj.setId(lObj.getId());
			newEnvObj.setOFSClient__Org_ID__c(lObj.getOrgIdPlusUserId());
			newEnvObj.setOFSClient__User_Name__c(lObj.getUserName());
			newEnvObj.setOFSClient__TokenCode__c(lObj.getToken());
			newEnvObj.setOFSClient__TokenCodeNonEncrypted__c(lObj
					.getTokenCodeNonEncrypted());
			newEnvObj.setOFSClient__RefreshTokenCode__c(lObj.getRefreshtoken());
			newEnvObj.setOFSClient__Server_URL__c(lObj.getServerURL());
			com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c[] mobj = new com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c[1];
			mobj[0] = newEnvObj;
			commit(mobj, sfHandle);
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	public boolean updateBitbucketAccessToken(Object obj, SFoAuthHandle sfHandle) {
		com.sforce.soap.enterprise.sobject.OFSClient__Repository__c newEnvObj = new com.sforce.soap.enterprise.sobject.OFSClient__Repository__c();
		RepositoryClient lObj = (RepositoryClient) obj;
		System.out.println("updateAccessTokne" + lObj.getAccessToken());
		try {
			newEnvObj.setId(lObj.getId());
			newEnvObj.setOFSClient__AccessToken__c(lObj.getAccessToken());

			com.sforce.soap.enterprise.sobject.OFSClient__Repository__c[] mobj = new com.sforce.soap.enterprise.sobject.OFSClient__Repository__c[1];
			mobj[0] = newEnvObj;
			commit(mobj, sfHandle);
		} catch (SFException e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	public List<Object> findByEnvId(String envId, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		EnvironmentInformationDO retObj = null;
		retObj = null;
		List list = new ArrayList();
		try {
			EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
			System.out.println(" sql : "
					+ EnvironmentSQLStmts.getClientEnvQuery(envId));
			QueryResult queryResults = conn.query(EnvironmentSQLStmts
					.getClientEnvQuery(envId));

			if (queryResults.getSize() > 0) {
				for (int i = 0; i < queryResults.getRecords().length; i++) {
					// cast the SObject to a strongly-typed Contact
					com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c locObj = (com.sforce.soap.enterprise.sobject.OFSClient__EnviromentInformation__c) queryResults
							.getRecords()[i];

					System.out.println(" - Id: " + locObj.getId());
					System.out.println(" - Id: "
							+ locObj.getOFSClient__Org_ID__c());
					System.out.println(" - Name: "
							+ locObj.getOFSClient__User_Name__c());
					System.out.println(" - Org: "
							+ locObj.getOFSClient__OrganizationId__c());
					System.out.println(" - token: "
							+ locObj.getOFSClient__TokenCode__c());
					System.out.println(" - refresh: "
							+ locObj.getOFSClient__RefreshTokenCode__c());
					System.out.println(" - encr: "
							+ locObj.getOFSClient__TokenCodeNonEncrypted__c());
					System.out.println(" - server: "
							+ locObj.getOFSClient__Server_URL__c());

					retObj = new EnvironmentInformationDO(locObj.getId(),
							locObj.getOFSClient__Org_ID__c(),
							locObj.getOFSClient__User_Name__c(),
							locObj.getOFSClient__OrganizationId__c(),
							locObj.getOFSClient__TokenCodeNonEncrypted__c(),
							locObj.getOFSClient__Server_URL__c(),
							locObj.getOFSClient__RefreshTokenCode__c(),
							locObj.getOFSClient__Auth_Type__c(),
							locObj.getOFSClient__GIT_User_Name__c(),
							locObj.getOFSClient__GIT_Access_Token__c(),
							locObj.getOFSClient__GIT_URL__c(),
							locObj.getOFSClient__Bitbucket_User_Name__c(),
							locObj.getOFSClient__BitBucket_URL__c(),
							locObj.getOFSClient__BitBucket_AccessToken__c(),
							locObj.getOFSClient__BitBucket_RefreshToken__c());
					list.add(retObj);

				}
			} else {
				System.out.println(" There are no records size is - : "
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
		return list;
	}

	@Override
	public boolean delete(Object obj, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean commit(SObject[] sobjects, SFoAuthHandle sfHandle) {
		try {
			if (sobjects != null && sobjects.length > 0) {
				UpsertResult[] results = sfHandle.getEnterpriseConnection()
						.upsert("Id", sobjects);
				for (UpsertResult r : results) {
					if (r.isSuccess()) {
						System.out.println("Updated Environment component: "
								+ r.getId());
					} else {
						System.out
								.println("Errors were encountered while updating "
										+ r.getId());
						for (com.sforce.soap.enterprise.Error e : r.getErrors()) {
							System.out.println("Error message: "
									+ e.getMessage());
							System.out.println("Status code: "
									+ e.getStatusCode());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

	public boolean commit1(SObject[] sobjects, SFoAuthHandle sfHandle) {
		try {
			if (sobjects != null && sobjects.length > 0) {
				UpsertResult[] results = sfHandle.getEnterpriseConnection()
						.upsert("Id", sobjects);
				for (UpsertResult r : results) {
					if (r.isSuccess()) {
						System.out.println("Updated Environment component: "
								+ r.getId());
					} else {
						System.out
								.println("Errors were encountered while updating "
										+ r.getId());
						for (com.sforce.soap.enterprise.Error e : r.getErrors()) {
							System.out.println("Error message: "
									+ e.getMessage());
							System.out.println("Status code: "
									+ e.getStatusCode());
						}
					}
				}
			}
		} catch (Exception e) {
			throw new SFException(e.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}
}
