package com.domain;

import java.util.Calendar;

/**
 * 
 * @author PackageInformationDO Used For Setting PackageInformation__c Object
 *         data.which is Developer Organization Package Information
 *
 */
public class CustomerMasterDetails {

	String id; // primary key
	String orgId;
	String name;
	String email;
	String phoneno;
	String address;
	String active_features;
	Double current_count;
	Double limit;
	boolean status;
	Calendar contractstartdate;
	Calendar contractenddate;

	public CustomerMasterDetails() {
		super();
	}

	public CustomerMasterDetails(String id, String orgId, String name,
			String email, String phoneno, String address, boolean status,
			Calendar contractstartdate, Calendar contractenddate,
			String active_features, Double current_count, Double limit) {
		this.id = id;
		this.orgId = orgId;
		this.name = name;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
		this.status = status;
		this.contractstartdate = contractstartdate;
		this.contractenddate = contractenddate;
		this.active_features = active_features;
		this.current_count = current_count;
		this.limit = limit;

	}

	public CustomerMasterDetails(String orgId, String name, String email,
			String phoneno, String address, boolean status,
			Calendar contractstartdate, Calendar contractenddate,
			String active_features, Double current_count, Double limit) {
		this.orgId = orgId;
		this.name = name;
		this.email = email;
		this.phoneno = phoneno;
		this.address = address;
		this.status = status;
		this.contractstartdate = contractstartdate;
		this.contractenddate = contractenddate;
		this.active_features = active_features;
		this.current_count = current_count;
		this.limit = limit;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Calendar getContractstartdate() {
		return contractstartdate;
	}

	public void setContractstartdate(Calendar contractstartdate) {
		this.contractstartdate = contractstartdate;
	}

	public Calendar getContractenddate() {
		return contractenddate;
	}

	public void setContractenddate(Calendar contractenddate) {
		this.contractenddate = contractenddate;
	}

	public String getActive_features() {
		return active_features;
	}

	public void setActive_features(String active_features) {
		this.active_features = active_features;
	}

	public Double getCurrent_count() {
		return current_count;
	}

	public void setCurrent_count(Double current_count) {
		this.current_count = current_count;
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

	
}
