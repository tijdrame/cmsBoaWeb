package com.boa.web.response.cardsrequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "client-card-identifier", "embossed-name", "number", "currency", "available-balance", "type",
        "category", "brand", "status", "active", "pinNotSet", "expiry-date", "reissuable",
        "client-card-account-owner", "supplementary-card", "output-string-field" })
public class CardDetails {

    @JsonProperty("client-card-identifier")
    private String clientCardIdentifier;
    @JsonProperty("embossed-name")
    private String embossedName;
    @JsonProperty("number")
    private String number;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("available-balance")
    private Integer availableBalance;
    @JsonProperty("type")
    private Type type;
    @JsonProperty("category")
    private String category;
    @JsonProperty("brand")
    private String brand;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("pinNotSet")
    private Boolean pinNotSet;
    @JsonProperty("expiry-date")
    private String expiryDate;
    @JsonProperty("reissuable")
    private Boolean reissuable;
    @JsonProperty("client-card-account-owner")
    private Boolean clientCardAccountOwner;
    @JsonProperty("supplementary-card")
    private Boolean supplementaryCard;
    @JsonProperty("output-string-field")
    private OutputStringField outputStringField;

    @JsonIgnore
    private String numberCard;

    @JsonProperty("client-card-identifier")
    public String getClientCardIdentifier() {      
        return clientCardIdentifier;
    }

    @JsonProperty("client-card-identifier")
    public void setClientCardIdentifier(String clientCardIdentifier) {
        this.clientCardIdentifier = clientCardIdentifier;
    }

    @JsonProperty("embossed-name")
    public String getEmbossedName() {
        return embossedName;
    }

    @JsonProperty("embossed-name")
    public void setEmbossedName(String embossedName) {
        this.embossedName = embossedName;
    }

    @JsonProperty("number")
    public String getNumber() {
        String lastCaract, firstCaract = "";
        if(number!=null && number.length()>3){
            lastCaract = number.substring(number.length() - 3);
            
            firstCaract = "*********";
            number = firstCaract +lastCaract;
        }
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("available-balance")
    public Integer getAvailableBalance() {
        return availableBalance;
    }

    @JsonProperty("available-balance")
    public void setAvailableBalance(Integer availableBalance) {
        this.availableBalance = availableBalance;
    }

    @JsonProperty("type")
    public Type getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Type type) {
        this.type = type;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("brand")
    public String getBrand() {
        return brand;
    }

    @JsonProperty("brand")
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonProperty("pinNotSet")
    public Boolean getPinNotSet() {
        return pinNotSet;
    }

    @JsonProperty("pinNotSet")
    public void setPinNotSet(Boolean pinNotSet) {
        this.pinNotSet = pinNotSet;
    }

    @JsonProperty("expiry-date")
    public String getExpiryDate() {
        return expiryDate;
    }

    @JsonProperty("expiry-date")
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @JsonProperty("reissuable")
    public Boolean getReissuable() {
        return reissuable;
    }

    @JsonProperty("reissuable")
    public void setReissuable(Boolean reissuable) {
        this.reissuable = reissuable;
    }

    @JsonProperty("client-card-account-owner")
    public Boolean getClientCardAccountOwner() {
        return clientCardAccountOwner;
    }

    @JsonProperty("client-card-account-owner")
    public void setClientCardAccountOwner(Boolean clientCardAccountOwner) {
        this.clientCardAccountOwner = clientCardAccountOwner;
    }

    @JsonProperty("supplementary-card")
    public Boolean getSupplementaryCard() {
        return supplementaryCard;
    }

    @JsonProperty("supplementary-card")
    public void setSupplementaryCard(Boolean supplementaryCard) {
        this.supplementaryCard = supplementaryCard;
    }

    @JsonProperty("output-string-field")
    public OutputStringField getOutputStringField() {
        return outputStringField;
    }

    @JsonProperty("output-string-field")
    public void setOutputStringField(OutputStringField outputStringField) {
        this.outputStringField = outputStringField;
    }

    public Boolean isActive() {
        return this.active;
    }

    public Boolean isPinNotSet() {
        return this.pinNotSet;
    }

    public Boolean isReissuable() {
        return this.reissuable;
    }

    public Boolean isClientCardAccountOwner() {
        return this.clientCardAccountOwner;
    }

    public Boolean isSupplementaryCard() {
        return this.supplementaryCard;
    }

    public String getNumberCard() {
        return this.numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

}