package com.rest;

public class RunTestsSuccessDetails {
	String id;
	String methodName;
	String name;
	String nameSpace;
	boolean seeAllData;
	double time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

	public boolean isSeeAllData() {
		return seeAllData;
	}

	public void setSeeAllData(boolean seeAllData) {
		this.seeAllData = seeAllData;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

}
