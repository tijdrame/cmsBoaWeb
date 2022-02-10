package com.boa.web.request;

import java.time.LocalDate;

public class CardlessRemittanceByCardNumberRequest {
    private String senderCardNumber, destCellPhone, institutionId, currency;
    private LocalDate withdrawalDueDate;
    private Double amount;
    private String langue;

    public CardlessRemittanceByCardNumberRequest() {
    }

    public CardlessRemittanceByCardNumberRequest(String senderCardNumber, String destCellPhone, String institutionId, String currency, LocalDate withdrawalDueDate, Double amount, String langue) {
        this.senderCardNumber = senderCardNumber;
        this.destCellPhone = destCellPhone;
        this.institutionId = institutionId;
        this.currency = currency;
        this.withdrawalDueDate = withdrawalDueDate;
        this.amount = amount;
        this.langue = langue;
    }

    public String getSenderCardNumber() {
        return this.senderCardNumber;
    }

    public void setSenderCardNumber(String senderCardNumber) {
        this.senderCardNumber = senderCardNumber;
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

    public CardlessRemittanceByCardNumberRequest senderCardNumber(String senderCardNumber) {
        setSenderCardNumber(senderCardNumber);
        return this;
    }

    public CardlessRemittanceByCardNumberRequest destCellPhone(String destCellPhone) {
        setDestCellPhone(destCellPhone);
        return this;
    }

    public CardlessRemittanceByCardNumberRequest institutionId(String institutionId) {
        setInstitutionId(institutionId);
        return this;
    }

    public CardlessRemittanceByCardNumberRequest currency(String currency) {
        setCurrency(currency);
        return this;
    }

    public CardlessRemittanceByCardNumberRequest withdrawalDueDate(LocalDate withdrawalDueDate) {
        setWithdrawalDueDate(withdrawalDueDate);
        return this;
    }

    public CardlessRemittanceByCardNumberRequest amount(Double amount) {
        setAmount(amount);
        return this;
    }

    public CardlessRemittanceByCardNumberRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " senderCardNumber='" + getSenderCardNumber() + "'" +
            ", destCellPhone='" + getDestCellPhone() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", withdrawalDueDate='" + getWithdrawalDueDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", langue='" + getLangue() + "'" +
            "}";
    }

}
