package com.services.application;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.domain.EnvironmentDO;
import com.domain.EnvironmentInformationDO;
import com.domain.ErrorLogBean;
import com.domain.MetaBean;
import com.domain.MetadataLogInformationDO;
import com.domain.PackageCompInfoDO;
import com.domain.PackageComponentDO;
import com.domain.PackageDO;
import com.domain.PackageInformationDO;
import com.domain.ReleaseInformationDO;
import com.domain.ReleasePackageDO;
import com.ds.salesforce.dao.comp.DeployDetailsInformationDAO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.ds.salesforce.dao.comp.EnvironmentInformationDAO;
import com.ds.salesforce.dao.comp.PackageComponentDAO;
import com.ds.salesforce.dao.comp.PackageDAO;
import com.ds.salesforce.dao.comp.PackageInformationDAO;
import com.ds.salesforce.dao.comp.ReleaseInformationDAO;
import com.ds.salesforce.dao.comp.ReleasePackageDAO;
import com.services.component.FDDeployCompService;
import com.services.component.FDGetSFoAuthHandleService;
import com.services.component.FDSFXMLPackageCompService;
import com.services.component.release.CreatePackage;
import com.services.component.release.CreatePackageComp;
import com.services.component.release.GetPkgCompList;
import com.util.Constants;
import com.util.GitRepoDO;
import com.util.Org;
import com.util.RepoUtil;
import com.util.SFoAuthHandle;

public class SubmitForApprovalServicebkp {
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

	public SubmitForApprovalServicebkp(Org bOrg, Org sOrg, String status,
			String pkgId, String metadataLogId, boolean override,
			String gitUsername, String gitAccessToken, String gitURL,
			String bitBucketUsername, String bitBucketAccessToken,
			String bitBucketRefreshToken, String bitBucketURL) {
		super();
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

	public String checkPackageAlreadyExists(PackageInformationDO pkgInfoDO,
			SFoAuthHandle sfhandle) {
		// Deleting Packages From BaseOrg
		String[] ids = new String[1];

		PackageDAO pkgDAO = new PackageDAO();
		List<Object> pkgList = pkgDAO.findByParentId(pkgInfoDO.getId(),
				sfhandle);
		String pid = "";
		if (pkgList.size() > 0) {
			for (Iterator iterator = pkgList.iterator(); iterator.hasNext();) {
				PackageDO object = (PackageDO) iterator.next();
				pid = object.getId();

			}

		} else {
			pid = "false";
			// delete Packages
		}
		return pid;
	}

	public String getMetadataLogId() {
		return metadataLogId;
	}

	public void setMetadataLogId(String metadataLogId) {
		this.metadataLogId = metadataLogId;
	}

	public void initiate() {

		String pid = "";

		// delete packages from Base
		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();

		MetadataLogInformationDO metadataLogInformationDO = null;
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
					/*
					 * delPkgInBase(pkgInfoDO,
					 * fdGetSFoAuthHandleService.getSFoAuthHandle(sEnvDO,
					 * Constants.CustomBaseOrgID));
					 */
					String packagestatus = checkPackageAlreadyExists(pkgInfoDO,
							fdGetSFoAuthHandleService.getSFoAuthHandle(sEnvDO,
									Constants.CustomBaseOrgID));
					System.out.println("Override Value " + isOverride());

					pid = packagestatus;

					System.out.println("Package Exists in server or not.."
							+ packagestatus);

					if (packagestatus.equals("false")) {

						try {
							pid = (new CreatePackage(getsOrg())).create(
									pkgInfoDO, fdGetSFoAuthHandleService
											.getSFoAuthHandle(sEnvDO,
													Constants.CustomBaseOrgID));

							List<Object> pkgCompList = (new GetPkgCompList(
									bEnvDO, pkgInfoDO.getId())).getListClient();

							// whether all the components same or not & check
							// whether
							// this package associates with release

							(new CreatePackageComp(getsOrg(), pkgCompList))
									.create(pid, fdGetSFoAuthHandleService
											.getSFoAuthHandle(sEnvDO,
													Constants.CustomBaseOrgID));
							ReleaseInformationDAO releaseInfoDAO = new ReleaseInformationDAO();
							List<Object> lst = releaseInfoDAO.findById(
									pkgInfoDO.getReleaseInformationId(),
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											bEnvDO, Constants.CustomBaseOrgID));
							for (Iterator iterator = lst.iterator(); iterator
									.hasNext();) {
								ReleaseInformationDO object = (ReleaseInformationDO) iterator
										.next();
								ReleasePackageDAO relPkgDAO = new ReleasePackageDAO();

								// System.out.println("Source ID" +
								// environmentInformationDO.getId());

								ReleasePackageDO relPkgDO = new ReleasePackageDO(
										"1", pid, object.getParentReleaseID(),
										object1.getId());

								String pkgId = relPkgDAO
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
										getMetadataLogId());

								FDDeployCompService fdDeployCompService = new FDDeployCompService();
								SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
										.getSFoAuthHandle(bEnvDO,
												Constants.CustomBaseOrgID);
								fdDeployCompService
										.deployCompoentsToRepository(
												sfSourceHandle,
												getMetadataLogId(), pcList);

								System.out.println("Bit Username"
										+ getBitBucketUsername());
								if (getGitUsername() != "") {

									System.out
											.println("Verion Control is Yes We can now Check in the files");
									System.out.println("Git URL :"
											+ getGitURL());
									System.out.println("Git USERNAME :"
											+ getGitUsername());

									System.out.println("GIT PASSWORD"
											+ getGitAccessToken());

									GitRepoDO gitRepoDO = new GitRepoDO(
											getGitUsername(),
											getGitAccessToken(), getGitURL());
									RepoUtil.CheckIn(gitRepoDO,
											getMetadataLogId(),"");

								}

								if (getBitBucketUsername() != "") {

									System.out
											.println("Verion Control is Yes We can now Check in the files");
									System.out.println("BIT URL :"
											+ getBitBucketURL());
									System.out.println("BIT USERNAME :"
											+ getBitBucketUsername());

									System.out.println("BIT PASSWORD"
											+ getBitBucketAccessToken());

									GitRepoDO gitRepoDO = new GitRepoDO(
											getBitBucketUsername(),
											getBitBucketAccessToken(),
											getBitBucketURL());
									RepoUtil.CheckIn(gitRepoDO,
											getMetadataLogId(),"");

								}

								metadataLogInformationDO = RDAppService
										.findMetadataLogInformation(
												getMetadataLogId(),
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
							}

							// get Package List

						}

						catch (Exception e) {
							RDAppService.updateMetadataLogInformationStatus(
									metadataLogInformationDO,
									Constants.ERROR_STATUS,
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											bEnvDO, Constants.CustomBaseOrgID));

							// updating Deploy Details Information
							RDAppService.updateDeploymentDetailsInformation(
									metadataLogId, e.getMessage(),
									metadataLogInformationDO.getSourceOrgId(),
									fdGetSFoAuthHandleService.getSFoAuthHandle(
											bEnvDO, Constants.CustomBaseOrgID));
						}
					}

					else {
						// check whether this package associates with any
						// release

						ReleasePackageDAO releasePackageDAO = new ReleasePackageDAO();
						List<Object> releaseassocPackageList = releasePackageDAO
								.findByPkgID(pid, fdGetSFoAuthHandleService
										.getSFoAuthHandle(sEnvDO,
												Constants.CustomBaseOrgID));

						if (releaseassocPackageList.size() > 0) {

							List<Object> pkgCompList = (new GetPkgCompList(
									bEnvDO, pkgInfoDO.getId())).getListClient();

							for (Iterator iterator = pkgCompList.iterator(); iterator
									.hasNext();) {
								PackageCompInfoDO packageCompInfoDO = (PackageCompInfoDO) iterator
										.next();
								System.out.println("Component"
										+ packageCompInfoDO.getObjName());
								System.out.println("Component Type"
										+ packageCompInfoDO.getType());
								System.out
										.println("Component Package ID in client"
												+ packageCompInfoDO
														.getPkgInfoParentId());
								System.out
										.println("Component Source Organizatrion client"
												+ packageCompInfoDO
														.getSourceOrganizationId());

								System.out
										.println("Component Source Organizatrion client"
												+ packageCompInfoDO
														.getSourceOrganizationId());

								System.out.println("Package ID" + pid);

								// check whether each component already exist in
								// existing package release or not if yes add to
								// Activity Details table else add new component
								// to
								// to server directly
								PackageComponentDAO packageComponentDAO = new PackageComponentDAO();
								List<Object> existsList = packageComponentDAO
										.findByPIDComponentType(
												pid,
												packageCompInfoDO.getObjName(),
												packageCompInfoDO.getType(),
												fdGetSFoAuthHandleService
														.getSFoAuthHandle(
																sEnvDO,
																Constants.CustomBaseOrgID));
								if (existsList.size() > 0) {
									String[] ids = new String[1];

									for (Iterator iterator3 = existsList
											.iterator(); iterator3.hasNext();) {
										PackageComponentDO object = (PackageComponentDO) iterator3
												.next();

										if (isOverride()) {

											ids[0] = object.getId();
											packageComponentDAO
													.deleteRecords(
															ids,
															fdGetSFoAuthHandleService
																	.getSFoAuthHandle(
																			sEnvDO,
																			Constants.CustomBaseOrgID));

											PackageComponentDO packageComponentDO = new PackageComponentDO();
											packageComponentDO
													.setObjName(packageCompInfoDO
															.getObjName());
											packageComponentDO
													.setOrder(new Double(1.0));
											packageComponentDO
													.setPkgCompName(packageCompInfoDO
															.getPkgCompInfoName());
											packageComponentDO
													.setParentPackageCompID(packageCompInfoDO
															.getPkgInfoParentId());
											packageComponentDO
													.setPkgParentId(pid);
											packageComponentDO
													.setSourceOrganizationId(packageCompInfoDO
															.getSourceOrganizationId());
											packageComponentDO
													.setType(packageCompInfoDO
															.getType());
											packageComponentDAO
													.insert(packageComponentDO,
															fdGetSFoAuthHandleService
																	.getSFoAuthHandle(
																			sEnvDO,
																			Constants.CustomBaseOrgID));
											System.out
													.println("create Component List for inserting to existing package");

										} else if (!isOverride()) {

											System.out.println("in else if");
											PackageDAO packageDAO = new PackageDAO();
											List<Object> packageDetails = packageDAO
													.findById(
															pid,
															fdGetSFoAuthHandleService
																	.getSFoAuthHandle(
																			sEnvDO,
																			Constants.CustomBaseOrgID));
											for (Iterator iterator4 = packageDetails
													.iterator(); iterator4
													.hasNext();) {
												PackageDO object2 = (PackageDO) iterator4
														.next();
												ErrorLogBean err = new ErrorLogBean();
												err.setErrorMessage("Component"
														+ " "
														+ object.getObjName()
														+ " " + " type"
														+ object.getType()
														+ " in Package "
														+ object2.getName()
														+ " alredy exist");
												err.setName(object.getObjName());
												err.setType(object.getType());
												DeployDetailsInformationDAO dao = new DeployDetailsInformationDAO();
												dao.insert(
														err,
														fdGetSFoAuthHandleService
																.getSFoAuthHandle(
																		bEnvDO,
																		Constants.CustomBaseOrgID));

												metadataLogInformationDO = RDAppService
														.findMetadataLogInformation(
																getMetadataLogId(),
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
												System.out
														.println("Activity Table Data Insert");
											}

										}

									}
								} else {

									PackageComponentDAO packageComponentDAO2 = new PackageComponentDAO();
									PackageComponentDO packageComponentDO = new PackageComponentDO();
									packageComponentDO
											.setObjName(packageCompInfoDO
													.getObjName());
									packageComponentDO
											.setOrder(new Double(1.0));
									packageComponentDO
											.setPkgCompName(packageCompInfoDO
													.getPkgCompInfoName());
									packageComponentDO
											.setParentPackageCompID(packageCompInfoDO
													.getPkgInfoParentId());
									packageComponentDO.setPkgParentId(pid);
									packageComponentDO
											.setSourceOrganizationId(packageCompInfoDO
													.getSourceOrganizationId());
									packageComponentDO
											.setType(packageCompInfoDO
													.getType());
									packageComponentDAO2
											.insert(packageComponentDO,
													fdGetSFoAuthHandleService
															.getSFoAuthHandle(
																	sEnvDO,
																	Constants.CustomBaseOrgID));
									System.out
											.println("create Component List for inserting to existing package");

								}

							}

						}

						PackageDAO packageDAO = new PackageDAO();
						List<Object> list = packageDAO.findById(pid,
								fdGetSFoAuthHandleService.getSFoAuthHandle(
										sEnvDO, Constants.CustomBaseOrgID));
						for (Iterator iterator = list.iterator(); iterator
								.hasNext();) {
							PackageDO object = (PackageDO) iterator.next();
							object.setCommitStatus("Completed");
							packageDAO.update(object, fdGetSFoAuthHandleService
									.getSFoAuthHandle(sEnvDO,
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
						xmlService.createPackageXML(pcList, getMetadataLogId());

						FDDeployCompService fdDeployCompService = new FDDeployCompService();
						SFoAuthHandle sfSourceHandle = fdGetSFoAuthHandleService
								.getSFoAuthHandle(bEnvDO,
										Constants.CustomBaseOrgID);
						fdDeployCompService.deployCompoentsToRepository(
								sfSourceHandle, getMetadataLogId(), pcList);

						System.out.println("Bit Username"
								+ getBitBucketUsername());

						if (getGitUsername() != "") {

							System.out
									.println("Verion Control is Yes We can now Check in the files");
							System.out.println("Git URL :" + getGitURL());
							System.out.println("Git USERNAME :"
									+ getGitUsername());

							System.out.println("GIT PASSWORD"
									+ getGitAccessToken());

							GitRepoDO gitRepoDO = new GitRepoDO(
									getGitUsername(), getGitAccessToken(),
									getGitURL());
							RepoUtil.CheckIn(gitRepoDO,
									getMetadataLogId(),"");

						}

						if (getBitBucketUsername() != "") {

							System.out
									.println("Verion Control is Yes We can now Check in the files");
							System.out.println("Bit URL :" + getBitBucketURL());
							System.out.println("Bit USERNAME :"
									+ getBitBucketUsername());

							System.out.println("Bit PASSWORD"
									+ getBitBucketAccessToken());

							GitRepoDO gitRepoDO = new GitRepoDO(
									getBitBucketUsername(),
									getBitBucketAccessToken(),
									getBitBucketURL());
							RepoUtil.CheckIn(gitRepoDO,
									getMetadataLogId(),"");

						}

						metadataLogInformationDO = RDAppService
								.findMetadataLogInformation(
										getMetadataLogId(),
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

		catch (Exception e) {
			RDAppService.updateDeploymentDetailsInformation(metadataLogId, e
					.getMessage(), metadataLogInformationDO.getSourceOrgId(),
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

}
