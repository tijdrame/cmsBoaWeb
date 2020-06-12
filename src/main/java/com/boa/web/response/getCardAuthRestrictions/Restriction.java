package com.boa.web.response.getCardAuthRestrictions;

public class Restriction {
    private String operationTypeIdentifier, regionIdentifier;
    private Boolean isActive;

    public Restriction() {
    }

    public Restriction(String operationTypeIdentifier, String regionIdentifier, Boolean isActive) {
        this.operationTypeIdentifier = operationTypeIdentifier;
        this.regionIdentifier = regionIdentifier;
        this.isActive = isActive;
    }

    public String getOperationTypeIdentifier() {
        return this.operationTypeIdentifier;
    }

    public void setOperationTypeIdentifier(String operationTypeIdentifier) {
        this.operationTypeIdentifier = operationTypeIdentifier;
    }

    public String getRegionIdentifier() {
        return this.regionIdentifier;
    }

    public void setRegionIdentifier(String regionIdentifier) {
        this.regionIdentifier = regionIdentifier;
    }

    public Boolean isIsActive() {
        return this.isActive;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Restriction operationTypeIdentifier(String operationTypeIdentifier) {
        this.operationTypeIdentifier = operationTypeIdentifier;
        return this;
    }

    public Restriction regionIdentifier(String regionIdentifier) {
        this.regionIdentifier = regionIdentifier;
        return this;
    }

    public Restriction isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " operationTypeIdentifier='" + getOperationTypeIdentifier() + "'" +
            ", regionIdentifier='" + getRegionIdentifier() + "'" +
            ", isActive='" + isIsActive() + "'" +
            "}";
    }

}