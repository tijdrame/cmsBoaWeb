package com.boa.web.response;

public class Reply {
    private String status;
    private Double amount;
    private String currencyCode, account, txnNum, remittanceReferenceNumber, otp;
    private String  errorCode, errorDescription;

    public Reply() {
    }

    public Reply(Double amount, String currencyCode, String account, String txnNum, String remittanceReferenceNumber, String otp, String status, String errorCode, String errorDescription) {
        this.amount = amount;
        this.currencyCode = currencyCode;
        this.account = account;
        this.txnNum = txnNum;
        this.remittanceReferenceNumber = remittanceReferenceNumber;
        this.otp = otp;
        this.status = status;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTxnNum() {
        return this.txnNum;
    }

    public void setTxnNum(String txnNum) {
        this.txnNum = txnNum;
    }

    public String getRemittanceReferenceNumber() {
        return this.remittanceReferenceNumber;
    }

    public void setRemittanceReferenceNumber(String remittanceReferenceNumber) {
        this.remittanceReferenceNumber = remittanceReferenceNumber;
    }

    public String getOtp() {
        return this.otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return this.errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Reply amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Reply currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public Reply account(String account) {
        this.account = account;
        return this;
    }

    public Reply txnNum(String txnNum) {
        this.txnNum = txnNum;
        return this;
    }

    public Reply remittanceReferenceNumber(String remittanceReferenceNumber) {
        this.remittanceReferenceNumber = remittanceReferenceNumber;
        return this;
    }

    public Reply otp(String otp) {
        this.otp = otp;
        return this;
    }

    public Reply status(String status) {
        this.status = status;
        return this;
    }

    public Reply errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Reply errorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " amount='" + getAmount() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", account='" + getAccount() + "'" +
            ", txnNum='" + getTxnNum() + "'" +
            ", remittanceReferenceNumber='" + getRemittanceReferenceNumber() + "'" +
            ", otp='" + getOtp() + "'" +
            ", status='" + getStatus() + "'" +
            ", errorCode='" + getErrorCode() + "'" +
            ", errorDescription='" + getErrorDescription() + "'" +
            "}";
    }

}