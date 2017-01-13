package com.rest;

import java.util.List;

public class ComponentResultInformation {
	List<DeployDetailsSuccessMessage> componentSuccesses;
	List<DeployDetailsfailureMessage> componentFailures;

	public List<DeployDetailsSuccessMessage> getComponentSuccesses() {
		return componentSuccesses;
	}

	public void setComponentSuccesses(
			List<DeployDetailsSuccessMessage> componentSuccesses) {
		this.componentSuccesses = componentSuccesses;
	}

	public List<DeployDetailsfailureMessage> getComponentFailures() {
		return componentFailures;
	}

	public void setComponentFailures(
			List<DeployDetailsfailureMessage> componentFailures) {
		this.componentFailures = componentFailures;
	}

}
