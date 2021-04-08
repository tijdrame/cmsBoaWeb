package com.boa.web.request;

public class VerifSeuilRequest {
    
    private String compte;
    private Double  montant;
    private String codeOperation;
    private String langue;
    private String country;

    public VerifSeuilRequest() {
    }

    public VerifSeuilRequest(String compte, Double montant, String codeOperation, String langue, String country) {
        this.compte = compte;
        this.montant = montant;
        this.codeOperation = codeOperation;
        this.langue = langue;
        this.country = country;
    }

    public String getCompte() {
        return this.compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public Double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCodeOperation() {
        return this.codeOperation;
    }

    public void setCodeOperation(String codeOperation) {
        this.codeOperation = codeOperation;
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

    public VerifSeuilRequest compte(String compte) {
        setCompte(compte);
        return this;
    }

    public VerifSeuilRequest montant(Double montant) {
        setMontant(montant);
        return this;
    }

    public VerifSeuilRequest codeOperation(String codeOperation) {
        setCodeOperation(codeOperation);
        return this;
    }

    public VerifSeuilRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    public VerifSeuilRequest country(String country) {
        setCountry(country);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " compte='" + getCompte() + "'" +
            ", montant='" + getMontant() + "'" +
            ", codeOperation='" + getCodeOperation() + "'" +
            ", langue='" + getLangue() + "'" +
            ", country='" + getCountry() + "'" +
            "}";
    }


}
