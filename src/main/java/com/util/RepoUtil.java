package com.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.domain.EnvironmentDO;

public class RepoUtil {

	public static void CheckIn(GitRepoDO gitRepoDO, String metatadataLogId,
			String type) throws Exception {
		File checkOutDir = new File(Constants.CheckoutPath1 + metatadataLogId);
		RepoClass.deleteDirectory(checkOutDir);
		Git git = null;
		try {
			git = RepoClass.cloneRepository(gitRepoDO, checkOutDir, type);
			FileBasedDeploy.readFile(metatadataLogId);

		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.toString());
		}
		try {
			RepoClass.addFile(git);
		} catch (IOException | GitAPIException e) {

			throw new Exception(e.toString());

			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		RepoClass.commit(git, gitRepoDO);
		RepoClass.deleteDirectory(checkOutDir);
		// RepoClass.deleteDirectory(checkOutDir);
		checkOutDir.delete();
		File f = new File(metatadataLogId);
		RepoClass.deleteDirectory(f);
		f.delete();

	}

	public static void CheckIn1(GitRepoDO gitRepoDO) throws Exception {
		File checkOutDir = new File(Constants.CheckoutPath1);
		RepoClass.deleteDirectory(checkOutDir);
		Git git = null;
		try {
			git = RepoClass.cloneRepository(gitRepoDO, checkOutDir, "");
			// FileBasedQuickDeploy.readFile();

		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			RepoClass.addFile(git);
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RepoClass.commit(git, gitRepoDO);
	}

	public static void CheckOut(GitRepoDO gitRepoDO) throws Exception {
		File checkOutDir = new File(Constants.CheckoutPath1);
		RepoClass.deleteDirectory(checkOutDir);
		Git git = null;
		try {
			git = RepoClass.cloneRepository(gitRepoDO, checkOutDir, "");
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getCurrentPath() {
		Path currentRelativePath = Paths.get("");
		String path = currentRelativePath.toAbsolutePath().toString();
		return path;
	}
}