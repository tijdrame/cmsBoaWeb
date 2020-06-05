package com.boa.web.response.changecardlimit;

import com.boa.web.response.GenericResponse;

/**
 * ChangeCardLimit
 */
public class ChangeCardLimitResponse extends GenericResponse {
    private String faultCode;
    private String faultString;

public String getFaultCode() {
	return this.faultCode;
}
public void setFaultCode(String faultCode) {
	this.faultCode = faultCode;
}


    public String getFaultString() {
    	return this.faultString;
    }
    public void setFaultString(String faultString) {
    	this.faultString = faultString;
    }

    
}