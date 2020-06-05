package com.boa.web.request;

/**
 * CardsRequest
 */
public class CardsRequest {

    private String compte;
    private String langue;
    private String pays;
    private String variant;
    private String catCarte;
    private String institutionId;

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    // Getter Methods

    public String getCompte() {
        return compte;
    }

    public String getLangue() {
        return langue;
    }

    public String getPays() {
        return pays;
    }

    public String getVariant() {
        return variant;
    }

    public String getCatCarte() {
        return catCarte;
    }

    // Setter Methods

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public void setCatCarte(String catCarte) {
        this.catCarte = catCarte;
    }

    public String toString() {
        return "compte=" + getCompte() + ", langue='" + getLangue() + "'";
    }

}