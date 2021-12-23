package com.boa.web.request;

import java.time.LocalDate;

public class CardlessRequest {
    private String senderAccountNumber, destCellPhone, institutionId, currency;
    private LocalDate withdrawalDueDate;
    private Double amount;
    private String langue;

    public CardlessRequest() {
    }

    public CardlessRequest(String senderAccountNumber, String destCellPhone, String institutionId,
    LocalDate withdrawalDueDate, String currency, Double amount) {
        this.senderAccountNumber = senderAccountNumber;
        this.destCellPhone = destCellPhone;
        this.institutionId = institutionId;
        this.withdrawalDueDate = withdrawalDueDate;
        this.currency = currency;
        this.amount = amount;
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

    public LocalDate getWithdrawalDueDate() {
        return this.withdrawalDueDate;
    }

    public void setWithdrawalDueDate(LocalDate withdrawalDueDate) {
        this.withdrawalDueDate = withdrawalDueDate;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public CardlessRequest senderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
        return this;
    }

    public CardlessRequest destCellPhone(String destCellPhone) {
        this.destCellPhone = destCellPhone;
        return this;
    }

    public CardlessRequest institutionId(String institutionId) {
        this.institutionId = institutionId;
        return this;
    }

    public CardlessRequest withdrawalDueDate(LocalDate withdrawalDueDate) {
        this.withdrawalDueDate = withdrawalDueDate;
        return this;
    }

    public CardlessRequest currency(String currency) {
        this.currency = currency;
        return this;
    }

    public CardlessRequest amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }


    @Override
    public String toString() {
        return "{" +
            " senderAccountNumber='" + getSenderAccountNumber() + "'" +
            ", destCellPhone='" + getDestCellPhone() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", withdrawalDueDate='" + getWithdrawalDueDate() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", amount='" + getAmount() + "'" +
            ", langue='" + getLangue() + "'" +
            "}";
    }

}