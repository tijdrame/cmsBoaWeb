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
@JsonPropertyOrder({ "idoperation", "idClient", "langue", "pays", "variant", "cartIdentif", "entKey1",
        "entValue1", "entKey2", "entValue2", "entKey3", "entValue3" })
public class CheckBankActivateCardRequest {

    @JsonProperty("idoperation")
    private String idoperation;
    @JsonProperty("idClient")
    private String idClient;
    private String institutionId, compte;
    @JsonProperty("langue")
    private String langue;
    @JsonProperty("pays")
    private String pays;
    @JsonProperty("variant")
    private String variant;
    @JsonProperty("cartIdentif")
    private String cartIdentif;
    @JsonProperty("entKey1")
    private String entKey1;
    @JsonProperty("entValue1")
    private String entValue1;
    @JsonProperty("entKey2")
    private String entKey2;
    @JsonProperty("entValue2")
    private String entValue2;
    @JsonProperty("entKey3")
    private String entKey3;
    @JsonProperty("entValue3")
    private String entValue3;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("idoperation")
    public String getIdoperation() {
        return this.idoperation;
    }

    public void setIdoperation(String idoperation) {
        this.idoperation = idoperation;
    }

    
    

    @JsonProperty("idClient")
    public String getIdClient() {
        return idClient;
    }

    @JsonProperty("idClient")
    public void setIdClient(String idClient) {
        this.idClient = idClient;
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

    @JsonProperty("entKey1")
    public String getEntKey1() {
        return entKey1;
    }

    @JsonProperty("entKey1")
    public void setEntKey1(String entKey1) {
        this.entKey1 = entKey1;
    }

    @JsonProperty("entValue1")
    public String getEntValue1() {
        return entValue1;
    }

    @JsonProperty("entValue1")
    public void setEntValue1(String entValue1) {
        this.entValue1 = entValue1;
    }

    @JsonProperty("entKey2")
    public String getEntKey2() {
        return entKey2;
    }

    @JsonProperty("entKey2")
    public void setEntKey2(String entKey2) {
        this.entKey2 = entKey2;
    }

    @JsonProperty("entValue2")
    public String getEntValue2() {
        return entValue2;
    }

    @JsonProperty("entValue2")
    public void setEntValue2(String entValue2) {
        this.entValue2 = entValue2;
    }

    @JsonProperty("entKey3")
    public String getEntKey3() {
        return entKey3;
    }

    @JsonProperty("entKey3")
    public void setEntKey3(String entKey3) {
        this.entKey3 = entKey3;
    }

    @JsonProperty("entValue3")
    public String getEntValue3() {
        return entValue3;
    }

    @JsonProperty("entValue3")
    public void setEntValue3(String entValue3) {
        this.entValue3 = entValue3;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getCompte() {
        return this.compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }
    public void setAdditionalProperties(Map<String,Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public String toString() {
        return "{" +
            " idoperation='" + idoperation + "'" +
            ", idClient='" + idClient + "'" +
            ", institutionId='" + institutionId + "'" +
            ", compte='" + compte + "'" +
            ", langue='" + langue + "'" +
            ", pays='" + pays + "'" +
            ", variant='" + variant + "'" +
            ", cartIdentif='" + cartIdentif + "'" +
            ", entKey1='" + entKey1 + "'" +
            ", entValue1='" + entValue1 + "'" +
            ", entKey2='" + entKey2 + "'" +
            ", entValue2='" + entValue2 + "'" +
            ", entKey3='" + entKey3 + "'" +
            ", entValue3='" + entValue3 + "'" +
            ", additionalProperties='" + additionalProperties + "'" +
            "}";
    }

}