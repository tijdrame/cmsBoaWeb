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
    private List<Restriction> restrictions = new ArrayList<>();
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

    public GetCardAuthRestrictionsResponse() {
    }

    public GetCardAuthRestrictionsResponse(OperationType operationType, List<Region> region, String faultCode, String faultString, List<Restriction> restrictions) {
        this.operationType = operationType;
        this.region = region;
        this.faultCode = faultCode;
        this.faultString = faultString;
        this.restrictions = restrictions;
    }

    public List<Restriction> getRestrictions() {
        return this.restrictions;
    }

    public void setRestrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
    }

    public GetCardAuthRestrictionsResponse operationType(OperationType operationType) {
        this.operationType = operationType;
        return this;
    }

    public GetCardAuthRestrictionsResponse region(List<Region> region) {
        this.region = region;
        return this;
    }

    public GetCardAuthRestrictionsResponse faultCode(String faultCode) {
        this.faultCode = faultCode;
        return this;
    }

    public GetCardAuthRestrictionsResponse faultString(String faultString) {
        this.faultString = faultString;
        return this;
    }

    public GetCardAuthRestrictionsResponse restrictions(List<Restriction> restrictions) {
        this.restrictions = restrictions;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " operationType='" + getOperationType() + "'" +
            ", region='" + getRegion() + "'" +
            ", faultCode='" + getFaultCode() + "'" +
            ", faultString='" + getFaultString() + "'" +
            ", restrictions='" + getRestrictions() + "'" +
            "}";
    }

}