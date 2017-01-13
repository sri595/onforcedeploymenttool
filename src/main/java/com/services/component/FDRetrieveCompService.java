package com.services.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.domain.CustomerActivityDetails;
import com.domain.CustomerMasterDetails;
import com.domain.EnvironmentDO;
import com.domain.MetaBean;
import com.domain.MetadataLogDO;
import com.domain.RefreshMetadataDO;
import com.ds.salesforce.dao.comp.CustomerActivityDetailsDAO;
import com.ds.salesforce.dao.comp.CustomerMasterDetailsDAO;
import com.ds.salesforce.dao.comp.MetadataDescriptionDAO;
import com.ds.salesforce.dao.comp.RefreshMetadataDAO;
import com.exception.PermissionException;
import com.exception.SFErrorCodes;
import com.exception.SFException;
import com.services.application.RDAppService;
import com.tasks.PreProcessingTask;
import com.util.Constants;
import com.util.CsvFileWriter;
import com.util.CustomerProcess;
import com.util.SFoAuthHandle;
import com.util.oauth.RefreshTokens;

public class FDRetrieveCompService {

	public FDRetrieveCompService() {
		super();
	}

	public void retrieve(String bOrgId, String bOrgToken, String bOrgURL,
			String refreshToken, String metadataLogId) {

		MetadataLogDO metadataLogDO = null;
		SFoAuthHandle sfcusHandle = null;
		EnvironmentDO envSoureDO = null;
		List<MetaBean> mainMBList = null;
		CustomerMasterDetails customer = null;

		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		// do pre-processing
		// does some sanity checks on input variables
		// updates the refreshed tokens in Environment
		PreProcessingTask preProcessingTask = new PreProcessingTask(bOrgId,
				bOrgToken, bOrgURL, refreshToken, Constants.BaseOrgID,
				metadataLogId);
		bOrgToken = preProcessingTask.doPreProcess();
		// get refreshed base token
		/*
		 * EnvironmentDO envDO = RDAppService.getEnv(bOrgId,
		 * FDGetSFoAuthHandleService.getSFoAuthHandle(bOrgId, bOrgToken,
		 * bOrgURL, refreshToken, true)); bOrgToken = envDO.getToken();
		 */

		try {
			// Get Meta data Log details
			metadataLogDO = RDAppService.findMetadataLog(metadataLogId,
					fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
							bOrgToken, bOrgURL, refreshToken,
							Constants.BaseOrgID));
			// nullify connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();

			// updating metadataLog to prcessing state
			RDAppService.updateMetadataLogStatus(metadataLogDO,
					Constants.PROCESSING_STATUS, fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.BaseOrgID));
			// nullify connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();

			if (metadataLogDO.getAction() != null
					&& (metadataLogDO.getAction().equals("Retrieve"))) {
				if (metadataLogDO.getStatus() != null
						&& (metadataLogDO.getStatus()
								.equals(Constants.PROCESSING_STATUS))) {
					System.out.println("Retrieve------");

					// check weather BaseorgId Exists in Customer Master Table
					// if exists continue process

					sfcusHandle = new SFoAuthHandle(Constants.USERID,
							Constants.PASSWORD, Constants.Server_URL, "");
					CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();

					customer = customerMasterDetailsDAO.findByOrgId(bOrgId,
							sfcusHandle);

					CustomerProcess customerProcess = new CustomerProcess();

					// refresh connection
					fdGetSFoAuthHandleService.setSfHandleToNUll();
					// get Source Env Details
					RDAppService rdAppService = new RDAppService();
					 envSoureDO = rdAppService.getEnv(
							metadataLogDO.getSourceOrgId(),
							fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
									bOrgToken, bOrgURL, refreshToken,
									Constants.BaseOrgID));

					RefreshTokens refreshTokens1 = new RefreshTokens();
					String newSToken = refreshTokens1.refreshCustomSFHandle(
							envSoureDO, bOrgId, bOrgToken, bOrgURL,
							refreshToken);
					envSoureDO.setToken(newSToken);

					RefreshMetadataDAO refreshMetadataDAO = new RefreshMetadataDAO();

					// query refreshmetadata for metadatalog id and get all
					// types

					List<RefreshMetadataDO> listfromrefreshMetadataTypes = refreshMetadataDAO
							.findById1(metadataLogId, fdGetSFoAuthHandleService
									.getSFoAuthHandle(bOrgId, bOrgToken,
											bOrgURL, refreshToken,
											Constants.BaseOrgID));
					List<MetaBean> metabeanListFromDbb = new ArrayList<MetaBean>();
					List<MetaBean> metabeanListFromDb = null;

					MetadataDescriptionDAO metadataDescriptionDAO = new MetadataDescriptionDAO();

					if (listfromrefreshMetadataTypes.size() > 0) {
						for (Iterator<RefreshMetadataDO> iterator = listfromrefreshMetadataTypes
								.iterator(); iterator.hasNext();) {
							RefreshMetadataDO refreshMetadataDO = (RefreshMetadataDO) iterator
									.next();

							metabeanListFromDb = metadataDescriptionDAO
									.findById1(
											metadataLogDO.getId(),
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(bOrgId,
															bOrgToken, bOrgURL,
															refreshToken,
															Constants.BaseOrgID),
											envSoureDO.getOrgId(), bOrgId,
											bOrgToken, bOrgURL, refreshToken,
											refreshMetadataDO.getType());

							if (metabeanListFromDb.size() > 0)
								metabeanListFromDbb.addAll(metabeanListFromDb);

						}
						if (metabeanListFromDbb.size() > 0) {
							doBulkDeletes(metabeanListFromDbb, bOrgId,
									bOrgToken, bOrgURL, refreshToken);
						}

						fdGetSFoAuthHandleService.setSfHandleToNUll();
						 mainMBList = getRetrieveObjListFromSource(
								metadataLogDO.getLogName(),
								fdGetSFoAuthHandleService.getSFoAuthHandle(
										envSoureDO, Constants.CustomOrgID),
								listfromrefreshMetadataTypes);

						System.out.println("source Organization Id "
								+ envSoureDO.getOrgId());
						/*if (customer != null) {
							customerProcess.process(customer,
									metadataLogDO.getAction(),
									mainMBList.size(), envSoureDO.getOrgId(),
									"", sfcusHandle,fdGetSFoAuthHandleService
									.getSFoAuthHandle(bOrgId,
											bOrgToken, bOrgURL,
											refreshToken,
											Constants.BaseOrgID),null);
						}*/

						// Do bulk inserts in Base Env
						doBulkInserts(mainMBList, bOrgId, bOrgToken, bOrgURL,
								refreshToken);

						// Update Success message
						fdGetSFoAuthHandleService.setSfHandleToNUll();

					} else {

						metabeanListFromDb = metadataDescriptionDAO.findById1(
								metadataLogDO.getId(),
								fdGetSFoAuthHandleService.getSFoAuthHandle(
										bOrgId, bOrgToken, bOrgURL,
										refreshToken, Constants.BaseOrgID),
								envSoureDO.getOrgId(), bOrgId, bOrgToken,
								bOrgURL, refreshToken, null);
						metadataDescriptionDAO = null;
						if (metabeanListFromDb.size() > 0) {

							doBulkDeletes(metabeanListFromDb, bOrgId,
									bOrgToken, bOrgURL, refreshToken);

						}
						fdGetSFoAuthHandleService.setSfHandleToNUll();
					     mainMBList = getRetrieveObjListFromSource(
								metadataLogDO.getLogName(),
								fdGetSFoAuthHandleService.getSFoAuthHandle(
										envSoureDO, Constants.CustomOrgID),
								null);

						System.out.println("source Organization Id "
								+ envSoureDO.getOrgId());

						// Do bulk inserts in Base Env
						doBulkInserts(mainMBList, bOrgId, bOrgToken, bOrgURL,
								refreshToken);

						// Update Success message
						fdGetSFoAuthHandleService.setSfHandleToNUll();

					}
					// delete components from metadata description table

					// refresh connection

					// updating metadataLog
					RDAppService.updateMetadataLogStatus(metadataLogDO,
							Constants.COMPLETED_STATUS,
							fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
									bOrgToken, bOrgURL, refreshToken,
									Constants.BaseOrgID));
					RDAppService.updateDeploymentDetails(metadataLogId,
							Constants.RETRIEVE_SUCESS_MESSAGE, metadataLogDO
									.getSourceOrgId(),
							fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
									bOrgToken, bOrgURL, refreshToken,
									Constants.BaseOrgID));
					// nullify connection
					fdGetSFoAuthHandleService.setSfHandleToNUll();

					// Sleep for few sec to let status updated to
					// "Processing"
					Thread.sleep(Constants.waitFor2Sec);
				}
			}

		} catch (SFException e) {
			if (metadataLogDO != null) {

				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating metadataLog
				RDAppService.updateMetadataLogStatus(metadataLogDO,
						Constants.ERROR_STATUS, fdGetSFoAuthHandleService
								.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
										refreshToken, Constants.BaseOrgID));

				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating Deploy Details
				RDAppService.updateDeploymentDetails(metadataLogId, e
						.getMessage(), metadataLogDO.getSourceOrgId(),
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.BaseOrgID));
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
			} else {
				System.out.println("Salesforce Exception " + e.getMessage());
			}
		} 
		catch (PermissionException p) {

			System.out.println(p.getMessage());

			// refresh connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			// updating metadataLog
			RDAppService.updateMetadataLogStatus(metadataLogDO,
					Constants.ERROR_STATUS, fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.BaseOrgID));

			// refresh connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			// updating Deploy Details
			RDAppService.updateDeploymentDetails(metadataLogId, p.getMessage(),
					metadataLogDO.getSourceOrgId(), fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.BaseOrgID));
			// refresh connection
			fdGetSFoAuthHandleService.setSfHandleToNUll();
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

			CustomerActivityDetails customerActivityDetails = new CustomerActivityDetails(
					customer.getId(), metadataLogDO.getAction(),
					customer.getOrgId(), envSoureDO.getOrgId(), "",
					p.getMessage(), cal, (double) mainMBList.size());

			CustomerActivityDetailsDAO customerActivityDetailsDAO = new CustomerActivityDetailsDAO();
			customerActivityDetailsDAO.insert(customerActivityDetails,
					sfcusHandle);

			System.out.println(p.getMessage());

		}
		
		catch (Exception e) {
			if (metadataLogDO != null) {
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating metadataLog
				RDAppService.updateMetadataLogStatus(metadataLogDO,
						Constants.ERROR_STATUS, fdGetSFoAuthHandleService
								.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
										refreshToken, Constants.BaseOrgID));

				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
				// updating Deploy Details
				RDAppService.updateDeploymentDetails(metadataLogId, e
						.getMessage(), metadataLogDO.getSourceOrgId(),
						fdGetSFoAuthHandleService.getSFoAuthHandle(bOrgId,
								bOrgToken, bOrgURL, refreshToken,
								Constants.BaseOrgID));
				// refresh connection
				fdGetSFoAuthHandleService.setSfHandleToNUll();
			} else {
				System.out.println("Salesforce Exception " + e.getMessage());
			}
		}

	}

	private List<MetaBean> getRetrieveObjListFromSource(String logName,
			SFoAuthHandle sfHandle, List<RefreshMetadataDO> typesList) {
		SFoAuthHandle sfSourceHandle = null;
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		List<MetaBean> mainMBList = new ArrayList<MetaBean>();

		try {

			if (typesList != null) {
				for (Iterator<RefreshMetadataDO> iterator = typesList
						.iterator(); iterator.hasNext();) {
					RefreshMetadataDO refreshMetadataDO = (RefreshMetadataDO) iterator
							.next();

					String contentType = refreshMetadataDO.getType();
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

		finally {

			fdGetSFoAuthHandleService = null;
		}
		return mainMBList;
	}

	private List<MetaBean> getRetrieveSourceComponentsFromTable(
			String sourceOrg, SFoAuthHandle sfHandle) {
		SFoAuthHandle sfSourceHandle = null;
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		List<MetaBean> mainMBList = new ArrayList<MetaBean>();
		try {

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
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		int chunkCount = 0, rem = 0, start = 0, end = Constants.ChunkSize;

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
			CsvFileWriter.writeCsvFile(l, Constants.Retrieve_CSV_File);
			BulkInsertService bulkService = new BulkInsertService();

			bulkService.insertInto(Constants.MetadataDescription_Name,
					Constants.Retrieve_CSV_File, fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.BaseOrgID));
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
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		int chunkCount = 0, rem = 0, start = 0, end = Constants.ChunkSize;

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
			CsvFileWriter.writeCsvFile1(l, Constants.Retrieve_CSV_File);
			BulkDeleteService bulkService = new BulkDeleteService();

			bulkService.deleteFrom(Constants.MetadataDescription_Name,
					Constants.Retrieve_CSV_File, fdGetSFoAuthHandleService
							.getSFoAuthHandle(bOrgId, bOrgToken, bOrgURL,
									refreshToken, Constants.BaseOrgID));
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
