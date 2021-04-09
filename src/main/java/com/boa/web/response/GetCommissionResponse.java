package com.boa.web.response;

public class GetCommissionResponse extends GenericResponse {
    private Double montantCommission;
    private String rCode;
    private String rMessage;


    public GetCommissionResponse() {
    }

    public GetCommissionResponse(Double montantCommission, String rCode, String rMessage) {
        this.montantCommission = montantCommission;
        this.rCode = rCode;
        this.rMessage = rMessage;
    }

    public Double getMontantCommission() {
        return this.montantCommission;
    }

    public void setMontantCommission(Double montantCommission) {
        this.montantCommission = montantCommission;
    }

    public String getRCode() {
        return this.rCode;
    }

    public void setRCode(String rCode) {
        this.rCode = rCode;
    }

    public String getRMessage() {
        return this.rMessage;
    }

    public void setRMessage(String rMessage) {
        this.rMessage = rMessage;
    }

    public GetCommissionResponse montantCommission(Double montantCommission) {
        setMontantCommission(montantCommission);
        return this;
    }

    public GetCommissionResponse rCode(String rCode) {
        setRCode(rCode);
        return this;
    }

    public GetCommissionResponse rMessage(String rMessage) {
        setRMessage(rMessage);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " montantCommission='" + getMontantCommission() + "'" +
            ", rCode='" + getRCode() + "'" +
            ", rMessage='" + getRMessage() + "'" +
            "}";
    }
    
}