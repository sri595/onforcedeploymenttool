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

@Path("/runTest")
public class RestServiceRunTestsResult {

	String targetOrgID = null;
	String targetOrgURL = null;
	String targetOrgToken = null;
	String targetOrgRefreshToken = null;
	String sessionId = null;

	@GET
	@Path("/result")
	// @Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public RunTestResultInformation getDeployResultInformation(
			@QueryParam("baseOrg") String baseOrg,
			@QueryParam("baseOrgToken") String baseOrgToken,
			@QueryParam("baseOrgURL") String baseOrgURL,
			@QueryParam("baseOrgRefreshToken") String baseOrgRefreshToken,
			@QueryParam("TargetOrg") String TargetOrg,
			@QueryParam("ValidationSuccessId") String ValidationSuccessId) {

		DeployResult deployResult = null;
		RunTestResultInformation runTestResultInformation = null;
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

			RunTestsResult runTestsResult = deployDetails.getRunTestResult();

			CodeCoverageResult[] codeCoverageResults = runTestsResult
					.getCodeCoverage();

			List<CodeCoverageResultsDetails> codeCoverageResultsDetails = new ArrayList<CodeCoverageResultsDetails>();
			CodeCoverageResultsDetails coverageResultsDetails = null;
			List<CodeLocationDMLInfo> codeLocationDMLInfoDetails = new ArrayList<CodeLocationDMLInfo>();

			CodeLocationDMLInfo codeLocationDMLInfo = null;

			List<LocationNotCoveredInfo> locationNotCoveredInfoDetails = new ArrayList<LocationNotCoveredInfo>();

			LocationNotCoveredInfo locationNotCovered = null;

			List<MethodInfo> methodInfoDetails = new ArrayList<MethodInfo>();

			MethodInfo methodInfodetails = null;

			List<CodeCoverageSoqlInfo> codeCoverageSoqlInfoDetails = new ArrayList<CodeCoverageSoqlInfo>();

			CodeCoverageSoqlInfo codeCoverageSoqlInfo = null;

			for (int i = 0; i < codeCoverageResults.length; i++) {
				coverageResultsDetails = new CodeCoverageResultsDetails();

				String id = codeCoverageResults[i].getId();
				/*CodeLocation[] dmlInfo = codeCoverageResults[i].getDmlInfo();

				for (int j = 0; j < dmlInfo.length; j++) {
					codeLocationDMLInfo = new CodeLocationDMLInfo();

					int column = dmlInfo[j].getColumn();
					int line = dmlInfo[j].getLine();
					int numExecutions = dmlInfo[j].getNumExecutions();
					double time = dmlInfo[j].getTime();
					codeLocationDMLInfo.setColumn(column);
					codeLocationDMLInfo.setLine(line);
					codeLocationDMLInfo.setNumExecutions(numExecutions);
					codeLocationDMLInfo.setTime(time);
					codeLocationDMLInfoDetails.add(codeLocationDMLInfo);

				}

				CodeLocation[] locatioNotCovered = codeCoverageResults[i]
						.getLocationsNotCovered();

				for (int k = 0; k < locatioNotCovered.length; k++) {

					locationNotCovered = new LocationNotCoveredInfo();
					int column = locatioNotCovered[k].getColumn();
					int line = locatioNotCovered[k].getLine();
					int numExecutions = locatioNotCovered[k].getNumExecutions();
					double time = locatioNotCovered[k].getTime();
					locationNotCovered.setColumn(column);
					locationNotCovered.setLine(line);
					locationNotCovered.setNumExecutions(numExecutions);
					locationNotCovered.setTime(time);
					locationNotCoveredInfoDetails.add(locationNotCovered);

				}
				CodeLocation[] methodInfo = codeCoverageResults[i]
						.getMethodInfo();

				for (int m = 0; m < methodInfo.length; m++) {
					methodInfodetails = new MethodInfo();
					int column = methodInfo[m].getColumn();
					int line = methodInfo[m].getLine();
					int numExecutions = methodInfo[m].getNumExecutions();
					double time = methodInfo[m].getTime();
					methodInfodetails.setColumn(column);
					methodInfodetails.setLine(line);
					methodInfodetails.setNumExecutions(numExecutions);
					methodInfodetails.setTime(time);
					methodInfoDetails.add(methodInfodetails);

				}*/
				String name = codeCoverageResults[i].getName();
				String nameSpace = codeCoverageResults[i].getNamespace();
				int numLocations = codeCoverageResults[i].getNumLocations();
				int numLocationsNotCovered = codeCoverageResults[i]
						.getNumLocationsNotCovered();

				/*CodeLocation[] soqlInfo = codeCoverageResults[i].getSoqlInfo();
				for (int n = 0; n < soqlInfo.length; n++) {
					codeCoverageSoqlInfo = new CodeCoverageSoqlInfo();
					int column = soqlInfo[n].getColumn();
					int line = soqlInfo[n].getLine();
					int numExecutions = methodInfo[n].getNumExecutions();
					double time = methodInfo[n].getTime();
					codeCoverageSoqlInfo.setColumn(column);
					codeCoverageSoqlInfo.setLine(line);
					codeCoverageSoqlInfo.setNumExecutions(numExecutions);
					codeCoverageSoqlInfo.setTime(time);
					codeCoverageSoqlInfoDetails.add(codeCoverageSoqlInfo);

				}*/
			//	CodeLocation[] soslInfo = codeCoverageResults[i].getSoslInfo();
				String type = codeCoverageResults[i].getType();

				coverageResultsDetails.setId(id);
				coverageResultsDetails.setName(name);
				coverageResultsDetails.setType(type);
				coverageResultsDetails.setNameSpace(nameSpace);
				coverageResultsDetails.setNoLocations(numLocations);
				coverageResultsDetails
						.setNoLocationsNotCovered(numLocationsNotCovered);
				/*coverageResultsDetails
						.setCodeLocationDMLInfo(codeLocationDMLInfoDetails);
				coverageResultsDetails
						.setLocatioNotCoveredInfo(locationNotCoveredInfoDetails);

				coverageResultsDetails.setMethodInfo(methodInfoDetails);
				coverageResultsDetails
						.setCodeCoverageSoqlInfo(codeCoverageSoqlInfoDetails);*/
				codeCoverageResultsDetails.add(coverageResultsDetails);

			}

			CodeCoverageWarning[] codeCoverageWarnings = runTestsResult
					.getCodeCoverageWarnings();

			List<CodeCoverageWarningDetails> codeCoverageWarningDetails = new ArrayList<CodeCoverageWarningDetails>();
			CodeCoverageWarningDetails coverageWarningDetails = null;
			for (int d = 0; d < codeCoverageWarnings.length; d++) {
				coverageWarningDetails = new CodeCoverageWarningDetails();
				String id = codeCoverageWarnings[d].getId();
				String message = codeCoverageWarnings[d].getMessage();
				String name = codeCoverageWarnings[d].getName();
				String nameSpace = codeCoverageWarnings[d].getNamespace();
				coverageWarningDetails.setId(id);
				coverageWarningDetails.setMessage(message);
				coverageWarningDetails.setName(name);
				coverageWarningDetails.setNameSpace(nameSpace);
				codeCoverageWarningDetails.add(coverageWarningDetails);
				coverageWarningDetails = null;

			}
			RunTestFailure[] runTestsFailure = runTestsResult.getFailures();
			List<RunTestFailureDetails> runTestFailureDetails = new ArrayList<>();
			RunTestFailureDetails rFailureDetails = null;
			for (int z = 0; z < runTestsFailure.length; z++) {

				if (z <= 50) {
					rFailureDetails = new RunTestFailureDetails();
					String id = runTestsFailure[z].getId();
					String message = runTestsFailure[z].getMessage();
					String methodName = runTestsFailure[z].getMethodName();
					String name = runTestsFailure[z].getName();
					String nameSpace = runTestsFailure[z].getNamespace();
					String packageName = runTestsFailure[z].getPackageName();
					boolean seeAllData = runTestsFailure[z].getSeeAllData();
					String stackTrace = runTestsFailure[z].getStackTrace();
					double time = runTestsFailure[z].getTime();
					String type = runTestsFailure[z].getType();
					rFailureDetails.setId(id);
					rFailureDetails.setMessage(message);
					rFailureDetails.setMethodName(methodName);
					rFailureDetails.setName(packageName);
					rFailureDetails.setNameSpace(nameSpace);
					rFailureDetails.setPackageName(packageName);
					rFailureDetails.setSeeAllData(seeAllData);
					rFailureDetails.setStackTrace(stackTrace);
					rFailureDetails.setTime(time);
					rFailureDetails.setType(type);
					runTestFailureDetails.add(rFailureDetails);
					rFailureDetails = null;
				}

			}

			int noOfFailures = runTestsResult.getNumFailures();
			RunTestSuccess[] runTestsSuccess = runTestsResult.getSuccesses();
			List<RunTestsSuccessDetails> runTestsSuccessDetails = new ArrayList<>();
			RunTestsSuccessDetails rSuccessDetails = null;

			for (int c = 0; c < runTestsSuccess.length; c++) {
				rSuccessDetails = new RunTestsSuccessDetails();
				String id = runTestsSuccess[c].getId();
				String methodName = runTestsSuccess[c].getMethodName();
				String name = runTestsSuccess[c].getName();
				String nameSpace = runTestsSuccess[c].getNamespace();
				boolean seeAllData = runTestsSuccess[c].getSeeAllData();
				double time = runTestsSuccess[c].getTime();
				rSuccessDetails.setId(id);
				rSuccessDetails.setMethodName(methodName);
				rSuccessDetails.setName(nameSpace);
				rSuccessDetails.setNameSpace(nameSpace);
				rSuccessDetails.setSeeAllData(seeAllData);
				rSuccessDetails.setTime(time);
				runTestsSuccessDetails.add(rSuccessDetails);
				rSuccessDetails = null;

			}
			Double totalTime = runTestsResult.getTotalTime();

			runTestResultInformation = new RunTestResultInformation();

			runTestResultInformation
					.setCodeCoverageResults(codeCoverageResultsDetails);

			runTestResultInformation
					.setRunTestFailureDetails(runTestFailureDetails);
			runTestResultInformation
					.setRunTestsSuccessDetails(runTestsSuccessDetails);
			runTestResultInformation
					.setCodeCoverageWarningDetails(codeCoverageWarningDetails);

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return runTestResultInformation;

	}
}
