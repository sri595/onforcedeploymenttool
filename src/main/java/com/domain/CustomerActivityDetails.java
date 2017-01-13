package com.domain;

import java.util.Calendar;

/**
 * 
 * @author PackageInformationDO Used For Setting PackageInformation__c Object
 *         data.which is Developer Organization Package Information
 *
 */
public class CustomerActivityDetails {

	String id; // primary key
	String masterid;
	String activityname;
	String orgId;
	String sourceId;
	String destinationId;
	String description;
	Calendar activitytime;
	Double count;

	public CustomerActivityDetails() {
		super();
	}

	public CustomerActivityDetails(String id, String masterid,
			String activityname, String orgId, String sourceId,
			String destinationId, String description, Calendar activitytime,
			Double count) {
		this.id = id;
		this.masterid = masterid;
		this.activityname = activityname;
		this.orgId = orgId;
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.description = description;
		this.activitytime = activitytime;
		this.count = count;

	}

	public CustomerActivityDetails(String masterid,
			String activityname, String orgId, String sourceId,
			String destinationId, String description, Calendar activitytime,
			Double count) {
		this.masterid = masterid;
		this.activityname = activityname;
		this.orgId = orgId;
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.description = description;
		this.activitytime = activitytime;
		this.count = count;

	}
	
	public CustomerActivityDetails(String masterid,
			String activityname, String orgId) {
		this.masterid = masterid;
		this.activityname = activityname;
		this.orgId = orgId;
	

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMasterid() {
		return masterid;
	}

	public void setMasterid(String masterid) {
		this.masterid = masterid;
	}

	public String getActivityname() {
		return activityname;
	}

	public void setActivityname(String activityname) {
		this.activityname = activityname;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getActivitytime() {
		return activitytime;
	}

	public void setActivitytime(Calendar activitytime) {
		this.activitytime = activitytime;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	
}
