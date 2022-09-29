package com.democrance.policy.service;

public enum PolicyStateEnum {

	NEW("new"),
	QUOTED("quoted"),
	ACCEPTED("accepted"),
	ACTIVE("active");
	
	private String param;

	private PolicyStateEnum(String param) {
		this.param = param;
	}

	public String getParam() {
		return param;
	}
	
}
