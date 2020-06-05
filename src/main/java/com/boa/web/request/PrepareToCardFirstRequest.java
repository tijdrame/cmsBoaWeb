package com.boa.web.request;

/**
 * PrepareToCardFirstRequest
 */
public class PrepareToCardFirstRequest {

    private String idoperation, compte, institutionId;
    private String langue,  pays, variant;
    private String cardIdClient, receiverCardNumber, sourceCardidClient;
    private String receiverName;
    private Double montant;
    private String currency, entkey, entval;


    public String getCompte() {
        return this.compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
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

    public String getCardIdClient() {
        return this.cardIdClient;
    }

    public void setCardIdClient(String cardIdClient) {
        this.cardIdClient = cardIdClient;
    }

    public String getReceiverCardNumber() {
        return this.receiverCardNumber;
    }

    public void setReceiverCardNumber(String receiverCardNumber) {
        this.receiverCardNumber = receiverCardNumber;
    }

    public String getReceiverName() {
        return this.receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEntkey() {
        return this.entkey;
    }

    public void setEntkey(String entkey) {
        this.entkey = entkey;
    }

    public String getEntval() {
        return this.entval;
    }

    public void setEntval(String entval) {
        this.entval = entval;
    }

    public String getIdoperation() {
        return this.idoperation;
    }

    public void setIdoperation(String idoperation) {
        this.idoperation = idoperation;
    }

    public String getSourceCardidClient() {
        return this.sourceCardidClient;
    }

    public void setSourceCardidClient(String sourceCardidClient) {
        this.sourceCardidClient = sourceCardidClient;
    }

    @Override
    public String toString() {
        return "{" +
            " idoperation='" + getIdoperation() + "'" +
            ", compte='" + getCompte() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", langue='" + getLangue() + "'" +
            ", country='" + getPays() + "'" +
            ", variant='" + getVariant() + "'" +
            ", cardIdClient='" + getCardIdClient() + "'" +
            ", receiverCardNumber='" + getReceiverCardNumber() + "'" +
            ", receiverName='" + getReceiverName() + "'" +
            ", montant='" + getMontant() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", entkey='" + getEntkey() + "'" +
            ", entval='" + getEntval() + "'" +
            "}";
    }

}