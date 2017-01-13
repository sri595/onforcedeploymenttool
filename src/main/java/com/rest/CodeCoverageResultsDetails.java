package com.rest;

import java.util.List;

public class CodeCoverageResultsDetails {

	String id;
	//List<CodeLocationDMLInfo> codeLocationDMLInfo;
	//List<LocationNotCoveredInfo> locatioNotCoveredInfo;
	List<MethodInfo> methodInfo;
	String name;
	String nameSpace;
	int noLocations;
	int noLocationsNotCovered;
	//List<CodeCoverageSoqlInfo> codeCoverageSoqlInfo;
	//List<CodeCoverageSoSlInfo> codeCoverageSoSlInfo;
	String type;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public List<MethodInfo> getMethodInfo() {
		return methodInfo;
	}

	public void setMethodInfo(List<MethodInfo> methodInfo) {
		this.methodInfo = methodInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public int getNoLocations() {
		return noLocations;
	}

	public void setNoLocations(int noLocations) {
		this.noLocations = noLocations;
	}

	public int getNoLocationsNotCovered() {
		return noLocationsNotCovered;
	}

	public void setNoLocationsNotCovered(int noLocationsNotCovered) {
		this.noLocationsNotCovered = noLocationsNotCovered;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
