package com.boa.web.response;

/**
 * ChangeCardAuthRestrictionResponse
 */
public class ChangeCardAuthRestrictionResponse extends GenericResponse {

    private Boolean isActive;
    private String faultCode;
    private String faultString;

    public Boolean isIsActive() {
        return this.isActive;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

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