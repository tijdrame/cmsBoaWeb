package com.boa.web.response;

import com.boa.web.response.cardhistory.Amount;
import com.boa.web.response.cardsrequest.Type;

/**
 * PrepareCardToCardTransfer
 */
public class ExecuteCardToCardTransferResponse extends GenericResponse{

    private Amount amount = new Amount();
    private Type type = new Type();

    
    private String dateTime;
    private String identifier;
    private Boolean isHold;
    private String mcc;
    private String faultCode;
    private String faultString;

    public Amount getAmount() {
        return this.amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
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

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Boolean isIsHold() {
        return this.isHold;
    }

    public Boolean getIsHold() {
        return this.isHold;
    }

    public void setIsHold(Boolean isHold) {
        this.isHold = isHold;
    }

    public String getMcc() {
        return this.mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }
    
}