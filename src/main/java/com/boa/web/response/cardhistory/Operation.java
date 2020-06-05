package com.boa.web.response.cardhistory;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.boa.web.response.cardsrequest.Type;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "datetime", "identifier", "type", "amount", "description", "is-reversal", "state", "address",
        "is-hold", "resulting-balance", "billing-amount", "direction", "mcc" })
public class Operation {

    @JsonProperty("datetime")
    private String datetime;
    @JsonProperty("identifier")
    private Integer identifier;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("is-reversal")
    private Boolean isReversal;
    @JsonProperty("state")
    private State state;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("is-hold")
    private Boolean isHold;
    @JsonProperty("resulting-balance")
    private ResultingBalance resultingBalance;
    @JsonProperty("billing-amount")
    private BillingAmount billingAmount;
    @JsonProperty("direction")
    private String direction;
    @JsonProperty("mcc")
    private Integer mcc;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("datetime")
    public String getDatetime() {
        return datetime;
    }

    @JsonProperty("datetime")
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @JsonProperty("identifier")
    public Integer getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty("amount")
    public Amount getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("is-reversal")
    public Boolean getIsReversal() {
        return isReversal;
    }

    @JsonProperty("is-reversal")
    public void setIsReversal(Boolean isReversal) {
        this.isReversal = isReversal;
    }

    @JsonProperty("state")
    public State getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(State state) {
        this.state = state;
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("is-hold")
    public Boolean getIsHold() {
        return isHold;
    }

    @JsonProperty("is-hold")
    public void setIsHold(Boolean isHold) {
        this.isHold = isHold;
    }

    @JsonProperty("resulting-balance")
    public ResultingBalance getResultingBalance() {
        return resultingBalance;
    }

    @JsonProperty("resulting-balance")
    public void setResultingBalance(ResultingBalance resultingBalance) {
        this.resultingBalance = resultingBalance;
    }

    @JsonProperty("billing-amount")
    public BillingAmount getBillingAmount() {
        return billingAmount;
    }

    @JsonProperty("billing-amount")
    public void setBillingAmount(BillingAmount billingAmount) {
        this.billingAmount = billingAmount;
    }

    @JsonProperty("direction")
    public String getDirection() {
        return direction;
    }

    @JsonProperty("direction")
    public void setDirection(String direction) {
        this.direction = direction;
    }

    @JsonProperty("mcc")
    public Integer getMcc() {
        return mcc;
    }

    @JsonProperty("mcc")
    public void setMcc(Integer mcc) {
        this.mcc = mcc;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}