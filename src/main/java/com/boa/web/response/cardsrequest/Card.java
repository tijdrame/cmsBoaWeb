package com.boa.web.response.cardsrequest;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "client-card-identifier", "embossed-name", "number", "currency", "available-balance", "type",
        "category", "brand", "status", "active", "pinNotSet", "expiry-date", "linked-accounts", "reissuable",
        "client-card-account-owner", "supplementary-card" })
public class Card {

    @JsonProperty("client-card-identifier")
    private String clientCardIdentifier;
    @JsonProperty("embossed-name")
    private String embossedName;
    @JsonProperty("number")
    private String number;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("available-balance")
    private Double availableBalance;
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
    @JsonProperty("linked-accounts")
    private String linkedAccounts;
    @JsonProperty("reissuable")
    private Boolean reissuable;
    @JsonProperty("client-card-account-owner")
    private Boolean clientCardAccountOwner;
    @JsonProperty("supplementary-card")
    private Boolean supplementaryCard;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    
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
    public Double getAvailableBalance() {
        return availableBalance;
    }

    @JsonProperty("available-balance")
    public void setAvailableBalance(Double availableBalance) {
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

    @JsonProperty("linked-accounts")
    public String getLinkedAccounts() {
        /*System.out.println("nonnnnnn++");
        if(linkedAccounts!=null && !linkedAccounts.isEmpty()&& linkedAccounts.contains("[")){
            
            System.out.println("ouiiiiiiiiii===="+linkedAccounts);
            linkedAccounts = linkedAccounts.replace("[", "");
            linkedAccounts = linkedAccounts.replace("]", "");
            String[] str =linkedAccounts.split(",");
            System.out.println("str[]="+str.toString());
            String retour = "";
            for (String it : str) {
                System.out.println("it="+it);
                retour += it; 
            }
            System.out.println("apressss===="+retour);
            linkedAccounts = retour;
        }*/
        return linkedAccounts;
    }

    @JsonProperty("linked-accounts")
    public void setLinkedAccounts(String linkedAccounts) {
        
        this.linkedAccounts = linkedAccounts;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}