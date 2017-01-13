package com.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.domain.MultiPleDeploymentDO;
import com.tasks.DeployTask;
import com.tasks.ExecuteScript;
import com.tasks.GetPackagesTask;
import com.tasks.QuickDeployTask;
import com.tasks.ReleaseTask;
import com.tasks.RetrieveClientTask;
import com.tasks.RetrieveTask;
import com.tasks.SubmitForApprovalTask;
import com.tasks.UpdatePackagesTask;
import com.util.Constants;

public class ForceDepService {

	public ForceDepService() {
		super();

		// c_t =
		// "00DS0000003Km6L!AQsAQFe_T3SXsKTpVRuZxE44jBIKQz3AsTRouOS1pTx2JeyqIEa0Q0flNY3DpKGEl6Av5wW1t2.5j4oUcOr0vhcxpTBD8IH1";
		// a0536000006M2Dg
		// a0536000006LvXAAA0
		// a0536000006M2qQ
		/*
		 * String metadataLogId = "a0536000006LvXAAA0"; String bOrgId
		 * ="00D36000000L6NwEAK"; String bOrgToken =
		 * "00D36000000L6Nw!AQgAQOAbDY5ziMgEP0WknWj2Er30PR_11cCGvWulygmzloRDSSbtVYU8SeV_zoCLYp52cC4lBnmTO1s8cjBsI9Zd2rvot1qX"
		 * ; String bOrgURL = "https://na30.salesforce.com"; String refreshToken
		 * =
		 * "5Aep861QbHyftz0nI9EQCBdPQPngCvXhaRLMS0L60dauyQnougFjneStqmrrOpTqcPihQX6a1GJuMgEj_CmXU1r"
		 * ; retrieve(bOrgId, bOrgToken, bOrgURL, refreshToken,metadataLogId);
		 * /* * getPackages(bOrgId, bOrgToken, bOrgURL, refreshToken,
		 * releaseParentId, releaseParentName, releaseStatus, metadataLogId); //
		 * retrieve(bOrgId, bOrgToken, bOrgURL,refreshToken, metadataLogId);
		 * 
		 * // release(bOrgId, bOrgToken, bOrgURL, refreshToken, releaseParentId,
		 * // releaseParentName, releaseStatus);
		 */

		/*
		 * String bOrgId = "00D610000006tjPEAQ"; String bOrgToken =
		 * "00D610000006tjP!AQ4AQI2RxQGJa.1iw9O5E0jEzH7.j9wTl9TcEDgJM6xzB4BdHocD0wHk99z9e7w7fB6Z9mmliNvCu9pDHKLmRpo_Z.i7nlNf"
		 * ; String bOrgURL = "https://na34.salesforce.com"; String refreshToken
		 * =
		 * "5Aep861tbt360sO1.uO0UjNoRyP9rNbAguo__QeBtE9I0DtmCCr9I0aAzS9A5Vnfr.9g909nJRwVrb0qp4fHnVG"
		 * ; // String releaseParentName = "srikanth-release182"; // String
		 * releaseParentId="a0B610000023fCKEAY"; // String releaseStatus =
		 * "Active"; String metadataLogId = "a0561000002BiDU"; deploy(bOrgId,
		 * bOrgToken, bOrgURL, refreshToken, metadataLogId, true);
		 */

		
		/* String metadataLogId = "a043600000EryHR"; 
		  String bOrgId = "00D36000000L6GgEAK"; 
		  String bOrgToken = "00D36000000L6Gg!ARQAQGmWT2GOuz.zJWArozp15C9ItC4DjhTMpJp7mCGe8fvrX._qzU1nTmZPS13oPSxTfWuHBf1Bpo7RTl3qPyVyeGIfYt1r"; 
		  String bOrgURL = "https://na30.salesforce.com"; 
		  String bOrgRefreshToken = "5Aep861QbHyftz0nI8oi1AwNQkWJGd9W7nKhOtgU3deajfzBMngDjIrbxZXWGT4nvHU8mJhJ8.V3uPgmq0dWc9G"; 
		  String sOrgId = "00D36000000L6NwEAK"; 
		  String sOrgToken ="00D36000000L6Nw!AQgAQJmWJEo7f.brVX4YzV4nhBN2L9g_i67g3x9OKuo4M3d00H0DDB_wUpgc_5oppPt840PVS6SUZvKRxYqahvOuRljvrsAG"; 
		  String sOrgURL = "https://na30.salesforce.com"; String
		  sOrgRefreshToken ="5Aep861QbHyftz0nI9EQCBdPQPngCvXhaRLMS0LL4ZlJJQzqzWUht0qTIcK75akUL0EE4W2mN1hNTZbWUMxcqyc"; 
		  String status = "open"; 
		  String pkgId = "a063600000Av4TvAAJ";
		  
		 
		  String gitUsername = ""; 
		  String gitAccessToken = ""; 
		  String gitURL =""; 
		  String bitBucketUsername = "srikanthsalesforce"; 
		  String bitBucketAccessToken ="8veSJCLsYcDhQhaBeH6aKKcvG_BvKuKbN6CLtZ5SWVhmGecOTA-vu8-LsP1-zNb6PWTN_6gHbbYTw1t27aU=" ; 
		  String bitBucketRefreshToken = "HQKRkvjNCFhcX8TeTY"; 
		  String bitBucketURL = "https://srikanthsalesforce@bitbucket.org/srikanthsalesforce/salesforcecommittest" ;
		  
		  submitForApproval(bOrgId, bOrgToken, bOrgURL, bOrgRefreshToken,
		  sOrgId, sOrgToken, sOrgURL, sOrgRefreshToken, status, pkgId,
		  metadataLogId, false, gitUsername, gitAccessToken, gitURL,
		  bitBucketUsername, bitBucketAccessToken, bitBucketRefreshToken,
		  bitBucketURL,""); /* /* getPackages(bOrgId, bOrgToken, bOrgURL,
		 * refreshToken, releaseParentId, releaseParentName, releaseStatus,
		 * metadataLogId); getPackages(bOrgId, bOrgToken, bOrgURL, refreshToken,
		 * releaseParentId, releaseParentName, releaseStatus, metadataLogId);
		 */
		// executeScript(Constants.userId, Constants.passwd,
		// Constants.serverURL,"a0161000002rbAS");

		
		/*  String bOrgId = "00D36000000L6NwEAK"; 
		  String bOrgRefreshToken ="5Aep861QbHyftz0nI9EQCBdPQPngCvXhaRLMS0LDkW6CXxT1b7iUFT7H9rX_lBRoD7G.LU1wy3HD1aRiyxiz8FO"; 
		  String bOrgToken ="00D36000000L6Nw!AQgAQCjIUBk7MJBdUa9AqvbO3MuVTd0WP8UBhu1VzMIK6D5KNPKOzUF0pw7k9.N0S9YbC7K_iy0lSbkom71M7M0ryPCfGL.w00D36000000L6Nw!AQgAQCjIUBk7MJBdUa9AqvbO3MuVTd0WP8UBhu1VzMIK6D5KNPKOzUF0pw7k9.N0S9YbC7K_iy0lSbkom71M7M0ryPCfGL.w";
		  String metadataLogId = "a053600000I0AMp"; 
		  String metadataLogId1= "a053600000HyEA7";
		  String metadataLogId2 = "a05360000030FRGAA2";
		  String metadataLogId3 = "a05360000030FRHAA2";
		  String metadataLogId4="a05360000030FRIAA2"; 
		  String validationId = "0Af2800000JmMwm";
		  
		  
		  String bOrgURL = "https://na30.salesforce.com";
		  List<MultiPleDeploymentDO> multiPleDeploymentDOs = new ArrayList<MultiPleDeploymentDO>(); MultiPleDeploymentDO
		  multiPleDeploymentDO = new MultiPleDeploymentDO(metadataLogId, bOrgId, bOrgToken, bOrgRefreshToken, bOrgURL,validationId);
		 /* MultiPleDeploymentDO multiPleDeploymentDO1 = new MultiPleDeploymentDO(metadataLogId1, bOrgId, bOrgToken,
		 * bOrgRefreshToken, bOrgURL); /*MultiPleDeploymentDO
		 * multiPleDeploymentDO2 = new MultiPleDeploymentDO(metadataLogId2,
		 * bOrgId, bOrgToken, bOrgRefreshToken, bOrgURL); MultiPleDeploymentDO
		 * multiPleDeploymentDO3 = new MultiPleDeploymentDO(metadataLogId3,
		 * bOrgId, bOrgToken, bOrgRefreshToken, bOrgURL); MultiPleDeploymentDO
		 * multiPleDeploymentDO4 = new MultiPleDeploymentDO(metadataLogId4,
		 * bOrgId, bOrgToken, bOrgRefreshToken, bOrgURL);
		 */

		// multiPleDeploymentDOs.add(multiPleDeploymentDO);
		// multiPleDeploymentDOs.add(multiPleDeploymentDO1);
		/*
		 * multiPleDeploymentDOs.add(multiPleDeploymentDO2);
		 * multiPleDeploymentDOs.add(multiPleDeploymentDO3);
		 * multiPleDeploymentDOs.add(multiPleDeploymentDO4);
		 */

		// System.out.println(multiPleDeploymentDOs.toString());

		
		 /* deploy(bOrgId, bOrgToken, bOrgURL, bOrgRefreshToken,
		  multiPleDeploymentDOs, false);
		 
		  /*deployRecentValidation(bOrgId, bOrgToken, bOrgURL,
		 * bOrgRefreshToken, multiPleDeploymentDOs, false);
		 */
	}

	public static void main(String[] args) {
		ForceDepService sr = new ForceDepService();

	}

	public void deploy(String bOrgId, String bOrgToken, String bOrgURL,
			String refreshtoken,
			List<MultiPleDeploymentDO> multiPleDeploymentDOs, boolean isValidate) {
		Runnable task;
		ExecutorService executorService = Executors
				.newFixedThreadPool(multiPleDeploymentDOs.size());

		for (Iterator<MultiPleDeploymentDO> iterator = multiPleDeploymentDOs
				.iterator(); iterator.hasNext();) {
			MultiPleDeploymentDO multiPleDeploymentDO = (MultiPleDeploymentDO) iterator
					.next();
			try {

				System.out.println("Deploy operation Initiated for requestId: "
						+ multiPleDeploymentDO.getMetadataLog());
				System.out.println("bOrgId : "
						+ multiPleDeploymentDO.getBaseOrg() + "~"
						+ "bOrgURL : " + multiPleDeploymentDO.getBaseOrgURL()
						+ "~" + "bOrgToken : "
						+ multiPleDeploymentDO.getBaseOrgToken() + "~");
				System.out.println("metadata Log Id: "
						+ multiPleDeploymentDO.getMetadataLog());
				task = new DeployTask(multiPleDeploymentDO.getBaseOrg(),
						multiPleDeploymentDO.getBaseOrgToken(),
						multiPleDeploymentDO.getBaseOrgURL(),
						multiPleDeploymentDO.getRefreshToken(),
						multiPleDeploymentDO.getMetadataLog(), isValidate,
						multiPleDeploymentDO.getRepositoryId(),multiPleDeploymentDO.getBitURL(),multiPleDeploymentDO.getGitURL());
				executorService.execute(task);
				// Thread.sleep(multiPleDeploymentDOs.size() * 2000);
				/*
				 * Thread t = new Thread(task); t.setPriority(count);
				 * t.setName(multiPleDeploymentDO.getMetadataLog()); t.start();
				 * Thread.sleep(5000);
				 */

				/*
				 * System.out.println("Thread Name... " + t.getName() +
				 * "Thread Priority.." + t.getPriority());
				 */
			}

			catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Deploy operation Initiated for requestId: "
						+ multiPleDeploymentDO.getMetadataLog());
			}
		}
		executorService.shutdown();
	}

	public void quickDeploy(String bOrgId, String bOrgToken, String bOrgURL,
			String refreshtoken,
			List<MultiPleDeploymentDO> multiPleDeploymentDOs, boolean isValidate) {
		Runnable task;
		ExecutorService executorService = Executors
				.newFixedThreadPool(multiPleDeploymentDOs.size());

		for (Iterator<MultiPleDeploymentDO> iterator = multiPleDeploymentDOs
				.iterator(); iterator.hasNext();) {
			MultiPleDeploymentDO multiPleDeploymentDO = (MultiPleDeploymentDO) iterator
					.next();
			try {

				System.out.println("Quick Deploy Prococess Initiated with: ");
				System.out.println("bOrgId : "
						+ multiPleDeploymentDO.getBaseOrg() + "~"
						+ "bOrgURL : " + multiPleDeploymentDO.getBaseOrgURL()
						+ "~" + "bOrgToken : "
						+ multiPleDeploymentDO.getBaseOrgToken() + "~");
				System.out.println("metadata Log Id: "
						+ multiPleDeploymentDO.getMetadataLog());

				task = new QuickDeployTask(multiPleDeploymentDO.getBaseOrg(),
						multiPleDeploymentDO.getBaseOrgToken(),
						multiPleDeploymentDO.getBaseOrgURL(),
						multiPleDeploymentDO.getRefreshToken(),
						multiPleDeploymentDO.getMetadataLog(), isValidate,
						multiPleDeploymentDO.getValidationId());
				executorService.execute(task);

			}

			catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("Deploy operation Initiated for requestId: "
						+ multiPleDeploymentDO.getMetadataLog());
			}
		}
		executorService.shutdown();
	}

	public boolean retrieve(String bOrgId, String bOrgToken, String bOrgURL,
			String bOrgRefreshToken, String metadataLogId) {
		Runnable task;
		try {
			task = new RetrieveTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, metadataLogId);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Retrieve operation Initiated for requestId: "
					+ metadataLogId);
		}
		return true;
	}

	public boolean updatePackages(String bOrgId, String bOrgToken,
			String bOrgURL, String bOrgRefreshToken, String metadataLogId,
			String status, String packageParentId) {
		Runnable task;
		try {
			task = new UpdatePackagesTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, status, packageParentId, metadataLogId);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Retrieve operation Initiated for requestId: "
					+ metadataLogId);
		}
		return true;
	}

	public boolean submitForApproval(String bOrgId, String bOrgToken,
			String bOrgURL, String bOrgRefreshToken, String sOrgId,
			String sOrgToken, String sOrgURL, String sOrgRefreshToken,
			String status, String pkgId, String metadataLogId,
			boolean override, String gitUsername, String gitAccessToken,
			String gitURL, String bitBucketUsername,
			String bitBucketAccessToken, String bitBucketRefreshToken,
			String bitBucketURL, String repositoryId) {

		Runnable task;
		try {
			task = new SubmitForApprovalTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, sOrgId, sOrgToken, sOrgURL,
					sOrgRefreshToken, status, pkgId, metadataLogId, override,
					gitUsername, gitAccessToken, gitURL, bitBucketUsername,
					bitBucketAccessToken, bitBucketRefreshToken, bitBucketURL,
					repositoryId);

			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out
					.println("Submit for Approval operation Initiated for requestId: "
							+ metadataLogId);
		}
		return true;
	}

	public boolean retrieveClient(String bOrgId, String bOrgToken,
			String bOrgURL, String bOrgRefreshToken, String metadataLogId) {
		Runnable task;
		try {
			task = new RetrieveClientTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, Constants.CustomBaseOrgID, metadataLogId);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Retrieve operation Initiated for requestId: "
					+ metadataLogId);
		}
		return true;
	}

	public boolean executeScript(String userId, String passwd,
			String serverURL, String metadataLogId, String testcasename) {
		Runnable task;
		try {
			task = new ExecuteScript(userId, passwd, serverURL, metadataLogId,
					testcasename);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out
					.println("ExecuteScript  operation Initiated for requestId: "
							+ metadataLogId);
		}
		return true;
	}

	public boolean release(String bOrgId, String bOrgToken, String bOrgURL,
			String bOrgRefreshToken, String releaseParentId,
			String releaseParentName, String releaseStatus, String metadataLogId) {
		Runnable task;
		try {
			task = new ReleaseTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, releaseParentId, releaseParentName,
					releaseStatus, metadataLogId);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Release operation Initiated for requestId: "
					+ releaseParentId);
		}
		return true;
	}

	public boolean getPackages(String bOrgId, String bOrgToken, String bOrgURL,
			String bOrgRefreshToken, String releaseParentId,
			String releaseParentName, String releaseStatus, String metadataLogId) {
		Runnable task;
		try {
			task = new GetPackagesTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, releaseParentId, releaseParentName,
					releaseStatus, metadataLogId);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Package operation Initiated for requestId: "
					+ releaseParentId);
		}
		return true;
	}

	public boolean getPackageInformation(String bOrgId, String bOrgToken,
			String bOrgURL, String bOrgRefreshToken, String releaseParentId,
			String releaseParentName, String releaseStatus, String metadataLogId) {
		Runnable task;
		try {
			task = new ReleaseTask(bOrgId, bOrgToken, bOrgURL,
					bOrgRefreshToken, releaseParentId, releaseParentName,
					releaseStatus, metadataLogId);
			Thread t = new Thread(task);
			t.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Release operation Initiated for requestId: "
					+ releaseParentId);
		}
		return true;
	}
}