package com.rest;

import java.util.Calendar;

public class DeployDetailsfailureMessage {
	
	String componentNameFullName;
	String componentType;
	String id;
	boolean getchnaged;
	int coloumnNumber;
	boolean created;
	String createdDates;
	boolean deleted;
	String fileName;
	int lineNumber;
	String problem ;
	boolean isSuccess;

	public String getComponentNameFullName() {
		return componentNameFullName;
	}

	public void setComponentNameFullName(String componentNameFullName) {
		this.componentNameFullName = componentNameFullName;
	}

	public String getComponentType() {
		return componentType;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isGetchnaged() {
		return getchnaged;
	}

	public void setGetchnaged(boolean getchnaged) {
		this.getchnaged = getchnaged;
	}

	public int getColoumnNumber() {
		return coloumnNumber;
	}

	public void setColoumnNumber(int coloumnNumber) {
		this.coloumnNumber = coloumnNumber;
	}

	public boolean isCreated() {
		return created;
	}

	public void setCreated(boolean created) {
		this.created = created;
	}

	public String getCreatedDates() {
		return createdDates;
	}

	public void setCreatedDates(String createdDates) {
		this.createdDates = createdDates;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	

}
