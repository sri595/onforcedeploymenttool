package com.rest;

import java.util.List;

public class RunTestResultInformation {
	List<CodeCoverageResultsDetails> codeCoverageResults;
	List<CodeCoverageWarningDetails> codeCoverageWarningDetails;
	List<RunTestFailureDetails> runTestFailureDetails;
	List<RunTestsSuccessDetails> runTestsSuccessDetails;
	public List<CodeCoverageResultsDetails> getCodeCoverageResults() {
		return codeCoverageResults;
	}
	public void setCodeCoverageResults(
			List<CodeCoverageResultsDetails> codeCoverageResults) {
		this.codeCoverageResults = codeCoverageResults;
	}
	public List<CodeCoverageWarningDetails> getCodeCoverageWarningDetails() {
		return codeCoverageWarningDetails;
	}
	public void setCodeCoverageWarningDetails(
			List<CodeCoverageWarningDetails> codeCoverageWarningDetails) {
		this.codeCoverageWarningDetails = codeCoverageWarningDetails;
	}
	public List<RunTestFailureDetails> getRunTestFailureDetails() {
		return runTestFailureDetails;
	}
	public void setRunTestFailureDetails(
			List<RunTestFailureDetails> runTestFailureDetails) {
		this.runTestFailureDetails = runTestFailureDetails;
	}
	public List<RunTestsSuccessDetails> getRunTestsSuccessDetails() {
		return runTestsSuccessDetails;
	}
	public void setRunTestsSuccessDetails(
			List<RunTestsSuccessDetails> runTestsSuccessDetails) {
		this.runTestsSuccessDetails = runTestsSuccessDetails;
	}
	
	

}
