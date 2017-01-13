package com.services.application;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.domain.CustomerMasterDetails;
import com.domain.EnvironmentDO;
import com.domain.EnvironmentInformationDO;
import com.domain.ErrorLogBean;
import com.domain.MetadataLogInformationDO;
import com.domain.PackageCompInfoDO;
import com.domain.PackageComponentDO;
import com.domain.PackageDO;
import com.domain.PackageInformationDO;
import com.domain.ReleaseInformationDO;
import com.domain.ReleasePackageDO;
import com.domain.RepositoryClient;
import com.ds.salesforce.dao.comp.CustomerMasterDetailsDAO;
import com.ds.salesforce.dao.comp.DeployDetailsInformationDAO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.ds.salesforce.dao.comp.EnvironmentInformationDAO;
import com.ds.salesforce.dao.comp.PackageComponentDAO;
import com.ds.salesforce.dao.comp.PackageDAO;
import com.ds.salesforce.dao.comp.PackageInformationDAO;
import com.ds.salesforce.dao.comp.ReleaseInformationDAO;
import com.ds.salesforce.dao.comp.ReleasePackageDAO;
import com.ds.salesforce.dao.comp.RepositoryClientDAO;
import com.services.component.FDDeployCompService;
import com.services.component.FDGetSFoAuthHandleService;
import com.services.component.FDSFXMLPackageCompService;
import com.services.component.release.CreatePackage;
import com.services.component.release.CreatePackageComp;
import com.services.component.release.GetPkgCompList;
import com.util.Constants;
import com.util.CustomerProcess;
import com.util.GitRepoDO;
import com.util.Org;
import com.util.RepoUtil;
import com.util.SFoAuthHandle;

public class SubmitForApprovalService {
	Org bOrg;
	Org sOrg;
	Org tOrg;
	String status;
	String pkgId;
	String metadataLogId;
	boolean override;
	String gitUsername;
	String gitAccessToken;
	String gitURL;
	String bitBucketUsername;
	String bitBucketAccessToken;
	String bitBucketRefreshToken;
	String bitBucketURL;
	String repositoryId;
	public String check = "false";
	public MetadataLogInformationDO metadataLogInformationDO = null;

	public SubmitForApprovalService() {

	}

	public SubmitForApprovalService(Org bOrg, Org sOrg, String status,
			String pkgId, String metadataLogId, boolean override,
			String gitUsername, String gitAccessToken, String gitURL,
			String bitBucketUsername, String bitBucketAccessToken,
			String bitBucketRefreshToken, String bitBucketURL,
			String repositoryId) {
		this.bOrg = bOrg;
		this.sOrg = sOrg;
		this.status = status;
		this.pkgId = pkgId;
		this.metadataLogId = metadataLogId;
		this.override = override;
		this.gitUsername = gitUsername;
		this.gitAccessToken = gitAccessToken;
		this.gitURL = gitURL;
		this.bitBucketUsername = bitBucketUsername;
		this.bitBucketAccessToken = bitBucketAccessToken;
		this.bitBucketRefreshToken = bitBucketRefreshToken;
		this.bitBucketURL = bitBucketURL;
		this.repositoryId = repositoryId;
	}

	/*
	 * public void delPkgInfoListFromBase() { if (getsOrg() != null) {
	 * ReleaseInformationDAO riDAO = new ReleaseInformationDAO(); // Get the
	 * ReleaseInformation in the client environments. List<Object> relInfoList =
	 * riDAO.findByParentId(getReleaseId(),
	 * FDGetSFoAuthHandleService.getSFoAuthHandle(getsOrg()));
	 * 
	 * for (Iterator iterator = relInfoList.iterator(); iterator.hasNext();) {
	 * EnvironmentDO envDO = new EnvironmentDO(getsOrg().getOrgId(),
	 * getsOrg().getOrgToken(), getsOrg().getOrgURL(), "",
	 * getsOrg().getRefreshToken()); List<Object> pkgInfoList = (new
	 * GetPkgInfoList(relInfoList, envDO)).getList(); for (Iterator iterator2 =
	 * pkgInfoList.iterator(); iterator2 .hasNext();) { PackageInformationDO
	 * pkgInfoDO = (PackageInformationDO) iterator2 .next(); SFoAuthHandle
	 * sfhandle = FDGetSFoAuthHandleService .getSFoAuthHandle(gettOrg());
	 * delPkgInBase(pkgInfoDO, sfhandle); } } } }
	 */
	public void delPkgInBase(PackageInformationDO pkgInfoDO,
			SFoAuthHandle sfhandle) {
		// Deleting Packages From BaseOrg
		String[] ids = new String[1];

		PackageDAO pkgDAO = new PackageDAO();
		List<Object> pkgList = pkgDAO.findByParentId(pkgInfoDO.getId(),
				sfhandle);
		if (pkgList.size() > 0) {
			for (Iterator iterator = pkgList.iterator(); iterator.hasNext();) {
				PackageDO packageDO = (PackageDO) iterator.next();
				ids[0] = packageDO.getId();
				/*
				 * ReleasePackageDAO releasePackageDAO = new
				 * ReleasePackageDAO(); List<Object> releasePackageList =
				 * releasePackageDAO .findByPkgIDAndRID(ids[0],
				 * pkgInfoDO.getReleaseInformationId(), sfhandle);
				 * 
				 * for (Iterator iterator2 = releasePackageList.iterator();
				 * iterator2 .hasNext();) { ReleaseInformationDO object =
				 * (ReleaseInformationDO) iterator2.next();
				 * releasePackageDAO.delete(object, sfhandle);
				 * 
				 * }
				 */

				// releasePackageDAO.delete(obj, sfHandle)
				pkgDAO.deleteRecords(ids, sfhandle);

			}
			// delete Packages
		}
	}

	public String getMetadataLogId() {
		return metadataLogId;
	}

	public void setMetadataLogId(String metadataLogId) {
		this.metadataLogId = metadataLogId;
	}

	public void initiate() {
		ReleasePackageDAO relPkgDAO = new ReleasePackageDAO();
		ReleasePackageDO relPkgDO = null;
		String pid = "";
		CustomerMasterDetails customer = null;
		SFoAuthHandle sfcusHandle = null;

		// delete packages from Base
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		EnvironmentDO bEnvDO = new EnvironmentDO(getbOrg().getOrgId(),
				getbOrg().getOrgToken(), getbOrg().getOrgURL(), "", getbOrg()
						.getRefreshToken());
		EnvironmentDO sEnvDO = new EnvironmentDO(getsOrg().getOrgId(),
				getsOrg().getOrgToken(), getsOrg().getOrgURL(), "", getsOrg()
						.getRefreshToken());

		EnvironmentDAO environmentDAO = new EnvironmentDAO();
		List<Object> lst1 = environmentDAO.findById(getbOrg().getOrgId(),
				fdGetSFoAuthHandleService.getSFoAuthHandle(sEnvDO,
						Constants.CustomBaseOrgID));
		EnvironmentDO object1 = null;
		for (Iterator iterator = lst1.iterator(); iterator.hasNext();) {
			object1 = (EnvironmentDO) iterator.next();
			System.out.println("----" + object1.getId());

		}

		EnvironmentInformationDAO environmentInformationDAO = new EnvironmentInformationDAO();
		List<Object> lst3 = environmentInformationDAO.findById(getsOrg()
				.getOrgId(), fdGetSFoAuthHandleService.getSFoAuthHandle(bEnvDO,
				Constants.CustomBaseOrgID));
		EnvironmentInformationDO object2 = null;
		for (Iterator iterator = lst3.iterator(); iterator.hasNext();) {
			object2 = (EnvironmentInformationDO) iterator.next();
			System.out.println("----" + object2.getId());
			System.out.println("----" + object2.getBitBucketRefreshToken());

		}

		RepositoryClientDAO repositoryClientDAO = new RepositoryClientDAO();
		List<Object> lst2 = repositoryClientDAO.findById(repositoryId,
				fdGetSFoAuthHandleService.getSFoAuthHandle(bEnvDO,
						Constants.CustomBaseOrgID));
		RepositoryClient client = null;

		for (Iterator iterator = lst2.iterator(); iterator.hasNext();) {
			client = (RepositoryClient) iterator.next();
			System.out.println("Repository Id" + client.getId());

		}

		try {
			// Get the ReleaseInformation in the each of Source environments.
			PackageInformationDAO pkgInfoDAO = new PackageInformationDAO();

			List<Object> pkgInfoList = pkgInfoDAO.findById(pkgId,
					fdGetSFoAuthHandleService.getSFoAuthHandle(bEnvDO,
							Constants.CustomBaseOrgID));

			for (Iterator<Object> iterator2 = pkgInfoList.iterator(); iterator2
					.hasNext();) {
				PackageInformationDO pkgInfoDO = (PackageInformationDO) iterator2
						.next();
				if (pkgInfoDO.getReadyForDeployment() != null
						&& pkgInfoDO.getReadyForDeployment().booleanValue()) {

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					pkgInfoDO.setCalendar(calendar);
					System.out.println(pkgInfoDO.getCalendar().getTime());

					pkgInfoDAO.updatePackageRetrievedTime(pkgInfoDO,
							fdGetSFoAuthHandleService.getSFoAuthHandle(bEnvDO,

							Constants.CustomBaseOrgID));

					List<Object> currentCommitpkgCompList = (new GetPkgCompList(
							bEnvDO, pkgInfoDO.getId())).getListClient();

					// get Release Id From Current Commit Package ID

					System.out.println("ReleaseInformation ID"
							+ pkgInfoDO.getReleaseInformationId());

					// method for query for parentRelease Id from
					// ReleaseInformation Object

					String releaseId = getReleaseId(
							pkgInfoDO.getReleaseInformationId(),
							fdGetSFoAuthHandleService.getSFoAuthHandle(bEnvDO,
									Constants.CustomBaseOrgID));

					System.out.println("Release ID" + releaseId);

					// Run a Query to get any PackageId's are associated
					// with this release

					ReleasePackageDAO releasePackageDAO = new ReleasePackageDAO();
					List<Object> listofPackageIds = releasePackageDAO
							.findByReleaseId1(releaseId, pkgInfoDO.getId(),
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											sEnvDO, Constants.CustomBaseOrgID));

					if (listofPackageIds.size() > 0) {

						for (Iterator iterator = listofPackageIds.iterator(); iterator
								.hasNext();) {
							ReleasePackageDO releasePackageDO = (ReleasePackageDO) iterator
									.next();

							System.out.println("Package ID"
									+ releasePackageDO.getPackageC());
							System.out.println("isOverride" + isOverride());

							// getPackageComponents from PackageComponent
							// using Packid

							List<Object> listalreadyexistingCompoentns = getPackageComponentInformation(
									releasePackageDO.getPackageC(),
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											sEnvDO, Constants.CustomBaseOrgID));

							findwhetheComponentAlreadyExist(
									currentCommitpkgCompList,
									releasePackageDO.getPackageC(),
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											sEnvDO, Constants.CustomBaseOrgID),
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											bEnvDO, Constants.CustomBaseOrgID),
									isOverride());

						}

						if (isOverride()) {

							try {

								metadataLogInformationDO = RDAppService
										.findMetadataLogInformation(
												metadataLogId,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));

								System.out.println("MetadataInformation DAO"
										+ metadataLogInformationDO.toString());

								System.out.println("Package Parent Id"
										+ pkgInfoDO.getId());

								// write a query any package exists with That
								// Package Id

								List<Object> list = relPkgDAO
										.findByParentPkgID(
												pkgInfoDO.getId(),
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

								if (list.size() > 0) {
									for (Iterator iterator3 = list.iterator(); iterator3
											.hasNext();) {
										relPkgDO = (ReleasePackageDO) iterator3
												.next();
										pkgId = relPkgDO.getId();

										pid = relPkgDO.getPackageC();
										List<Object> components = getPackageComponentInformation(
												pid,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

										deleteComponents(
												components,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

										List<Object> pkgCompList = (new GetPkgCompList(
												bEnvDO, pkgInfoDO.getId()))
												.getListClient();

										sfcusHandle = new SFoAuthHandle(
												Constants.USERID,
												Constants.PASSWORD,
												Constants.Server_URL, "");
										CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();

										customer = customerMasterDetailsDAO
												.findByOrgId(getbOrg()
														.getOrgId(),
														sfcusHandle);

										CustomerProcess customerProcess = new CustomerProcess();
										/*if (customer != null) {
											customerProcess
													.process(
															customer,
															"Commit",
															pkgCompList.size(),
															getbOrg()
																	.getOrgId(),
															getsOrg()
																	.getOrgId(),
															sfcusHandle,
															fdGetSFoAuthHandleService
																	.getSFoAuthHandle(
																			sEnvDO,
																			Constants.CustomBaseOrgID),null);
										}*/

										(new CreatePackageComp(getsOrg(),
												pkgCompList))
												.create(pid,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID));

									}

								}

								else {
									pid = (new CreatePackage(getsOrg()))
											.create(pkgInfoDO,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));
									List<Object> pkgCompList = (new GetPkgCompList(
											bEnvDO, pkgInfoDO.getId()))
											.getListClient();

									sfcusHandle = new SFoAuthHandle(
											Constants.USERID,
											Constants.PASSWORD,
											Constants.Server_URL, "");
									CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();

									customer = customerMasterDetailsDAO
											.findByOrgId(getbOrg().getOrgId(),
													sfcusHandle);

									CustomerProcess customerProcess = new CustomerProcess();
									/*if (customer != null) {
										customerProcess
												.process(
														customer,
														"Commit",
														pkgCompList.size(),
														getbOrg().getOrgId(),
														getsOrg().getOrgId(),
														sfcusHandle,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID),null);
									}*/

									(new CreatePackageComp(getsOrg(),
											pkgCompList))
											.create(pid,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));
									ReleaseInformationDAO releaseInfoDAO = new ReleaseInformationDAO();
									List<Object> lst = releaseInfoDAO
											.findById(
													pkgInfoDO
															.getReleaseInformationId(),
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	bEnvDO,
																	Constants.CustomBaseOrgID));
									for (Iterator iterator = lst.iterator(); iterator
											.hasNext();) {
										ReleaseInformationDO object = (ReleaseInformationDO) iterator
												.next();
										relPkgDAO = new ReleasePackageDAO();

										// System.out.println("Source ID" +
										// environmentInformationDO.getId());

										relPkgDO = new ReleasePackageDO("1",
												pid,
												object.getParentReleaseID(),
												object1.getId());

										pkgId = relPkgDAO
												.insertAndGetId(
														relPkgDO,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID));

									}
								}
								// whether all the components same or not &
								// check
								// whether
								// this package associates with release

								PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
								List<Object> pcList = packageComponentDAO
										.findByPackageIdForCI(
												pid,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

								FDSFXMLPackageCompService xmlService = new FDSFXMLPackageCompService();
								xmlService.createPackageXML(pcList,
										metadataLogId);

								FDDeployCompService fdDeployCompService = new FDDeployCompService();
								SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
										.getSFoAuthHandle(bEnvDO,
												Constants.CustomBaseOrgID);
								fdDeployCompService
										.deployCompoentsToRepository(
												sfSourceHandle, metadataLogId,
												pcList);

								if ((repositoryId != null)
										|| (repositoryId != "") ) {
									if(client!=null)
									{
									repositoryCheckin(client, metadataLogId,
											sfSourceHandle, getBitBucketURL(),
											getGitURL());
									}
								}

								RDAppService
										.updateMetadataLogInformationStatus(
												metadataLogInformationDO,
												Constants.COMPLETED_STATUS,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));

								if (pkgId != "") {

									relPkgDAO
											.updateCommitStatus(
													relPkgDO,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID),	pkgId);

								}

								// get Package List

							}

							catch (Exception e) {
								RDAppService
										.updateMetadataLogInformationStatus(
												metadataLogInformationDO,
												Constants.ERROR_STATUS,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));

								// updating Deploy Details Information
								RDAppService
										.updateDeploymentDetailsInformation(
												metadataLogId,
												e.getMessage(),
												metadataLogInformationDO
														.getSourceOrgId(),
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));
							}

						}

						else if (!isOverride() && check.equals("false")) {

							List<Object> listofPackageId = releasePackageDAO
									.findByReleaseId2(releaseId, pkgInfoDO
											.getId(), fdGetSFoAuthHandleService
											.getSFoAuthHandle(sEnvDO,
													Constants.CustomBaseOrgID));

							if (listofPackageId.size() > 0) {

								for (Iterator iterator3 = listofPackageId
										.iterator(); iterator3.hasNext();) {

									relPkgDO = (ReleasePackageDO) iterator3
											.next();
									pkgId = relPkgDO.getId();

									pid = relPkgDO.getPackageC();
									List<Object> components = getPackageComponentInformation(
											pid,
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															sEnvDO,
															Constants.CustomBaseOrgID));

									deleteComponents(
											components,
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															sEnvDO,
															Constants.CustomBaseOrgID));

									List<Object> pkgCompList = (new GetPkgCompList(
											bEnvDO, pkgInfoDO.getId()))
											.getListClient();

									sfcusHandle = new SFoAuthHandle(
											Constants.USERID,
											Constants.PASSWORD,
											Constants.Server_URL, "");
									CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();

									customer = customerMasterDetailsDAO
											.findByOrgId(getbOrg().getOrgId(),
													sfcusHandle);

									CustomerProcess customerProcess = new CustomerProcess();
									/*if (customer != null) {
										customerProcess
												.process(
														customer,
														"Commit",
														pkgCompList.size(),
														getbOrg().getOrgId(),
														getsOrg().getOrgId(),
														sfcusHandle,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID),null);
									}*/

									(new CreatePackageComp(getsOrg(),
											pkgCompList))
											.create(pid,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));

								}

								PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
								List<Object> pcList = packageComponentDAO
										.findByPackageIdForCI(
												pid,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

								FDSFXMLPackageCompService xmlService = new FDSFXMLPackageCompService();
								xmlService.createPackageXML(pcList,
										metadataLogId);

								FDDeployCompService fdDeployCompService = new FDDeployCompService();
								SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
										.getSFoAuthHandle(bEnvDO,
												Constants.CustomBaseOrgID);
								fdDeployCompService
										.deployCompoentsToRepository(
												sfSourceHandle, metadataLogId,
												pcList);

								if ((repositoryId != null)
										|| (repositoryId != "")) {
									if(client!=null)
									{
									repositoryCheckin(client, metadataLogId,
											sfSourceHandle, getBitBucketURL(),
											getGitURL());
									}
								}

								metadataLogInformationDO = RDAppService
										.findMetadataLogInformation(
												metadataLogId,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));
								RDAppService
										.updateMetadataLogInformationStatus(
												metadataLogInformationDO,
												Constants.COMPLETED_STATUS,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));

								if (pkgId != "") {

									relPkgDAO
											.updateCommitStatus(
													relPkgDO,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID),
													pkgId);

								}
							} else {

								try {
									pid = (new CreatePackage(getsOrg()))
											.create(pkgInfoDO,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));

									List<Object> pkgCompList = (new GetPkgCompList(
											bEnvDO, pkgInfoDO.getId()))
											.getListClient();

									// whether all the components same or not &
									// check
									// whether
									// this package associates with release

									(new CreatePackageComp(getsOrg(),
											pkgCompList))
											.create(pid,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));
									ReleaseInformationDAO releaseInfoDAO = new ReleaseInformationDAO();
									List<Object> lst = releaseInfoDAO
											.findById(
													pkgInfoDO
															.getReleaseInformationId(),
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	bEnvDO,
																	Constants.CustomBaseOrgID));
									for (Iterator iterator = lst.iterator(); iterator
											.hasNext();) {
										ReleaseInformationDO object = (ReleaseInformationDO) iterator
												.next();
										relPkgDAO = new ReleasePackageDAO();

										// System.out.println("Source ID" +
										// environmentInformationDO.getId());

										relPkgDO = new ReleasePackageDO("1",
												pid,
												object.getParentReleaseID(),
												object1.getId());

										pkgId = relPkgDAO
												.insertAndGetId(
														relPkgDO,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID));

										PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
										List<Object> pcList = packageComponentDAO
												.findByPackageIdForCI(
														pid,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID));

										FDSFXMLPackageCompService xmlService = new FDSFXMLPackageCompService();
										xmlService.createPackageXML(pcList,
												metadataLogId);

										FDDeployCompService fdDeployCompService = new FDDeployCompService();
										SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
												.getSFoAuthHandle(
														bEnvDO,
														Constants.CustomBaseOrgID);
										fdDeployCompService
												.deployCompoentsToRepository(
														sfSourceHandle,
														metadataLogId, pcList);

										if ((repositoryId != null)
												|| (repositoryId != "")) {
											if(client!=null)
											{
											repositoryCheckin(client,
													metadataLogId,
													sfSourceHandle,
													getBitBucketURL(),
													getGitURL());
											}
										}

										metadataLogInformationDO = RDAppService
												.findMetadataLogInformation(
														metadataLogId,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		bEnvDO,
																		Constants.CustomBaseOrgID));
										RDAppService
												.updateMetadataLogInformationStatus(
														metadataLogInformationDO,
														Constants.COMPLETED_STATUS,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		bEnvDO,
																		Constants.CustomBaseOrgID));
										if (pkgId != "") {

											relPkgDAO
													.updateCommitStatus(
															relPkgDO,
															fdGetSFoAuthHandleService
																	.getSFoAuthHandle(
																			sEnvDO,
																			Constants.CustomBaseOrgID),
															pkgId);

										}

									}

									// get Package List

								}

								catch (Exception e) {
									RDAppService
											.updateMetadataLogInformationStatus(
													metadataLogInformationDO,
													Constants.ERROR_STATUS,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	bEnvDO,
																	Constants.CustomBaseOrgID));

									// updating Deploy Details Information
									RDAppService
											.updateDeploymentDetailsInformation(
													metadataLogId,
													e.getMessage(),
													metadataLogInformationDO
															.getSourceOrgId(),
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	bEnvDO,
																	Constants.CustomBaseOrgID));
								}
							}
						}
					} else if (listofPackageIds.size() == 0) {

						List<Object> listofPackageId = releasePackageDAO
								.findByReleaseId2(
										releaseId,
										pkgInfoDO.getId(),
										fdGetSFoAuthHandleService
												.getSFoAuthHandle(
														sEnvDO,
														Constants.CustomBaseOrgID));

						if (listofPackageId.size() > 0) {

							for (Iterator iterator3 = listofPackageId
									.iterator(); iterator3.hasNext();) {

								relPkgDO = (ReleasePackageDO) iterator3.next();
								pkgId = relPkgDO.getId();

								pid = relPkgDO.getPackageC();
								List<Object> components = getPackageComponentInformation(
										pid,
										fdGetSFoAuthHandleService
												.getSFoAuthHandle(
														sEnvDO,
														Constants.CustomBaseOrgID));

								deleteComponents(
										components,
										fdGetSFoAuthHandleService
												.getSFoAuthHandle(
														sEnvDO,
														Constants.CustomBaseOrgID));

								List<Object> pkgCompList = (new GetPkgCompList(
										bEnvDO, pkgInfoDO.getId()))
										.getListClient();

								sfcusHandle = new SFoAuthHandle(
										Constants.USERID, Constants.PASSWORD,
										Constants.Server_URL, "");
								CustomerMasterDetailsDAO customerMasterDetailsDAO = new CustomerMasterDetailsDAO();

								customer = customerMasterDetailsDAO
										.findByOrgId(getbOrg().getOrgId(),
												sfcusHandle);

								CustomerProcess customerProcess = new CustomerProcess();
								/*if (customer != null) {
									customerProcess
											.process(
													customer,
													"Commit",
													pkgCompList.size(),
													getbOrg().getOrgId(),
													getsOrg().getOrgId(),
													sfcusHandle,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID),null);
								}*/

								(new CreatePackageComp(getsOrg(), pkgCompList))
										.create(pid,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

							}

							PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
							List<Object> pcList = packageComponentDAO
									.findByPackageIdForCI(
											pid,
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															sEnvDO,
															Constants.CustomBaseOrgID));

							FDSFXMLPackageCompService xmlService = new FDSFXMLPackageCompService();
							xmlService.createPackageXML(pcList, metadataLogId);

							FDDeployCompService fdDeployCompService = new FDDeployCompService();
							SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
									.getSFoAuthHandle(bEnvDO,
											Constants.CustomBaseOrgID);
							fdDeployCompService.deployCompoentsToRepository(
									sfSourceHandle, metadataLogId, pcList);

							if ((repositoryId != null) || (repositoryId != "")) {
								if(client!=null)
								{
								repositoryCheckin(client, metadataLogId,
										sfSourceHandle, getBitBucketURL(),
										getGitURL());
								}
							} else {
								repositoryCheckinEnvironment(object2,
										metadataLogId, sfSourceHandle);

							}

							metadataLogInformationDO = RDAppService
									.findMetadataLogInformation(
											metadataLogId,
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															bEnvDO,
															Constants.CustomBaseOrgID));
							RDAppService.updateMetadataLogInformationStatus(
									metadataLogInformationDO,
									Constants.COMPLETED_STATUS,
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											bEnvDO, Constants.CustomBaseOrgID));

							if (pkgId != "") {

								relPkgDAO
										.updateCommitStatus(
												relPkgDO,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID),
												pkgId);

							}
						} else {

							try {
								pid = (new CreatePackage(getsOrg()))
										.create(pkgInfoDO,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));

								List<Object> pkgCompList = (new GetPkgCompList(
										bEnvDO, pkgInfoDO.getId()))
										.getListClient();

								// whether all the components same or not &
								// check
								// whether
								// this package associates with release

								(new CreatePackageComp(getsOrg(), pkgCompList))
										.create(pid,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));
								ReleaseInformationDAO releaseInfoDAO = new ReleaseInformationDAO();
								List<Object> lst = releaseInfoDAO
										.findById(
												pkgInfoDO
														.getReleaseInformationId(),
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));
								for (Iterator iterator = lst.iterator(); iterator
										.hasNext();) {
									ReleaseInformationDO object = (ReleaseInformationDO) iterator
											.next();
									relPkgDAO = new ReleasePackageDAO();

									// System.out.println("Source ID" +
									// environmentInformationDO.getId());

									relPkgDO = new ReleasePackageDO("1", pid,
											object.getParentReleaseID(),
											object1.getId());

									pkgId = relPkgDAO
											.insertAndGetId(
													relPkgDO,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));

									PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
									List<Object> pcList = packageComponentDAO
											.findByPackageIdForCI(
													pid,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));

									FDSFXMLPackageCompService xmlService = new FDSFXMLPackageCompService();
									xmlService.createPackageXML(pcList,
											metadataLogId);

									FDDeployCompService fdDeployCompService = new FDDeployCompService();
									SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
											.getSFoAuthHandle(bEnvDO,
													Constants.CustomBaseOrgID);
									fdDeployCompService
											.deployCompoentsToRepository(
													sfSourceHandle,
													metadataLogId, pcList);

									if ((repositoryId != null)
											|| (repositoryId != "") ) {
										if(client!=null)
										{
										repositoryCheckin(client,
												metadataLogId, sfSourceHandle,
												getBitBucketURL(), getGitURL());
										}
									} else {
										repositoryCheckinEnvironment(object2,
												metadataLogId, sfSourceHandle);

									}

									metadataLogInformationDO = RDAppService
											.findMetadataLogInformation(
													metadataLogId,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	bEnvDO,
																	Constants.CustomBaseOrgID));
									RDAppService
											.updateMetadataLogInformationStatus(
													metadataLogInformationDO,
													Constants.COMPLETED_STATUS,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	bEnvDO,
																	Constants.CustomBaseOrgID));
									if (pkgId != "") {

										relPkgDAO
												.updateCommitStatus(
														relPkgDO,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		sEnvDO,
																		Constants.CustomBaseOrgID),
														pkgId);

									}

								}

								// get Package List

							}

							catch (Exception e) {
								RDAppService
										.updateMetadataLogInformationStatus(
												metadataLogInformationDO,
												Constants.ERROR_STATUS,
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));

								// updating Deploy Details Information
								RDAppService
										.updateDeploymentDetailsInformation(
												metadataLogId,
												e.toString(),
												metadataLogInformationDO
														.getSourceOrgId(),
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																bEnvDO,
																Constants.CustomBaseOrgID));
							}

							PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
							List<Object> pcList = packageComponentDAO
									.findByPackageIdForCI(
											pkgInfoDO.getId(),
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															sEnvDO,
															Constants.CustomBaseOrgID));

							if (pcList != null) {
								FDSFXMLPackageCompService xmlService = new FDSFXMLPackageCompService();
								xmlService.createPackageXML(pcList,
										metadataLogId);

								FDDeployCompService fdDeployCompService = new FDDeployCompService();
								SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
										.getSFoAuthHandle(bEnvDO,
												Constants.CustomBaseOrgID);
								fdDeployCompService
										.deployCompoentsToRepository(
												sfSourceHandle, metadataLogId,
												pcList);
								if ((repositoryId != null)
										|| (repositoryId != "")) {
									if(client!=null)
									{
									repositoryCheckin(client, metadataLogId,
											sfSourceHandle, getBitBucketURL(),
											getGitURL());
									}
								} else {
									repositoryCheckinEnvironment(object2,
											metadataLogId, sfSourceHandle);

								}

							}
							metadataLogInformationDO = RDAppService
									.findMetadataLogInformation(
											metadataLogId,
											fdGetSFoAuthHandleService
													.getSFoAuthHandle(
															bEnvDO,
															Constants.CustomBaseOrgID));
							RDAppService.updateMetadataLogInformationStatus(
									metadataLogInformationDO,
									Constants.COMPLETED_STATUS,
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											bEnvDO, Constants.CustomBaseOrgID));

						}
					}

				}
			}

		} catch (Exception e) {
			RDAppService.updateDeploymentDetailsInformation(metadataLogId, e
					.toString(), "", fdGetSFoAuthHandleService
					.getSFoAuthHandle(bEnvDO, Constants.CustomBaseOrgID));
			metadataLogInformationDO = RDAppService.findMetadataLogInformation(
					metadataLogId, fdGetSFoAuthHandleService.getSFoAuthHandle(
							bEnvDO, Constants.CustomBaseOrgID));
			RDAppService.updateMetadataLogInformationStatus(
					metadataLogInformationDO, Constants.COMPLETED_STATUS,
					fdGetSFoAuthHandleService.getSFoAuthHandle(bEnvDO,
							Constants.CustomBaseOrgID));
		}

	}

	public Org getsOrg() {
		return sOrg;
	}

	public void setsOrg(Org sOrg) {
		this.sOrg = sOrg;
	}

	public Org gettOrg() {
		return tOrg;
	}

	public void settOrg(Org tOrg) {
		this.tOrg = tOrg;
	}

	public Org getbOrg() {
		return bOrg;
	}

	public void setbOrg(Org bOrg) {
		this.bOrg = bOrg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public String getGitUsername() {
		return gitUsername;
	}

	public void setGitUsername(String gitUsername) {
		this.gitUsername = gitUsername;
	}

	public String getGitAccessToken() {
		return gitAccessToken;
	}

	public void setGitAccessToken(String gitAccessToken) {
		this.gitAccessToken = gitAccessToken;
	}

	public String getGitURL() {
		return gitURL;
	}

	public void setGitURL(String gitURL) {
		this.gitURL = gitURL;
	}

	public String getBitBucketUsername() {
		return bitBucketUsername;
	}

	public void setBitBucketUsername(String bitBucketUsername) {
		this.bitBucketUsername = bitBucketUsername;
	}

	public String getBitBucketAccessToken() {
		return bitBucketAccessToken;
	}

	public void setBitBucketAccessToken(String bitBucketAccessToken) {
		this.bitBucketAccessToken = bitBucketAccessToken;
	}

	public String getBitBucketRefreshToken() {
		return bitBucketRefreshToken;
	}

	public void setBitBucketRefreshToken(String bitBucketRefreshToken) {
		this.bitBucketRefreshToken = bitBucketRefreshToken;
	}

	public String getBitBucketURL() {
		return bitBucketURL;
	}

	public void setBitBucketURL(String bitBucketURL) {
		this.bitBucketURL = bitBucketURL;
	}

	public MetadataLogInformationDO getMetadataLogInformationDO() {
		return metadataLogInformationDO;
	}

	public void setMetadataLogInformationDO(
			MetadataLogInformationDO metadataLogInformationDO) {
		this.metadataLogInformationDO = metadataLogInformationDO;
	}

	public String getReleaseId(String releaseInformationId,
			SFoAuthHandle sfHandle) {

		ReleaseInformationDO reInformationDO = null;
		String releaseId = null;
		ReleaseInformationDAO releaseInformationDAO = new ReleaseInformationDAO();
		List<Object> list = releaseInformationDAO.findById(
				releaseInformationId, sfHandle);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			reInformationDO = (ReleaseInformationDO) iterator.next();
			releaseId = reInformationDO.getParentReleaseID();
		}

		return releaseId;

	}

	public static void deleteComponents(List<Object> l, SFoAuthHandle sfHandle) {

		String[] ids = new String[1];
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			PackageComponentDO pComponentDO = (PackageComponentDO) iterator
					.next();
			ids[0] = pComponentDO.getId();
			PackageDAO pComponentDAO = new PackageDAO();
			pComponentDAO.deleteRecords(ids, sfHandle);

		}

	}

	public static List<Object> getPackageComponentInformation(String packageId,
			SFoAuthHandle sfHandle) {

		PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
		List<Object> list = packageComponentDAO.findByPackageId1(packageId,
				sfHandle);

		return list;
	}

	public void repositoryCheckinEnvironment(EnvironmentInformationDO object2,
			String metadataLogId, SFoAuthHandle sfHandle) {
		EnvironmentInformationDAO eDao = new EnvironmentInformationDAO();

		if (object2.getGitUsername() != null) {

			System.out
					.println("Verion Control is Yes We can now Check in the files");
			System.out.println("Git URL :" + object2.getGitURL());
			System.out.println("Git USERNAME :" + object2.getGitUsername());

			System.out.println("GIT PASSWORD" + object2.getGitAccesstoken());

			GitRepoDO gitRepoDO = new GitRepoDO(object2.getGitUsername(),
					object2.getGitAccesstoken(), object2.getGitURL());
			try {
				RepoUtil.CheckIn(gitRepoDO, metadataLogId, "gitRepo");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (object2.getBitBucketUsername() != null) {

			System.out
					.println("Version Control is Yes We can now Check in the files");
			System.out.println("BIT URL :" + object2.getBitBucketURL());
			System.out.println("BIT USERNAME :"
					+ object2.getBitBucketUsername());

			System.out.println("BIT PASSWORD"
					+ object2.getBitBucketAccessToken());

			GitRepoDO gitRepoDO = new GitRepoDO(object2.getBitBucketUsername(),
					object2.getBitBucketAccessToken(),
					object2.getBitBucketRefreshToken(),

					getBitBucketURL());
			Git git = null;
			try {

				File checkOutDir = new File(Constants.CheckoutPath1);
				CloneCommand cc = new CloneCommand()
						.setCredentialsProvider(
								getCredentialsProviderForBitBucket(gitRepoDO))
						.setDirectory(checkOutDir)
						.setURI(gitRepoDO.getBitURL());

				git = cc.call();
				RepoUtil.CheckIn(gitRepoDO, metadataLogId, "BitBucketRepo");

			} catch (Exception e) {

				System.out.println("In Exception Block");
				git = null;

				try {
					String accessToken = getAccessToken(gitRepoDO
							.getBitBucketRefreshToken());
					System.out.println("New Access Token...." + accessToken);
					gitRepoDO.setBitBucketAccessToken(accessToken);
					object2.setBitBucketAccessToken(accessToken);
					eDao.updateBitbucketAccessToken(object2, sfHandle);

					RepoUtil.CheckIn(gitRepoDO, metadataLogId, "BitBucketRepo");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}

	}

	@SuppressWarnings("unused")
	public void repositoryCheckin(RepositoryClient object2,
			String metadataLogId, SFoAuthHandle sfHandle, String bitBucketURL,
			String gitURL) {
		RepositoryClientDAO reDao = new RepositoryClientDAO();

		if (object2.getType().equals("GIT")) {

			System.out.println("Git USERNAME :" + object2.getUsername());

			System.out.println("GIT PASSWORD" + object2.getAccessToken());
			System.out.println("GIT URL" + gitURL);

			try {
				GitRepoDO gitRepoDO = null;
				if (gitURL != null && gitURL != "") {
					System.out.println("Git URL :" + gitURL);

					gitRepoDO = new GitRepoDO(object2.getUsername(),
							object2.getAccessToken(), gitURL);
					RepoUtil.CheckIn(gitRepoDO, metadataLogId, "gitRepo");
					String msg = "Files are check in to " + "" + gitURL;
					RDAppService.updateDeploymentDetailsInformation(
							metadataLogId, msg, sfHandle.getOrgId(), sfHandle);

				}

				else {
					RDAppService
							.updateDeploymentDetailsInformation(
									metadataLogId,
									"Your GitHUB URL is Empty Please Populate to Continue your Check in Process",
									sfHandle.getOrgId(), sfHandle);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (object2.getType().equals("BitBucket")) {

			System.out.println("BIT USERNAME :" + object2.getUsername());

			System.out.println("BIT PASSWORD" + object2.getAccessToken());
			System.out.println("BIT RefreshToken" + object2.getRefreshToken());
			System.out.println("Bitbucket URL" + bitBucketURL);

			if (bitBucketURL != null && bitBucketURL != "") {
				System.out.println("Bitbucket URL" + bitBucketURL);

				GitRepoDO gitRepoDO = new GitRepoDO(object2.getUsername(),
						object2.getAccessToken(), object2.getRefreshToken(),

						bitBucketURL);

				Git git = null;
				try {

					File checkOutDir = new File(Constants.CheckoutPath1);
					CloneCommand cc = new CloneCommand()
							.setCredentialsProvider(
									getCredentialsProviderForBitBucket(gitRepoDO))
							.setDirectory(checkOutDir)
							.setURI(gitRepoDO.getBitURL());

					git = cc.call();
					RepoUtil.CheckIn(gitRepoDO, metadataLogId, "BitBucketRepo");
					String msg = "Files are check in to " + "" + bitBucketURL;
					RDAppService.updateDeploymentDetailsInformation(
							metadataLogId, msg, sfHandle.getOrgId(), sfHandle);

				} catch (Exception e) {

					System.out.println("In Exception Block");
					git = null;

					try {
						String accessToken = getAccessToken(gitRepoDO
								.getBitBucketRefreshToken());
						System.out
								.println("New Access Token...." + accessToken);
						gitRepoDO.setBitBucketAccessToken(accessToken);
						object2.setAccessToken(accessToken);
						reDao.updateBitbucketAccessToken(object2, sfHandle);

						RepoUtil.CheckIn(gitRepoDO, metadataLogId,
								"BitBucketRepo");
						String msg = "Files are check in to " + ""
								+ bitBucketURL;
						RDAppService.updateDeploymentDetailsInformation(
								metadataLogId, msg, sfHandle.getOrgId(),
								sfHandle);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} else {
				RDAppService
						.updateDeploymentDetailsInformation(
								metadataLogId,
								"Your BitBucket URL is Empty Please Populate to Continue your Check in Process",
								sfHandle.getOrgId(), sfHandle);

			}

		}
	}

	public static String getAccessToken(String refreshToken)
			throws HttpException, IOException {

		String accessToken = "";
		String tokenUrl = "https://bitbucket.org/site/oauth2/access_token";
		String clientId = "9nevKHn7BRTGL3zkCQ";
		String clientSecret = "f6ZNukEbJxXbLdMhrabQvXgtEdgeLvSf";
		HttpClient httpclient = new HttpClient();
		PostMethod post = new PostMethod(tokenUrl);
		// post.addParameter("code", code);
		post.addParameter("grant_type", "refresh_token");
		post.addParameter("client_id", clientId);
		post.addParameter("client_secret", clientSecret);
		post.addParameter("refresh_token", refreshToken);

		HttpConnectionManager conManager = httpclient
				.getHttpConnectionManager();
		httpclient.getHostConfiguration().setProxy(
				"us-east-static-02.quotaguard.com", 9293);
		HttpState state = new HttpState();
		state.setProxyCredentials(null, null, new UsernamePasswordCredentials(
				"quotaguard7898", "4ffb21b2078d"));
		httpclient.setState(state);
		/*
		 * HttpConnectionManager conManager = httpclient
		 * .getHttpConnectionManager();
		 * httpclient.getHostConfiguration().setProxy(
		 * "us-east-1-static-hopper.quotaguard.com", 9293); HttpState state =
		 * new HttpState(); state.setProxyCredentials(null, null, new
		 * UsernamePasswordCredentials("quotaguard5120", "f9daf5b7d721"));
		 * httpclient.setState(state);
		 */

		httpclient.executeMethod(post);
		try {
			JSONObject authResponse = new JSONObject(new JSONTokener(
					new InputStreamReader(post.getResponseBodyAsStream())));
			accessToken = authResponse.getString("access_token");
			System.out.println(authResponse.toString());
			// System.out.println(accessToken);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return accessToken;

	}

	public static CredentialsProvider getCredentialsProvider(GitRepoDO gitRepoDO) {
		// credentials
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(
				gitRepoDO.getUserName(), gitRepoDO.getPassword());
		return cp;
	}

	public static CredentialsProvider getCredentialsProviderForBitBucket(
			GitRepoDO gitRepoDO) {
		// credentials
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(
				gitRepoDO.getBitBucketUsername(),
				gitRepoDO.getBitBucketAccessToken());
		return cp;
	}

	public void findwhetheComponentAlreadyExist(List<Object> list,
			String PackageId, SFoAuthHandle sfHandle,
			SFoAuthHandle sfBAuthHandle, boolean override) {

		PackageComponentDAO pComponentDAO = new PackageComponentDAO();
		PackageDAO packageDAO = new PackageDAO();
		String[] ids = new String[1];

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			PackageCompInfoDO packageCompInfoDO = (PackageCompInfoDO) iterator
					.next();
			List<Object> existsList = pComponentDAO.findByPIDComponentType(
					PackageId, packageCompInfoDO.getObjName(),
					packageCompInfoDO.getType(), sfHandle);

			if (existsList.size() > 0) {

				// check any other override functionality is enable

				if (override) {

					// delete that component from existing package

					for (Iterator iterator2 = existsList.iterator(); iterator2
							.hasNext();) {
						PackageComponentDO packageComponentDO = (PackageComponentDO) iterator2
								.next();
						System.out.println(packageComponentDO.getId());
						ids[0] = packageComponentDO.getId();
						pComponentDAO.deleteRecords(ids, sfHandle);

					}

				}

				else {
					check = "true";
					List<Object> packageDetails = packageDAO.findById(
							PackageId, sfHandle);
					String sourceenvName = "";
					for (Iterator iterator2 = packageDetails.iterator(); iterator2
							.hasNext();) {

						ReleasePackageDAO releasePackageDAO = new ReleasePackageDAO();
						List<Object> list1 = releasePackageDAO.findByPkgID(
								PackageId, sfHandle);
						for (Iterator iterator3 = list1.iterator(); iterator3
								.hasNext();) {
							ReleasePackageDO object = (ReleasePackageDO) iterator3
									.next();
							System.out.println("SourceEnvironment Name"
									+ object.getSourceEnvName());
							sourceenvName = object.getSourceEnvName();

						}

						PackageDO packageDO = (PackageDO) iterator2.next();

						ErrorLogBean err = new ErrorLogBean();
						err.setErrorMessage("Component" + " "
								+ packageCompInfoDO.getObjName() + " "
								+ " type" + packageCompInfoDO.getType()
								+ " in Package " + packageDO.getName()
								+ " From Organisation " + sourceenvName
								+ " already exist");
						err.setName(packageCompInfoDO.getObjName());
						err.setType(packageCompInfoDO.getType());
						err.setMetadataLogId(metadataLogId);
						DeployDetailsInformationDAO dao = new DeployDetailsInformationDAO();
						dao.insert(err, sfBAuthHandle);
						System.out.println("Activity Table Data Insert");

					}

					metadataLogInformationDO = RDAppService
							.findMetadataLogInformation(metadataLogId,
									sfBAuthHandle);
					RDAppService.updateMetadataLogInformationStatus(
							metadataLogInformationDO,
							Constants.COMPLETED_STATUS, sfBAuthHandle);
				}
			}

		}

	}
}
