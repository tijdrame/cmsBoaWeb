package com.boa.web.request;

/**
 * CardsRequest
 */
public class CardsDetailRequest {

    private String compte;
    private String langue;
    private String pays;
    private String variant;
    private String cartIdentif;
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

    public String getCartIdentif() {
        return cartIdentif;
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

    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    @Override
    public String toString() {
        return "{" + " compte='" + getCompte() + "'" + ", langue='" + getLangue() + "'" + ", pays='" + getPays() + "'"
                + ", variant='" + getVariant() + "'" + ", cartIdentif='" + getCartIdentif() + "'" + ", institutionId='"
                + getInstitutionId() + "'" + "}";
    }
}
