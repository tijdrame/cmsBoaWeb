package com.boa.web.response;

/**
 * PrepareCardToCardTransfer
 */
public class PrepareCardToCardTransferResponse extends GenericResponse{

    private Double amount;

    private String currency;

    private String faultCode;
    private String faultString;

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFaultCode() {
        return this.faultCode;
    }

    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

    public String getFaultString() {
        return this.faultString;
    }

    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
    
}