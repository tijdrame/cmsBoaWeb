package com.boa.web.request;

public class GetCardsBisRequest {
    
    private String institutionId;
    private String compte;


    public GetCardsBisRequest() {
    }

    public GetCardsBisRequest(String institutionId, String compte) {
        this.institutionId = institutionId;
        this.compte = compte;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getCompte() {
        return this.compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public GetCardsBisRequest institutionId(String institutionId) {
        setInstitutionId(institutionId);
        return this;
    }

    public GetCardsBisRequest compte(String compte) {
        setCompte(compte);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " institutionId='" + getInstitutionId() + "'" +
            ", compte='" + getCompte() + "'" +
            "}";
    }

    
    
}