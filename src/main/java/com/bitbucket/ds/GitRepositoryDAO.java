package com.bitbucket.ds;

import java.util.ArrayList;
import java.util.List;

import com.bitbucket.domain.GitRepositoryDO;
import com.bitbucket.exception.SFErrorCodes;
import com.bitbucket.exception.SFException;
import com.bitbucket.util.SFoAuthHandle;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.SaveResult;
import com.sforce.soap.enterprise.sobject.Git_Repositories__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.util.sql.GitRepositorySQlStatements;

/**
 * 
 * @author DeploySettingsDAO is Used For Performing CRUD Operations for
 *         {@link DeploymentSetting__c}
 *
 */
public class GitRepositoryDAO {

	public GitRepositoryDAO() {
		super();
	}

	public List<Object> listAll(SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insert(Object obj, SFoAuthHandle sfHandle) {
		// create the records
		System.out.println("insereting Data into Git Repository Table :");
		GitRepositoryDO gitDo = (GitRepositoryDO) obj;
		Git_Repositories__c[] record = new Git_Repositories__c[1];
		try {
			// Get the name of the sObject

			Git_Repositories__c a = new Git_Repositories__c();
			a.setName(gitDo.getRepository_name());
			// a.setRepository_URI__c(gitDo.getRepository_uri());
			a.setRepository_Username__c(gitDo.getRepository_username());
			a.setRepository_Access_Token__c(gitDo.getRepositoy_access_token());
			a.setRepository_Refresh_Token__c(gitDo
					.getRepository_refresh_token());
			a.setEnvironment__c(gitDo.getEnvId());
			a.setBaseOrganisationId__c(gitDo.getDestinationorg());
			a.setBaseOrgURL__c(gitDo.getDestinationURL());
			a.setBaseOrgToken__c(gitDo.getDestinationToken());
			a.setBaseOrg_refreshToken__c(gitDo.getDestinationrefreshToken());

			record[0] = a;
			commit(record, sfHandle);
		} catch (Exception ce) {
			throw new SFException(ce.toString(),
					SFErrorCodes.SFEnvironment_Update_Error);
		}
		return true;
	}

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
					System.out.println("Created Git Repository  record - Id: "
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

		public boolean deleteRecords(String[] ids, SFoAuthHandle sfHandle) {
			   try {
				   com.sforce.soap.enterprise.DeleteResult[] deleteResults = sfHandle.getEnterpriseConnection().delete(ids);
			      for (int i = 0; i < deleteResults.length; i++) {
			    	  com.sforce.soap.enterprise. DeleteResult deleteResult = deleteResults[i];
			         if (deleteResult.isSuccess()) {
			            System.out
			                  .println("Deleted Record ID: " + deleteResult.getId());
			        
			         }
			      }
			   } catch (Exception ce) {
			      ce.printStackTrace();
			   }
			   return true;
			}
	

	public List<Object> findByGitUsername(String username, SFoAuthHandle sfHandle) {
		// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			GitRepositoryDO retObj = null;
			retObj = null;
			List<Object> list = new ArrayList<Object>();
			try {
				EnterpriseConnection conn = sfHandle.getEnterpriseConnection();
				System.out.println(" sql : "
						+ GitRepositorySQlStatements.getDetailsByUsername(username));
				QueryResult queryResults = conn.query(GitRepositorySQlStatements
						.getDetailsByUsername(username));

				if (queryResults.getSize() > 0) {
					for (int i = 0; i < queryResults.getRecords().length; i++) {
						// cast the SObject to a strongly-typed Contact
						com.sforce.soap.enterprise.sobject.Git_Repositories__c locObj = (com.sforce.soap.enterprise.sobject.Git_Repositories__c) queryResults
								.getRecords()[i];


						retObj = new GitRepositoryDO(locObj.getId(), locObj.getRepository_Username__c(), "", locObj.getRepository_Username__c(), locObj.getRepository_Access_Token__c(), locObj.getRepository_Refresh_Token__c(), locObj.getEnvironment__c(), locObj.getBaseOrganisationId__c(), locObj.getBaseOrgURL__c(), locObj.getBaseOrgToken__c(), locObj.getBaseOrg_refreshToken__c());
						
					System.out.println("Repository ID..." + locObj.getId() );
						
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

}
