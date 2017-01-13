package com.util;

import java.util.Iterator;
import java.util.List;

import com.domain.EnvironmentDO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.rest.DeployResultInformation;
import com.services.component.FDGetSFoAuthHandleService;
import com.sforce.soap.metadata.DeployResult;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.ws.ConnectionException;

public class TestRest {
	
	
	
	public static void main(String[] args) {
		
		String targetOrgID = null;
		String targetOrgURL = null;
		String targetOrgToken = null;
		String targetOrgRefreshToken = null;
		
		
	String baseOrg="00D36000000L6NwEAK";
	String baseOrgToken="00D36000000L6Nw!AQgAQIiiOJzyLdrWBbTcb4.l5An1ppWhyaxCsh_cN22YMhhLwinaM8xnhJcFnXvYWIouhpIPG2rMOyabPojKNd_q_L3twZni";
	String baseOrgURL="https://na30.salesforce.com";
	String baseOrgRefreshToken="5Aep861QbHyftz0nI9EQCBdPQPngCvXhaRLMS0LDkW6CXxT1b5YFBa651ilroIlU12MRfIAKGjJRc9ha8y5qpbL";
	String TargetOrg="00D280000015PQhEAM";
    String ValidationSuccessId="0Af2800000YIkKZCA1";
    
	DeployResult deployResult = null;
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

	MetadataConnection conn = sfThandle.getMetadataConnection();
	try {
		deployResult = conn.checkDeployStatus(ValidationSuccessId, true);
	} catch (ConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	/*DeployResultInformation deployResultInformation = new DeployResultInformation();
	deployResultInformation.setNumberComponentsDeployed(deployResult
			.getNumberComponentsDeployed());
	deployResultInformation.setNumberComponentsTotal(deployResult
			.getNumberComponentsTotal());
	deployResultInformation.setNumberComponentErrors(deployResult
			.getNumberComponentErrors());
	deployResultInformation.setNumberTestErrors(deployResult
			.getNumberTestErrors());
	deployResultInformation.setNumberTestsCompleted(deployResult
			.getNumberTestsCompleted());
	deployResultInformation.setNumberTestsTotal(deployResult
			.getNumberTestsTotal());
	System.out.println(deployResultInformation.getNumberComponentsTotal());*/

		
		
		
	}

}
