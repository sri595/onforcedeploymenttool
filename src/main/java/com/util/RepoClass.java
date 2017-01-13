package com.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.domain.EnvironmentDO;
import com.domain.EnvironmentInformationDO;
import com.ds.salesforce.dao.comp.EnvironmentDAO;
import com.ds.salesforce.dao.comp.EnvironmentInformationDAO;
import com.services.application.SubmitForApprovalService;
import com.services.component.FDGetSFoAuthHandleService;

public class RepoClass {

	private static Git git;
	private static CredentialsProvider cp;
	private static File dir = new File(getCurrentPath() + "/"
			+ Constants.CheckoutPath1);

	public static String getCurrentPath() {
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		return path;
	}

	/**
	 * Delete directory even if not empty
	 */
	public static void deleteDirectory(File dirPath) {

		if (!dirPath.exists()) {
			return;
		}

		for (String filePath : dirPath.list()) {
			File file = new File(dirPath, filePath);
			if (file.isDirectory())
				deleteDirectory(file);
			file.delete();
		}
	}

	public RepoClass() throws InvalidRemoteException, TransportException,
			GitAPIException, IOException {
		deleteDirectory(dir);
		// cloneRepository();
		addFile();
		// commit();
	}

	public static void CheckIn(File file) throws IOException {
		deleteDirectory(dir);
		// cloneRepository();
		// addFiles(dir);
		// commit(dir);
		// push(cp, dir);
	}

	public static void main(String[] args) throws InvalidRemoteException,
			TransportException, GitAPIException, IOException {
		RepoClass t = new RepoClass();
		// deleteDirectory(dir);
		// init1();
	}

	public static Git cloneRepository(GitRepoDO gitRepoDO, File chekOutDir,
			String type) throws Exception {
		// clone
		CloneCommand cc=null;
		if (type.equals("gitRepo")) {
			cc = new CloneCommand()
					.setCredentialsProvider(getCredentialsProvider(gitRepoDO))
					.setDirectory(chekOutDir).setURI(gitRepoDO.getUrl());
		}
		if (type.equals("BitBucketRepo")) {
			cc = new CloneCommand()
					.setCredentialsProvider(getCredentialsProviderForBitBucket(gitRepoDO))
					.setDirectory(chekOutDir).setURI(gitRepoDO.getBitURL());
		}
		try {
			git = cc.call();

		} catch (GitAPIException e1) {
			/*
			 * FDGetSFoAuthHandleService fdGetSFoAuthHandleService = new
			 * FDGetSFoAuthHandleService(); SubmitForApprovalService service =
			 * new SubmitForApprovalService();
			 * 
			 * if (type.equals("server")) {
			 * 
			 * System.out.println("Server Bitbucket");
			 * 
			 * EnvironmentDAO environmentDAO = new EnvironmentDAO();
			 * 
			 * SFoAuthHandle sFBAuthHandle = fdGetSFoAuthHandleService
			 * .getSFoAuthHandle(bDo, Constants.BaseOrgID);
			 * 
			 * List<Object> list = environmentDAO.findById(sDo.getOrgId(),
			 * sFBAuthHandle); for (Iterator iterator = list.iterator();
			 * iterator.hasNext();) { EnvironmentDO enDo = (EnvironmentDO)
			 * iterator.next(); System.out.println("Server Refresh Token" +
			 * enDo.getBitBucketRefreshToken()); String accessToken =
			 * getAccessToken(enDo .getBitBucketRefreshToken());
			 * enDo.setBitBucketAccesToken(accessToken);
			 * System.out.println("New Bit Bucket AccessToken" +
			 * enDo.getBitBucketAccesToken());
			 * environmentDAO.updateBitBucketAccessToken(enDo, sFBAuthHandle);
			 * System.out.println("New Token Updated Server");
			 * gitRepoDO.setBitBucketAccessToken(accessToken);
			 * 
			 * cc = new CloneCommand() .setCredentialsProvider(
			 * getCredentialsProvider(gitRepoDO)) .setDirectory(chekOutDir)
			 * .setURI(gitRepoDO.getUrl());
			 * 
			 * git = cc.call();
			 * 
			 * } } else {
			 * 
			 * EnvironmentInformationDAO environmentInformationDAO = new
			 * EnvironmentInformationDAO();
			 * 
			 * SFoAuthHandle sFBAuthHandle = fdGetSFoAuthHandleService
			 * .getSFoAuthHandle(bDo, Constants.CustomOrgID); System.out
			 * .println("In catch of Bitbucket/Git Connection server Org" +
			 * sDo.getOrgId()); List<Object> list =
			 * environmentInformationDAO.findById( sDo.getOrgId(),
			 * sFBAuthHandle); for (Iterator iterator = list.iterator();
			 * iterator.hasNext();) { EnvironmentInformationDO enDo =
			 * (EnvironmentInformationDO) iterator .next();
			 * System.out.println("Client Refresh Token" +
			 * enDo.getBitBucketRefreshToken()); String accessToken =
			 * getAccessToken(enDo .getBitBucketRefreshToken());
			 * enDo.setBitBucketAccessToken(accessToken);
			 * System.out.println("New Bit Bucket AccessToken" +
			 * enDo.getBitBucketAccessToken());
			 * environmentInformationDAO.updateBitbucketAccessToken(enDo,
			 * sFBAuthHandle); System.out.println("New Token Updated Client");
			 * 
			 * gitRepoDO.setBitBucketAccessToken(accessToken);
			 * service.setBitBucketAccessToken(accessToken); cc = new
			 * CloneCommand() .setCredentialsProvider(
			 * getCredentialsProvider(gitRepoDO)) .setDirectory(chekOutDir)
			 * .setURI(gitRepoDO.getUrl());
			 * 
			 * git = cc.call();
			 * 
			 * }
			 * 
			 * }
			 */

			// e1.printStackTrace();
			throw new Exception(e1.toString());

		} catch (Exception e1) {

			// TODO Auto-generated catch block
			// e1.printStackTrace();
			throw new Exception(e1.toString());

		}
		return git;
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

	public static CredentialsProvider getCredentialsProviderForBitBucket(GitRepoDO gitRepoDO) {
		// credentials
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(
				gitRepoDO.getBitBucketUsername(), gitRepoDO.getBitBucketAccessToken());
		return cp;
	}
	public void addFile() throws IOException, NoFilepatternException,
			GitAPIException {
		String fileName = "abc7.txt";
		File myfile = null;
		try {
			myfile = new File(git.getRepository().getDirectory().getParent()
					+ "/testcases", fileName);
			myfile.createNewFile();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}

		// add
		AddCommand ac = git.add();
		ac.addFilepattern(".");
		try {
			ac.call();
		} catch (NoFilepatternException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addFile(String fileName, Git git) throws IOException,
			NoFilepatternException, GitAPIException {
		File myfile = null;
		try {
			myfile = new File(git.getRepository().getDirectory().getParent()
					+ "/testcases", fileName);
			myfile.createNewFile();
		} catch (java.lang.NullPointerException e) {
			e.printStackTrace();
		}

		// add
		AddCommand ac = git.add();
		ac.addFilepattern(".");
		try {
			ac.call();
		} catch (NoFilepatternException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addFile(Git git) throws IOException,
			NoFilepatternException, GitAPIException {
		// add
		AddCommand ac = git.add();
		ac.addFilepattern(".");
		try {
			ac.call();
		} catch (NoFilepatternException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void commit(Git git, GitRepoDO gitRepoDO) {
		// commit

		CommitCommand commit = git.commit();
		commit.setCommitter("TMall", "open@tmall.com").setMessage("push war");
		try {
			commit.call();
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (NoMessageException e) {
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			e.printStackTrace();
		} catch (UnmergedPathsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbortedByHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// push
		PushCommand pc = git.push();
		pc.setCredentialsProvider(getCredentialsProviderForBitBucket(gitRepoDO))
				.setForce(true).setPushAll();
		try {
			Iterator<PushResult> it = pc.call().iterator();
			if (it.hasNext()) {
				System.out.println(it.next().toString());
			}
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	public static void init1() throws Exception {
		String name = "skrishna@infrascape.com";
		String password = "Yarragsa@01";
		String url = "https://github.com/saiinfra/CustomerTestProject.git";

		// credentials
		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(name,
				password);
		// clone
		File dir = new File(getCurrentPath() + "/" + "test");
		CloneCommand cc = new CloneCommand().setCredentialsProvider(cp)
				.setDirectory(dir).setURI(url);
		Git git = null;
		try {
			git = cc.call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File myfile = new File(git.getRepository().getDirectory().getParent(),
				"abc5.txt");
		try {
			myfile.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// add
		AddCommand ac = git.add();
		ac.addFilepattern(".");
		try {
			ac.call();
		} catch (NoFilepatternException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// commit
		CommitCommand commit = git.commit();
		commit.setCommitter("TMall", "open@tmall.com").setMessage("push war");
		git.tag().setName("version1").call();
		try {
			commit.call();
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (NoMessageException e) {
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			e.printStackTrace();
		} catch (UnmergedPathsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbortedByHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// push
		PushCommand pc = git.push();
		pc.setCredentialsProvider(cp).setForce(true).setPushAll();
		try {
			Iterator<PushResult> it = pc.call().iterator();
			if (it.hasNext()) {
				System.out.println(it.next().toString());
			}
		} catch (GitAPIException e) {
			e.printStackTrace();
		}

		// cleanup
		dir.deleteOnExit();
	}

	public static void init() throws InvalidRemoteException,
			TransportException, GitAPIException, IOException {
		String name = "skrishna@infrascape.com";
		String password = "Yarragsa@01";
		String url = "https://github.com/saiinfra/CustomerTestProject.git";
		// credentials
		cp = new UsernamePasswordCredentialsProvider(name, password);
		// clone

		File dir = new File(getCurrentPath() + "/" + "test");
		CloneCommand cc = new CloneCommand().setCredentialsProvider(cp)
				.setDirectory(dir).setURI(url);
		Git git = cc.call();
		File myfile = new File(git.getRepository().getDirectory().getParent(),
				"abc4.txt");
		myfile.createNewFile();

		// run the add-call
		git.add().addFilepattern("abc5.txt").call();

		// commit
		CommitCommand commit = git.commit();
		commit.setCommitter("TMall", "open@tmall.com").setMessage("push war");
		try {
			commit.call();
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (NoMessageException e) {
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			e.printStackTrace();
		}
		// push
		PushCommand pc = git.push();
		pc.setCredentialsProvider(cp).setForce(true).setPushAll();
		try {
			Iterator<PushResult> it = pc.call().iterator();
			if (it.hasNext()) {
				System.out.println(it.next().toString());
			}
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		}

		// cleanup
		dir.deleteOnExit();
	}
}
