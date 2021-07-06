package com.boa.web.request;

/**
 * PrepareChangeCardOption
 */
public class PrepareChangeCardOptionRequest {

    private String compte;
    private String langue;
    private String pays;
    private String variant;
    private String cartIdentif;
    private String action;
    private String entryKey;
    private String entryValue;
    private String institutionId;

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

    public String getCartIdentif() {
        return this.cartIdentif;
    }

    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntryKey() {
        return this.entryKey;
    }

    public void setEntryKey(String entryKey) {
        this.entryKey = entryKey;
    }

    public String getEntryValue() {
        return this.entryValue;
    }

    public void setEntryValue(String entryValue) {
        this.entryValue = entryValue;
    }

    @Override
    public String toString() {
        return "{" +
            " compte='" + compte + "'" +
            ", langue='" + langue + "'" +
            ", pays='" + pays + "'" +
            ", variant='" + variant + "'" +
            ", cartIdentif='" + cartIdentif + "'" +
            ", action='" + action + "'" +
            ", entryKey='" + entryKey + "'" +
            ", entryValue='" + entryValue + "'" +
            ", institutionId='" + institutionId + "'" +
            "}";
    }

}