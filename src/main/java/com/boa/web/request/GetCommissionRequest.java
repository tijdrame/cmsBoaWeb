package com.boa.web.request;

public class GetCommissionRequest {
    private Double montant;
    private String devise;
    private String codeOperation;
    private String compte;
    private String langue;
    private String country;


    public GetCommissionRequest() {
    }

    public GetCommissionRequest(Double montant, String devise, String codeOperation, String compte, String langue, String country) {
        this.montant = montant;
        this.devise = devise;
        this.codeOperation = codeOperation;
        this.compte = compte;
        this.langue = langue;
        this.country = country;
    }

    public Double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return this.devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public String getCodeOperation() {
        return this.codeOperation;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
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

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public GetCommissionRequest montant(Double montant) {
        setMontant(montant);
        return this;
    }

    public GetCommissionRequest devise(String devise) {
        setDevise(devise);
        return this;
    }

    public GetCommissionRequest codeOperation(String codeOperation) {
        setCodeOperation(codeOperation);
        return this;
    }

    public GetCommissionRequest compte(String compte) {
        setCompte(compte);
        return this;
    }

    public GetCommissionRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    public GetCommissionRequest country(String country) {
        setCountry(country);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " montant='" + getMontant() + "'" +
            ", devise='" + getDevise() + "'" +
            ", codeOperation='" + getCodeOperation() + "'" +
            ", compte='" + getCompte() + "'" +
            ", langue='" + getLangue() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }
    
}
