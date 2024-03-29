package com.boa.web.request;

import java.time.LocalDate;

public class CardlessRequest {
    private String senderAccountNumber, destCellPhone, institutionId, currency;
    private LocalDate withdrawalDueDate;
    private Double amount;
    private String langue;
    private String senderType;


    public CardlessRequest() {
    }

    public CardlessRequest(String senderAccountNumber, String destCellPhone, String institutionId, String currency, LocalDate withdrawalDueDate, Double amount, String langue, String senderType) {
        this.senderAccountNumber = senderAccountNumber;
        this.destCellPhone = destCellPhone;
        this.institutionId = institutionId;
        this.currency = currency;
        this.withdrawalDueDate = withdrawalDueDate;
        this.amount = amount;
        this.langue = langue;
        this.senderType = senderType;
    }

    public String getSenderAccountNumber() {
        return this.senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getDestCellPhone() {
        return this.destCellPhone;
    }

    public void setDestCellPhone(String destCellPhone) {
        this.destCellPhone = destCellPhone;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getWithdrawalDueDate() {
        return this.withdrawalDueDate;
    }

    public void setWithdrawalDueDate(LocalDate withdrawalDueDate) {
        this.withdrawalDueDate = withdrawalDueDate;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getSenderType() {
        return this.senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public CardlessRequest senderAccountNumber(String senderAccountNumber) {
        setSenderAccountNumber(senderAccountNumber);
        return this;
    }

    public CardlessRequest destCellPhone(String destCellPhone) {
        setDestCellPhone(destCellPhone);
        return this;
    }

    public CardlessRequest institutionId(String institutionId) {
        setInstitutionId(institutionId);
        return this;
    }

    public CardlessRequest currency(String currency) {
        setCurrency(currency);
        return this;
    }

    public CardlessRequest withdrawalDueDate(LocalDate withdrawalDueDate) {
        setWithdrawalDueDate(withdrawalDueDate);
        return this;
    }

    public CardlessRequest amount(Double amount) {
        setAmount(amount);
        return this;
    }

    public CardlessRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    public CardlessRequest senderType(String senderType) {
        setSenderType(senderType);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " senderAccountNumber='" + getSenderAccountNumber() + "'" +
            ", destCellPhone='" + getDestCellPhone() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", withdrawalDueDate='" + getWithdrawalDueDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", langue='" + getLangue() + "'" +
            ", senderType='" + getSenderType() + "'" +
            "}";
    }
    

}