package com.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import com.domain.EnvironmentDO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.services.component.FDGetSFoAuthHandleService;
import com.sforce.soap.metadata.CodeCoverageResult;
import com.sforce.soap.metadata.CodeCoverageWarning;
import com.sforce.soap.metadata.CodeLocation;
import com.sforce.soap.metadata.DeployDetails;
import com.sforce.soap.metadata.DeployMessage;
import com.sforce.soap.metadata.DeployResult;
import com.sforce.soap.metadata.DeployStatus;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.RunTestFailure;
import com.sforce.soap.metadata.RunTestSuccess;
import com.sforce.soap.metadata.RunTestsResult;
import com.sforce.soap.metadata.StatusCode;
import com.sforce.ws.ConnectionException;
import com.util.Constants;
import com.util.SFoAuthHandle;

@Path("/deploy")
public class RestServiceDeployResult {

	String targetOrgID = null;
	String targetOrgURL = null;
	String targetOrgToken = null;
	String targetOrgRefreshToken = null;
	String sessionId = null;

	@GET
	@Path("/result")
	// @Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DeployResultInformation getDeployResultInformation(
			@QueryParam("baseOrg") String baseOrg,
			@QueryParam("baseOrgToken") String baseOrgToken,
			@QueryParam("baseOrgURL") String baseOrgURL,
			@QueryParam("baseOrgRefreshToken") String baseOrgRefreshToken,
			@QueryParam("TargetOrg") String TargetOrg,
			@QueryParam("ValidationSuccessId") String ValidationSuccessId) {

		DeployResult deployResult = null;
		DeployResultInformation deployResultInformation = null;
		// connect to base environment to get Details of Target

		FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new FDGetSFoAuthHandleService();
		SFoAuthHandle sfHandle = fdGetSFoAuthHandleService.getSFoAuthHandle(
				baseOrg, baseOrgToken, baseOrgURL, baseOrgRefreshToken,
				Constants.BaseOrgID);

		// Query on Environment Object

		EnvironmentDAO environmentDAO = new EnvironmentDAO();
		List<Object> tEnvdo = environmentDAO.findById(TargetOrg, sfHandle);
		for (Iterator iterator = tEnvdo.iterator(); iterator.hasNext();) {
			EnvironmentDO environmentDO = (EnvironmentDO) iterator.next();
			targetOrgID = environmentDO.getOrgId();
			targetOrgURL = environmentDO.getServerURL();
			targetOrgToken = environmentDO.getToken();
			targetOrgRefreshToken = environmentDO.getRefreshtoken();

		}

		// TargetConnection
		SFoAuthHandle sfThandle = fdGetSFoAuthHandleService.getSFoAuthHandle(
				targetOrgID, targetOrgToken, targetOrgURL,
				targetOrgRefreshToken, Constants.CustomOrgID);

		String ParentsessionId = sfThandle.getEnterpriseConnection()
				.getSessionHeader().getSessionId();

		System.out.println("Session Id" + ParentsessionId);
		System.out.println("Server URL" + targetOrgURL);
		System.out.println("Token" + targetOrgToken);

		MetadataConnection conn = sfThandle.getMetadataConnection();
		try {
			deployResult = conn.checkDeployStatus(ValidationSuccessId, true);
			String canceledBy = deployResult.getCanceledBy();
			String canceledByName = deployResult.getCanceledByName();
			boolean checkOnly = deployResult.getCheckOnly();
			Calendar completedDate = deployResult.getCompletedDate();
			String createdBy = deployResult.getCreatedBy();
			String createdByName = deployResult.getCreatedByName();
			Calendar createdDate = deployResult.getCreatedDate();

			createdDate.add(Calendar.DATE, 1);
			Date date = createdDate.getTime();
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

			SimpleDateFormat format1 = new SimpleDateFormat(
					"yyyy-MM-dd'T'hh:mm:ssZ");
			String modifiedDate = format1.format(date);

			completedDate.add(Calendar.DATE, 1);
			Date date1 = completedDate.getTime();
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

			SimpleDateFormat format2 = new SimpleDateFormat(
					"yyyy-MM-dd'T'hh:mm:ssZ");
			String modifiedCompleteDate = format2.format(date1);

			DeployDetails deployDetails = deployResult.getDetails();
			boolean deployDone = deployResult.getDone();
			String errorMessage = deployResult.getErrorMessage();
			StatusCode errorStatucCode = deployResult.getErrorStatusCode();
			String Id = deployResult.getId();
			boolean IgnoreWarnings = deployResult.getIgnoreWarnings();
			Calendar lastModifiedDate = deployResult.getLastModifiedDate();
			int noofComponentErrors = deployResult.getNumberComponentErrors();
			int noofcomponentsDeployed = deployResult
					.getNumberComponentsDeployed();
			int componentsTotal = deployResult.getNumberComponentsTotal();
			int noofTestErrors = deployResult.getNumberTestErrors();
			int noofTestsCompleted = deployResult.getNumberTestsCompleted();
			int noofTestsToatl = deployResult.getNumberTestsTotal();
			boolean rollbackonerror = deployResult.getRollbackOnError();
			boolean runtestsEnabled = deployResult.getRunTestsEnabled();
			Calendar startDate = deployResult.getStartDate();

			startDate.add(Calendar.DATE, 1);
			Date date3 = startDate.getTime();
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

			SimpleDateFormat format4 = new SimpleDateFormat(
					"yyyy-MM-dd'T'hh:mm:ssZ");
			String modifiedStartDate = format4.format(date3);
			
			lastModifiedDate.add(Calendar.DATE, 1);
			Date date4 = lastModifiedDate.getTime();
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

			SimpleDateFormat format5 = new SimpleDateFormat(
					"yyyy-MM-dd'T'hh:mm:ssZ");
			String lastmodifiedStartDate = format5.format(date4);

			String stateDetails = deployResult.getStateDetail();
			DeployStatus deployStatus = deployResult.getStatus();
			// String deployStatus1 = deployStatus.toString();
			boolean issuccess = deployResult.getSuccess();

			deployResultInformation = new DeployResultInformation();
			deployResultInformation.setCanceledBy(canceledBy);
			deployResultInformation.setCanceledByName(canceledByName);
			deployResultInformation.setCheckOnly(checkOnly);
			deployResultInformation.setCompletedDate(modifiedCompleteDate);
			deployResultInformation.setCreatedBy(createdBy);
			deployResultInformation.setCreatedByName(createdByName);
			deployResultInformation.setCreatedDate(modifiedDate);
			deployResultInformation.setDone(deployDone);
			deployResultInformation.setErrorMessage(errorMessage);
			/*deployResultInformation.setErrorStatusCode(errorStatucCode
					.toString());*/
			deployResultInformation.setId(Id);
			deployResultInformation.setIgnoreWarnings(IgnoreWarnings);
			deployResultInformation.setIssuccess(issuccess);
			deployResultInformation.setLastModifiedDate(lastmodifiedStartDate);
			deployResultInformation
					.setNumberComponentErrors(noofComponentErrors);
			deployResultInformation
					.setNumberComponentsDeployed(noofcomponentsDeployed);
			deployResultInformation.setNumberComponentsTotal(componentsTotal);
			deployResultInformation.setNumberTestErrors(noofTestErrors);
			deployResultInformation.setNumberTestsCompleted(noofTestsCompleted);
			deployResultInformation.setNumberTestsTotal(noofTestsToatl);
			deployResultInformation.setRollbackOnError(rollbackonerror);
			deployResultInformation.setRunTestsEnabled(runtestsEnabled);
			deployResultInformation.setStartDate(modifiedStartDate);
			deployResultInformation.setStateDetail(stateDetails);

			/*
			 * sessionId =
			 * sfThandle.getEnterpriseConnection().getSessionHeader()
			 * .getSessionId();
			 */

			// deployResultInformation.setDeployMessageSri(deployMessagesforComponentsucess);
			/*
			 * deployResultInformation
			 * .setDeployDetailsuccessMessages(deployComponentSuccess);
			 */

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return deployResultInformation;

	}
}
