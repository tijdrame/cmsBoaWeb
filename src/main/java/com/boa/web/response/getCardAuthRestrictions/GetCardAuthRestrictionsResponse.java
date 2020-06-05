package com.boa.web.response.getCardAuthRestrictions;

import java.util.ArrayList;
import java.util.List;

import com.boa.web.response.GenericResponse;

/**
 * GetCardAuthRestrictionsResponse
 */
public class GetCardAuthRestrictionsResponse extends GenericResponse {
    private OperationType operationType = new OperationType();
    private List<Region> region = new ArrayList<>();
    private String faultCode;
    private String faultString;

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public List<Region> getRegion() {
        return region;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
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