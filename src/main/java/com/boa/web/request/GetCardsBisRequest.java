package com.boa.web.request;

public class GetCardsBisRequest {
    
    private String institutionId;
    private String compte;

    private String langue;
    private String pays;
    private String variant;
    private String catCarte;



    public GetCardsBisRequest() {
    }

    public GetCardsBisRequest(String institutionId, String compte, String langue, String pays, String variant, String catCarte) {
        this.institutionId = institutionId;
        this.compte = compte;
        this.langue = langue;
        this.pays = pays;
        this.variant = variant;
        this.catCarte = catCarte;
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

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVariant() {
        return this.variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getCatCarte() {
        return this.catCarte;
    }

    public void setCatCarte(String catCarte) {
        this.catCarte = catCarte;
    }

    public GetCardsBisRequest institutionId(String institutionId) {
        setInstitutionId(institutionId);
        return this;
    }

    public GetCardsBisRequest compte(String compte) {
        setCompte(compte);
        return this;
    }

    public GetCardsBisRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    public GetCardsBisRequest pays(String pays) {
        setPays(pays);
        return this;
    }

    public GetCardsBisRequest variant(String variant) {
        setVariant(variant);
        return this;
    }

    public GetCardsBisRequest catCarte(String catCarte) {
        setCatCarte(catCarte);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " institutionId='" + getInstitutionId() + "'" +
            ", compte='" + getCompte() + "'" +
            ", langue='" + getLangue() + "'" +
            ", pays='" + getPays() + "'" +
            ", variant='" + getVariant() + "'" +
            ", catCarte='" + getCatCarte() + "'" +
            "}";
    }
    
    
    
}