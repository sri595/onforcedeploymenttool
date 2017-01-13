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

@Path("/component")
public class RestServiceComponentsFailureSuccess {

	String targetOrgID = null;
	String targetOrgURL = null;
	String targetOrgToken = null;
	String targetOrgRefreshToken = null;
	String sessionId = null;

	@GET
	@Path("/result")
	// @Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ComponentResultInformation getDeployResultInformation(
			@QueryParam("baseOrg") String baseOrg,
			@QueryParam("baseOrgToken") String baseOrgToken,
			@QueryParam("baseOrgURL") String baseOrgURL,
			@QueryParam("baseOrgRefreshToken") String baseOrgRefreshToken,
			@QueryParam("TargetOrg") String TargetOrg,
			@QueryParam("ValidationSuccessId") String ValidationSuccessId) {

		DeployResult deployResult = null;
		ComponentResultInformation componentResultInformation = new ComponentResultInformation();
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

			DeployDetails deployDetails = deployResult.getDetails();
			DeployMessage[] componentFailures = deployDetails
					.getComponentFailures();
			DeployMessage[] componentSuccesses = deployDetails
					.getComponentSuccesses();

			List<DeployDetailsSuccessMessage> deployComponentSuccess = new ArrayList<DeployDetailsSuccessMessage>();
			List<DeployDetailsfailureMessage> deployComponentfailure = new ArrayList<DeployDetailsfailureMessage>();

			DeployDetailsSuccessMessage deployDetailsSuccessMessage = null;
			DeployDetailsfailureMessage deployDetailsfailrureMessage = null;

			for (int i = 0; i < componentSuccesses.length; i++) {

				deployDetailsSuccessMessage = new DeployDetailsSuccessMessage();
				String componentNameFullName = componentSuccesses[i]
						.getFullName();
				String componenetType = componentSuccesses[i]
						.getComponentType();

				String id = componentSuccesses[i].getId();
				boolean getchnaged = componentSuccesses[i].getChanged();
				int coloumnNumber = componentSuccesses[i].getColumnNumber();
				boolean created = componentSuccesses[i].getCreated();
				Calendar createdDates = componentSuccesses[i].getCreatedDate();

				createdDates.add(Calendar.DATE, 1);
				Date date2 = createdDates.getTime();
				// DateFormat df = new
				// SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

				SimpleDateFormat format3 = new SimpleDateFormat(
						"yyyy-MM-dd'T'hh:mm:ssZ");
				String modifiedCreatedDate = format3.format(date2);

				boolean deleted = componentSuccesses[i].getDeleted();
				String fileName = componentSuccesses[i].getFileName();
				int lineNumber = componentSuccesses[i].getLineNumber();
				String problem = componentSuccesses[i].getProblem();
				boolean isSuccess = componentSuccesses[i].getSuccess();
				deployDetailsSuccessMessage
						.setComponentNameFullName(componentNameFullName);
				deployDetailsSuccessMessage.setComponentType(componenetType);
				deployDetailsSuccessMessage.setId(id);
				deployDetailsSuccessMessage.setCreated(created);
				deployDetailsSuccessMessage
						.setCreatedDates(modifiedCreatedDate);
				deployDetailsSuccessMessage.setDeleted(deleted);
				deployDetailsSuccessMessage.setFileName(fileName);
				deployDetailsSuccessMessage.setGetchnaged(getchnaged);
				deployDetailsSuccessMessage.setLineNumber(lineNumber);
				deployDetailsSuccessMessage.setProblem(problem);
				deployDetailsSuccessMessage.setSuccess(isSuccess);
				deployDetailsSuccessMessage.setColoumnNumber(coloumnNumber);
				deployComponentSuccess.add(deployDetailsSuccessMessage);
				deployDetailsSuccessMessage = null;

			}

			for (int i = 0; i < componentFailures.length; i++) {

				deployDetailsfailrureMessage = new DeployDetailsfailureMessage();
				String componentNameFullName = componentFailures[i]
						.getFullName();
				String componenetType = componentFailures[i].getComponentType();
				String id = componentFailures[i].getId();
				boolean getchnaged = componentFailures[i].getChanged();
				int coloumnNumber = componentFailures[i].getColumnNumber();
				boolean created = componentFailures[i].getCreated();
				Calendar createdDates = componentFailures[i].getCreatedDate();

				createdDates.add(Calendar.DATE, 1);
				Date date2 = createdDates.getTime();
				// DateFormat df = new
				// SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

				SimpleDateFormat format3 = new SimpleDateFormat(
						"yyyy-MM-dd'T'hh:mm:ssZ");
				String modifiedCreatedDate = format3.format(date2);

				boolean deleted = componentFailures[i].getDeleted();
				String fileName = componentFailures[i].getFileName();
				int lineNumber = componentFailures[i].getLineNumber();
				String problem = componentFailures[i].getProblem();
				boolean isSuccess = componentFailures[i].getSuccess();
				deployDetailsfailrureMessage
						.setComponentNameFullName(componentNameFullName);
				deployDetailsfailrureMessage.setComponentType(componenetType);
				deployDetailsfailrureMessage.setId(id);
				deployDetailsfailrureMessage.setCreated(created);
				deployDetailsfailrureMessage
						.setCreatedDates(modifiedCreatedDate);
				deployDetailsfailrureMessage.setDeleted(deleted);
				deployDetailsfailrureMessage.setFileName(fileName);
				deployDetailsfailrureMessage.setGetchnaged(getchnaged);
				deployDetailsfailrureMessage.setLineNumber(lineNumber);
				deployDetailsfailrureMessage.setProblem(problem);
				deployDetailsfailrureMessage.setSuccess(isSuccess);
				deployDetailsfailrureMessage.setColoumnNumber(coloumnNumber);
				deployComponentfailure.add(deployDetailsfailrureMessage);
				deployDetailsfailrureMessage = null;

			}

			componentResultInformation
					.setComponentFailures(deployComponentfailure);
			componentResultInformation
					.setComponentSuccesses(deployComponentSuccess);

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

		return componentResultInformation;

	}
}
