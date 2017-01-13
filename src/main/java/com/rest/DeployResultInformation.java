package com.rest;

import java.util.Calendar;
import java.util.Date;

public class DeployResultInformation {

	String id;
	String canceledBy;
	String canceledByName;
	boolean checkOnly;
	String completedDate;
	String createdBy;
	String createdByName;
	String createdDate;
	boolean done;
	String errorMessage;
	String errorStatusCode;
	boolean ignoreWarnings;
	String lastModifiedDate;
	int numberComponentErrors;
	int numberComponentsDeployed;
	int numberComponentsTotal;
	int numberTestErrors;
	int numberTestsCompleted;
	int numberTestsTotal;
	boolean runTestsEnabled;
	boolean rollbackOnError;
	String startDate;
	String stateDetail;
	boolean issuccess;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCanceledBy() {
		return canceledBy;
	}

	public void setCanceledBy(String canceledBy) {
		this.canceledBy = canceledBy;
	}

	public String getCanceledByName() {
		return canceledByName;
	}

	public void setCanceledByName(String canceledByName) {
		this.canceledByName = canceledByName;
	}

	public boolean isCheckOnly() {
		return checkOnly;
	}

	public void setCheckOnly(boolean checkOnly) {
		this.checkOnly = checkOnly;
	}

	public String getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorStatusCode() {
		return errorStatusCode;
	}

	public void setErrorStatusCode(String errorStatusCode) {
		this.errorStatusCode = errorStatusCode;
	}

	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getNumberComponentErrors() {
		return numberComponentErrors;
	}

	public void setNumberComponentErrors(int numberComponentErrors) {
		this.numberComponentErrors = numberComponentErrors;
	}

	public int getNumberComponentsDeployed() {
		return numberComponentsDeployed;
	}

	public void setNumberComponentsDeployed(int numberComponentsDeployed) {
		this.numberComponentsDeployed = numberComponentsDeployed;
	}

	public int getNumberComponentsTotal() {
		return numberComponentsTotal;
	}

	public void setNumberComponentsTotal(int numberComponentsTotal) {
		this.numberComponentsTotal = numberComponentsTotal;
	}

	public int getNumberTestErrors() {
		return numberTestErrors;
	}

	public void setNumberTestErrors(int numberTestErrors) {
		this.numberTestErrors = numberTestErrors;
	}

	public int getNumberTestsCompleted() {
		return numberTestsCompleted;
	}

	public void setNumberTestsCompleted(int numberTestsCompleted) {
		this.numberTestsCompleted = numberTestsCompleted;
	}

	public int getNumberTestsTotal() {
		return numberTestsTotal;
	}

	public void setNumberTestsTotal(int numberTestsTotal) {
		this.numberTestsTotal = numberTestsTotal;
	}

	public boolean isRunTestsEnabled() {
		return runTestsEnabled;
	}

	public void setRunTestsEnabled(boolean runTestsEnabled) {
		this.runTestsEnabled = runTestsEnabled;
	}

	public boolean isRollbackOnError() {
		return rollbackOnError;
	}

	public void setRollbackOnError(boolean rollbackOnError) {
		this.rollbackOnError = rollbackOnError;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStateDetail() {
		return stateDetail;
	}

	public void setStateDetail(String stateDetail) {
		this.stateDetail = stateDetail;
	}

	public boolean isIssuccess() {
		return issuccess;
	}

	public void setIssuccess(boolean issuccess) {
		this.issuccess = issuccess;
	}

}
