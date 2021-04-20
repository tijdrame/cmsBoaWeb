package com.boa.web.request;

public class GetCardsBisRequest {
    
    private String institutionId;
    private String comptes;


    public GetCardsBisRequest() {
    }

    public GetCardsBisRequest(String institutionId, String comptes) {
        this.institutionId = institutionId;
        this.comptes = comptes;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getComptes() {
        return this.comptes;
    }

    public void setComptes(String comptes) {
        this.comptes = comptes;
    }

    

    public GetCardsBisRequest institutionId(String institutionId) {
        setInstitutionId(institutionId);
        return this;
    }

    public GetCardsBisRequest comptes(String comptes) {
        setComptes(comptes);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " institutionId='" + getInstitutionId() + "'" +
            ", comptes='" + getComptes() + "'" +
            "}";
    }
    
}