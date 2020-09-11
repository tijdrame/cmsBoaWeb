package com.boa.web.response.cardlimit;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "@type", "identifier", "name", "description", "is-active", "is-changeable", "is-permanent",
        "currency", "value", "used-value", "is-per-transaction" })
public class CardLimit_ {

    @JsonProperty("@type")
    private String type;
    @JsonProperty("identifier")
    private Integer identifier;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("is-active")
    private Boolean isActive;
    @JsonProperty("is-changeable")
    private Boolean isChangeable;
    @JsonProperty("is-permanent")
    private Boolean isPermanent;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("value")
    private Integer value;
    @JsonProperty("used-value")
    private Integer usedValue;
    @JsonProperty("is-per-transaction")
    private Boolean isPerTransaction;

    @JsonProperty("expiry-datetime")
    private String expiryDatetime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("@type")
    public String getType() {
        return type;
    }

    @JsonProperty("@type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("identifier")
    public Integer getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("is-active")
    public Boolean getIsActive() {
        return isActive;
    }

    @JsonProperty("is-active")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("is-changeable")
    public Boolean getIsChangeable() {
        return isChangeable;
    }

    @JsonProperty("is-changeable")
    public void setIsChangeable(Boolean isChangeable) {
        this.isChangeable = isChangeable;
    }

    @JsonProperty("is-permanent")
    public Boolean getIsPermanent() {
        return isPermanent;
    }

    @JsonProperty("is-permanent")
    public void setIsPermanent(Boolean isPermanent) {
        this.isPermanent = isPermanent;
    }

    @JsonProperty("currency")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("value")
    public Integer getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Integer value) {
        this.value = value;
    }

    @JsonProperty("used-value")
    public Integer getUsedValue() {
        return usedValue;
    }

    @JsonProperty("used-value")
    public void setUsedValue(Integer usedValue) {
        this.usedValue = usedValue;
    }

    @JsonProperty("is-per-transaction")
    public Boolean getIsPerTransaction() {
        return isPerTransaction;
    }

    @JsonProperty("is-per-transaction")
    public void setIsPerTransaction(Boolean isPerTransaction) {
        this.isPerTransaction = isPerTransaction;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Boolean isIsActive() {
        return this.isActive;
    }

    public Boolean isIsChangeable() {
        return this.isChangeable;
    }

    public Boolean isIsPermanent() {
        return this.isPermanent;
    }

    public Boolean isIsPerTransaction() {
        return this.isPerTransaction;
    }

    public String getExpiryDatetime() {
        return this.expiryDatetime;
    }

    public void setExpiryDatetime(String expiryDatetime) {
        this.expiryDatetime = expiryDatetime;
    }
    public void setAdditionalProperties(Map<String,Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

}