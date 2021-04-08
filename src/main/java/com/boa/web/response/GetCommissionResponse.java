package com.boa.web.response;

public class GetCommissionResponse extends GenericResponse {
    private Double montantCommission;

    public GetCommissionResponse() {
    }

    public GetCommissionResponse(Double montantCommission) {
        this.montantCommission = montantCommission;
    }

    public Double getMontantCommission() {
        return this.montantCommission;
    }

    public void setMontantCommission(Double montantCommission) {
        this.montantCommission = montantCommission;
    }

    public GetCommissionResponse montantCommission(Double montantCommission) {
        setMontantCommission(montantCommission);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " montantCommission='" + getMontantCommission() + "'" +
            "}";
    }
}