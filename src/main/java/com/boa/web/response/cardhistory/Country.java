package com.boa.web.response.cardhistory;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "name", "alpha-2-code", "alpha-3-code", "number-3-code" })
public class Country {

    @JsonProperty("name")
    private String name;
    @JsonProperty("alpha-2-code")
    private String alpha2Code;
    @JsonProperty("alpha-3-code")
    private String alpha3Code;
    @JsonProperty("number-3-code")
    private Integer number3Code;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("alpha-2-code")
    public String getAlpha2Code() {
        return alpha2Code;
    }

    @JsonProperty("alpha-2-code")
    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    @JsonProperty("alpha-3-code")
    public String getAlpha3Code() {
        return alpha3Code;
    }

    @JsonProperty("alpha-3-code")
    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    @JsonProperty("number-3-code")
    public Integer getNumber3Code() {
        return number3Code;
    }

    @JsonProperty("number-3-code")
    public void setNumber3Code(Integer number3Code) {
        this.number3Code = number3Code;
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