package com.boa.web.request;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "compte", "langue", "pays", "variant", "cartIdentif", "limitIdent", "amount", "currency",
        "countClimit", "isActive", "duration" })
public class ChangeCardRequest {

    @JsonProperty("compte")
    private String compte;
    @JsonProperty("langue")
    private String langue;
    @JsonProperty("pays")
    private String pays;
    @JsonProperty("variant")
    private String variant;
    @JsonProperty("cartIdentif")
    private String cartIdentif;
    @JsonProperty("limitIdent")
    private String limitIdent;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("countClimit")
    private String countClimit;
    @JsonProperty("isActive")
    private String isActive;
    @JsonProperty("duration")
    private String duration;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    private String institutionId;

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    @JsonProperty("compte")
    public String getCompte() {
        return compte;
    }

    @JsonProperty("compte")
    public void setCompte(String compte) {
        this.compte = compte;
    }

    @JsonProperty("langue")
    public String getLangue() {
        return langue;
    }

    @JsonProperty("langue")
    public void setLangue(String langue) {
        this.langue = langue;
    }

    @JsonProperty("pays")
    public String getPays() {
        return pays;
    }

    @JsonProperty("pays")
    public void setPays(String pays) {
        this.pays = pays;
    }

    @JsonProperty("variant")
    public String getVariant() {
        return variant;
    }

    @JsonProperty("variant")
    public void setVariant(String variant) {
        this.variant = variant;
    }

    @JsonProperty("cartIdentif")
    public String getCartIdentif() {
        return cartIdentif;
    }

    @JsonProperty("cartIdentif")
    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    @JsonProperty("limitIdent")
    public String getLimitIdent() {
        return limitIdent;
    }

    @JsonProperty("limitIdent")
    public void setLimitIdent(String limitIdent) {
        this.limitIdent = limitIdent;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("countClimit")
    public String getCountClimit() {
        return countClimit;
    }

    @JsonProperty("countClimit")
    public void setCountClimit(String countClimit) {
        this.countClimit = countClimit;
    }

    @JsonProperty("isActive")
    public String getIsActive() {
        return isActive;
    }

    @JsonProperty("isActive")
    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
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