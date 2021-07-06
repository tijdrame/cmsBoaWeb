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
@JsonPropertyOrder({ "compte", "langue", "pays", "variant", "cartIdentif", "startNum", "maxCount", "dateFrom",
        "dateTo", "hold", "institutionId" })
public class CardHistoryRequest {

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
    @JsonProperty("startNum")
    private Integer startNum;
    @JsonProperty("maxCount")
    private Integer maxCount;
    @JsonProperty("dateFrom")
    private String dateFrom;
    @JsonProperty("dateTo")
    private String dateTo;
    @JsonProperty("hold")
    private String hold;
    @JsonProperty("institutionId")
    private String institutionId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("compte")
    public String getCompte() {
        return compte;
    }

    @JsonProperty("compte")
    public void setCompte(String compte) {
        this.compte = compte;
    }

    @JsonProperty("institutionId")
    public String getInstitutionId() {
        return institutionId;
    }

    @JsonProperty("institutionId")
    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
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

    @JsonProperty("startNum")
    public Integer getStartNum() {
        return startNum;
    }

    @JsonProperty("startNum")
    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    @JsonProperty("maxCount")
    public Integer getMaxCount() {
        return maxCount;
    }

    @JsonProperty("maxCount")
    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    @JsonProperty("dateFrom")
    public String getDateFrom() {
        return dateFrom;
    }

    @JsonProperty("dateFrom")
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    @JsonProperty("dateTo")
    public String getDateTo() {
        return dateTo;
    }

    @JsonProperty("dateTo")
    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @JsonProperty("hold")
    public String getHold() {
        return hold;
    }

    @JsonProperty("hold")
    public void setHold(String hold) {
        this.hold = hold;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "{" +
            " compte='" + compte + "'" +
            ", langue='" + langue + "'" +
            ", pays='" + pays + "'" +
            ", variant='" + variant + "'" +
            ", cartIdentif='" + cartIdentif + "'" +
            ", startNum='" + startNum + "'" +
            ", maxCount='" + maxCount + "'" +
            ", dateFrom='" + dateFrom + "'" +
            ", dateTo='" + dateTo + "'" +
            ", hold='" + hold + "'" +
            ", institutionId='" + institutionId + "'" +
            ", additionalProperties='" + additionalProperties + "'" +
            "}";
    }

}