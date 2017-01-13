package com.services.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.domain.CustomerActivityDetails;
import com.domain.CustomerMasterDetails;
import com.domain.EnvironmentInformationDO;
import com.domain.MetaBean;
import com.domain.MetadataLogInformationDO;
import com.domain.RefreshMetadataInformationDO;
import com.ds.salesforce.dao.comp.CustomerActivityDetailsDAO;
import com.ds.salesforce.dao.comp.CustomerMasterDetailsDAO;
import com.ds.salesforce.dao.comp.MetadataDescriptionInformationDAO;
import com.ds.salesforce.dao.comp.RefreshMetadataInformationDAO;
import com.exception.PermissionException;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.services.application.RDAppService;
import com.tasks.PreProcessingTask;
import com.util.Constants;
import com.util.CsvFileWriter1;
import com.util.CustomerProcess;
import com.util.SFoAuthHandle;
import com.util.oauth.RefreshTokens;

public class FDRetrieveClientCompService {

	public FDRetrieveClientCompService() {
		super();
	}

	public void retrieve(String bOrgId, String bOrgToken, String bOrgURL,
			String refreshToken, String orgType, String metadataLogId) {

		MetadataLogInformationDO metadataLogInformationDO = null;
		CustomerMasterDetails customer = null;
		List<MetaBean> mainMBList = null;
        SFoAuthHandle sfcusHandle = null;
		EnvironmentInformationDO envSoureDO=null;
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		// do pre-processing
		// does some sanity checks on input variables
		// updates the refreshed tokens in Environment
		PreProcessingTask preProcessingTask = new PreProcessingTask(bOrgId,
				bOrgToken, bOrgURL, refreshToken, orgType, metadataLogId);
		bOrgToken = preProcessingTask.doPreProcess();
		// get refreshed base token

		try {
			// Get Meta data Log details
			metadataLogInformationDO = RDAppService.findMetadataLogInformation(
					metadataLogId, fdGetSFoAuthHandleService.getSFoAuthHandle(
							bOrgId, bOrgToken, bOrgURL, refreshToken,
							Constants.CustomBaseOrgID));
			// nullify connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();

			// updating metadataLog to processing state
			RDAppService.updateMetadataLogInformationStatus(
					metadataLogInformationDO, Constants.PROCESSING_STATUS,
					fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
							bOrgToken, bOrgURL, refreshToken,
							Constants.CustomBaseOrgID));
			// nullify connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();

			if (metadataLogInformationDO.getAction() != null
					&& (metadataLogInformationDO.getAction().equals("Retrieve"))) {
				if (metadataLogInformationDO.getStatus() != null
						&& (metadataLogInformationDO.getStatus()
								.equals(Constants.PROCESSING_STATUS))) {
					System.out.println("Retrieve------");

					sfcusHandle = new SFoAuthHandle(Constants.USERID,
							Constants.PASSWORD, Constants.Server_URL, "");
					CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();

					customer = customerMasterDetailsDAO.findByOrgId(bOrgId,
							sfcusHandle);

					CustomerProcess customerProcess = new CustomerProcess();
					// refresh connection
					fdGetSFoAuthHandleService.setSfHandleToNUll();
					RefreshTokens refreshTokens1 = new RefreshTokens();
					// getting Token
					String newSToken = refreshTokens1.refreshSFHandle(bOrgId,
							bOrgToken, bOrgURL, Constants.CustomBaseOrgID,
							refreshToken);
					refreshTokens1.getoAuthToken();

					// Creating EnvironmentDO Object
					 envSoureDO = RDAppService.getEnv1(
							metadataLogInformationDO.getSourceOrgId(),
							fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
									bOrgToken, bOrgURL, refreshToken,
									Constants.CustomBaseOrgID));
					String newSToken1 = refreshTokens1
							.refreshClientCustomSFHandle(envSoureDO, bOrgId,
									bOrgToken, bOrgURL, refreshToken);
					envSoureDO.setToken(newSToken1);

					RefreshMetadataInformationDAO refreshMetadataDAO = new RefreshMetadataInformationDAO();

					List<RefreshMetadataInformationDO> listfromrefreshMetadataTypes = refreshMetadataDAO
							.findById1(metadataLogId, fdGetSFoAuthHandleService
									.getSFoAuthHandle(bOrgId, bOrgToken,
											bOrgURL, refreshToken,
											Constants.CustomBaseOrgID));

					// delete records from metadatadescriotion table
					List<MetaBean> metabeanListFromDbb = new ArrayList();

					List<MetaBean> metabeanListFromDb = null;

					MetadataDescriptionInformationDAO metadataDescriptionInformationDAO = new MetadataDescriptionInformationDAO();
					if (listfromrefreshMetadataTypes.size() > 0) {

						for (Iterator iterator = listfromrefreshMetadataTypes
								.iterator(); iterator.hasNext();) {
							RefreshMetadataInformationDO refreshMetadataDO = (RefreshMetadataInformationDO) iterator
									.next();

							metabeanListFromDb = metadataDescriptionInformationDAO
									.findById1(
											metadataLogInformationDO.getId(),
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															bOrgId,
															bOrgToken,
															bOrgURL,
															refreshToken,
															Constants.CustomBaseOrgID),
											envSoureDO.getOrgId(),
											refreshMetadataDO.getType());

							metabeanListFromDbb.addAll(metabeanListFromDb);

							// Do bulk inserts in Base Environment Organisation.

						}

						doBulkDeletes(metabeanListFromDbb, bOrgId, bOrgToken,
								bOrgURL, refreshToken);

						// refresh connection
						fdGetSFoAuthHandleService.setSfHandleToNUll();
					      mainMBList = getRetrieveObjListFromSource(
								metadataLogInformationDO.getLogName(),
								fdGetSFoAuthHandleService.getSFoAuthHandle(
										envSoureDO, Constants.CustomBaseOrgID),
								listfromrefreshMetadataTypes);

					/*	if (customer != null) {
							customerProcess.process(customer,
									metadataLogInformationDO.getAction(),
									mainMBList.size(), envSoureDO.getOrgId(),
									"", sfcusHandle,fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
											bOrgToken, bOrgURL, refreshToken,
											Constants.CustomBaseOrgID),null);
						}*/

						doBulkInserts(mainMBList, bOrgId, bOrgToken, bOrgURL,
								refreshToken);

						// Update Success message
						fdGetSFoAuthHandleService.setSfHandleToNUll();

					} else {

						metabeanListFromDb = metadataDescriptionInformationDAO
								.findById1(
										metadataLogInformationDO.getId(),
										fdGetSFoAuthHandleService
												.getSFoAuthHandle(
														bOrgId,
														bOrgToken,
														bOrgURL,
														refreshToken,
														Constants.CustomBaseOrgID),
										envSoureDO.getOrgId(), null);

						if (metabeanListFromDb.size() > 0) {

							doBulkDeletes(metabeanListFromDb, bOrgId,
									bOrgToken, bOrgURL, refreshToken);

						}

						// refresh connection
						fdGetSFoAuthHandleService.setSfHandleToNUll();
						 mainMBList = getRetrieveObjListFromSource(
								metadataLogInformationDO.getLogName(),
								fdGetSFoAuthHandleService.getSFoAuthHandle(
										envSoureDO, Constants.CustomBaseOrgID),
								null);

						/*if (customer != null) {
							customerProcess.process(customer,
									metadataLogInformationDO.getAction(),
									mainMBList.size(), envSoureDO.getOrgId(),
									"", sfcusHandle,fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
											bOrgToken, bOrgURL, refreshToken,
											Constants.CustomBaseOrgID),null);
						}*/
						// Do bulk inserts in Base Environment Organisation.
						doBulkInserts(mainMBList, bOrgId, bOrgToken, bOrgURL,
								refreshToken);

						// Update Success message
						fdGetSFoAuthHandleService.setSfHandleToNUll();

					}
					// updating metadataLog
					RDAppService.updateMetadataLogInformationStatus(
							metadataLogInformationDO,
							Constants.COMPLETED_STATUS,
							fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
									bOrgToken, bOrgURL, refreshToken,
									Constants.CustomBaseOrgID));
					RDAppService.updateDeploymentDetailsInformation(
							metadataLogId, Constants.RETRIEVE_SUCESS_MESSAGE,
							metadataLogInformationDO.getSourceOrgId(),
							fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
									bOrgToken, bOrgURL, refreshToken,
									Constants.CustomBaseOrgID));
					// nullify connection
					fdGetSFoAuthHandleService.setSfHandleToNUll();
				} else {
					// Sleep for few seconds to let status updated to
					// "Processing"
					Thread.sleep(Constants.waitFor2Sec);
				}
			}
		} catch (SFException e) {
			if (metadataLogInformationDO != null) {
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating metadataLog
				RDAppService.updateMetadataLogInformationStatus(
						metadataLogInformationDO, Constants.ERROR_STATUS,
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.CustomBaseOrgID));

				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating Deploy Details Information
				RDAppService.updateDeploymentDetailsInformation(metadataLogId,
						e.getMessage(), metadataLogInformationDO
								.getSourceOrgId(),
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.CustomBaseOrgID));
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
			} else {
				System.out.println("Salesforce Exception " + e.getMessage());
			}
		} 
		
		catch (PermissionException p) {
			if (metadataLogInformationDO != null) {
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating metadataLog
				RDAppService.updateMetadataLogInformationStatus(
						metadataLogInformationDO, Constants.ERROR_STATUS,
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.CustomBaseOrgID));

				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating Deploy Details Information
				RDAppService.updateDeploymentDetailsInformation(metadataLogId,
						p.getMessage(), metadataLogInformationDO
								.getSourceOrgId(),
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.CustomBaseOrgID));
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());

				CustomerActivityDetails customerActivityDetails = new CustomerActivityDetails(
						customer.getId(), metadataLogInformationDO.getAction(),
						customer.getOrgId(), envSoureDO.getOrgId(), "",
						p.getMessage(), cal, (double) mainMBList.size());

				CustomerActivityDetailsDAO customerActivityDetailsDAO = new CustomerActivityDetailsDAO();
				customerActivityDetailsDAO.insert(customerActivityDetails,
						sfcusHandle);

			} 
		}
		
		catch (Exception e) {
			if (metadataLogInformationDO != null) {
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating metadataLog
				RDAppService.updateMetadataLogInformationStatus(
						metadataLogInformationDO, Constants.ERROR_STATUS,
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.CustomBaseOrgID));

				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating Deploy Details Information
				RDAppService.updateDeploymentDetailsInformation(metadataLogId,
						e.getMessage(), metadataLogInformationDO
								.getSourceOrgId(),
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.CustomBaseOrgID));
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
			} else {
				System.out.println("Salesforce Exception " + e.getMessage());
			}
		}
	}

	private List<MetaBean> getRetrieveObjListFromSource(String logName,
			SFoAuthHandle sfHandle, List<RefreshMetadataInformationDO> listtypes) {
		SFoAuthHandle sfSourceHandle = null;
		List<MetaBean> mainMBList = new ArrayList<MetaBean>();
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		try {

			if (listtypes != null) {

				for (Iterator iterator = listtypes.iterator(); iterator
						.hasNext();) {
					RefreshMetadataInformationDO refreshMetadataInformationDO = (RefreshMetadataInformationDO) iterator
							.next();

					String contentType = refreshMetadataInformationDO.getType();
					// getting list of objects from source
					FDGetComponentsTypesCompService getAllComponents = new FDGetComponentsTypesCompService();
					// refresh connection
					fdGetSFoAuthHandleService.setSfHandleToNUll();
					List<MetaBean> metaBeanList = getAllComponents
							.listMetadataObjects(logName, contentType, sfHandle);
					System.out.println("record size of " + contentType
							+ " is : " + metaBeanList.size());
					mainMBList.addAll(metaBeanList);
					if (sfSourceHandle != null) {
						sfSourceHandle.nullify();
					}
					sfSourceHandle = null;
					fdGetSFoAuthHandleService.setSfHandleToNUll();
				}
			} else {
				for (int k = 0; k < Constants.SFTypes.length; k++) {
					String contentType = Constants.SFTypes[k];
					// getting list of objects from source
					FDGetComponentsTypesCompService getAllComponents = new FDGetComponentsTypesCompService();
					// refresh connection
					fdGetSFoAuthHandleService.setSfHandleToNUll();
					List<MetaBean> metaBeanList = getAllComponents
							.listMetadataObjects(logName, contentType, sfHandle);
					System.out.println("record size of " + contentType
							+ " is : " + metaBeanList.size());
					mainMBList.addAll(metaBeanList);
					if (sfSourceHandle != null) {
						sfSourceHandle.nullify();
					}
					sfSourceHandle = null;
					fdGetSFoAuthHandleService.setSfHandleToNUll();
				}
			}
			System.out.println("Total record size of all contenttypes is : "
					+ mainMBList.size());
		} catch (Exception e) {
			if (sfSourceHandle != null) {
				sfSourceHandle.nullify();
			}
			sfSourceHandle = null;
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			throw new SFException(e.toString(),
					SFErrorCodes.SF_ListObject_Error);
		}
		return mainMBList;
	}

	private void doBulkInserts(List<MetaBean> mainMBList, String bOrgId,
			String bOrgToken, String bOrgURL, String refreshToken) {
		int chunkCount = 0, rem = 0, start = 0, end = Constants.ChunkSize;
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		if (mainMBList.size() > Constants.ChunkSize) {
			rem = (mainMBList.size() % Constants.ChunkSize);
			if ((mainMBList.size() % Constants.ChunkSize) > 0) {
				chunkCount = mainMBList.size() / Constants.ChunkSize + 1;
			} else {
				chunkCount = mainMBList.size() / Constants.ChunkSize;
			}
		} else {
			chunkCount = 1;
			end = mainMBList.size();
		}
		System.out.println(chunkCount);

		// updating records
		for (int i = 0; i < chunkCount; i++) {
			System.out.println("Record Update start: " + start + " ~ end: "
					+ end);
			System.out.println("Updating " + (i + 1) + " set of "
					+ Constants.ChunkSize + " records out of total "
					+ mainMBList.size() + " records" + " with start : " + start
					+ " end :" + end);
			List<MetaBean> l = mainMBList.subList(start, end);
			CsvFileWriter1.writeCsvFile(l, Constants.Retrieve_CSV_File);
			BulkInsertService bulkService = new BulkInsertService();
			bulkService.insertInto(
					Constants.MetadataDescriptionInformation_Name,
					Constants.Retrieve_CSV_File, fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.CustomBaseOrgID));
			if ((mainMBList.size() - end) > Constants.ChunkSize) {
				start = end;
				end = start + (Constants.ChunkSize);
			} else {
				start = end;
				end = start + rem;
			}
			fdGetSFoAuthHandleService.setSfHandleToNUll();
		}
	}

	private void doBulkDeletes(List<MetaBean> mainMBList, String bOrgId,
			String bOrgToken, String bOrgURL, String refreshToken) {
		int chunkCount = 0, rem = 0, start = 0, end = Constants.ChunkSize;
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		if (mainMBList.size() > Constants.ChunkSize) {
			rem = (mainMBList.size() % Constants.ChunkSize);
			if ((mainMBList.size() % Constants.ChunkSize) > 0) {
				chunkCount = mainMBList.size() / Constants.ChunkSize + 1;
			} else {
				chunkCount = mainMBList.size() / Constants.ChunkSize;
			}
		} else {
			chunkCount = 1;
			end = mainMBList.size();
		}
		System.out.println(chunkCount);

		// updating records
		for (int i = 0; i < chunkCount; i++) {
			System.out.println("Record Update start: " + start + " ~ end: "
					+ end);
			System.out.println("Updating " + (i + 1) + " set of "
					+ Constants.ChunkSize + " records out of total "
					+ mainMBList.size() + " records" + " with start : " + start
					+ " end :" + end);
			List<MetaBean> l = mainMBList.subList(start, end);
			CsvFileWriter1.writeCsvFile1(l, Constants.Retrieve_CSV_File);
			BulkDeleteService bulkService = new BulkDeleteService();
			bulkService.deleteFrom(
					Constants.MetadataDescriptionInformation_Name,
					Constants.Retrieve_CSV_File, fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.CustomBaseOrgID));
			if ((mainMBList.size() - end) > Constants.ChunkSize) {
				start = end;
				end = start + (Constants.ChunkSize);
			} else {
				start = end;
				end = start + rem;
			}
			fdGetSFoAuthHandleService.setSfHandleToNUll();
		}
	}
}
