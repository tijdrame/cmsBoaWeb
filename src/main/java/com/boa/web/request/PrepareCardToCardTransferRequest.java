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
@JsonPropertyOrder({ "opidentifier", "clidentifier", "langage", "country", "variant", "cardclidentifier", "cardnumber",
        "cardidentif", "tcardnumb", "rname", "amount", "currency", "entkey", "entval" })
public class PrepareCardToCardTransferRequest {

    @JsonProperty("opidentifier")
    private String opidentifier;
    @JsonProperty("clidentifier")
    private String clidentifier;
    @JsonProperty("langage")
    private String langage;
    @JsonProperty("country")
    private String country;
    @JsonProperty("variant")
    private String variant;
    @JsonProperty("cardclidentifier")
    private String cardclidentifier;
    @JsonProperty("cardnumber")
    private String cardnumber;
    @JsonProperty("cardidentif")
    private String cardidentif;
    @JsonProperty("tcardnumb")
    private String tcardnumb;
    @JsonProperty("rname")
    private String rname;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("entkey")
    private String entkey;
    @JsonProperty("entval")
    private String entval;
    private String institutionId ;
    private String compte ;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("opidentifier")
    public String getOpidentifier() {
        return opidentifier;
    }

    @JsonProperty("opidentifier")
    public void setOpidentifier(String opidentifier) {
        this.opidentifier = opidentifier;
    }

    @JsonProperty("clidentifier")
    public String getClidentifier() {
        return clidentifier;
    }

    @JsonProperty("clidentifier")
    public void setClidentifier(String clidentifier) {
        this.clidentifier = clidentifier;
    }

    @JsonProperty("langage")
    public String getLangage() {
        return langage;
    }

    @JsonProperty("langage")
    public void setLangage(String langage) {
        this.langage = langage;
    }

    @JsonProperty("country")
    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("variant")
    public String getVariant() {
        return variant;
    }

    @JsonProperty("variant")
    public void setVariant(String variant) {
        this.variant = variant;
    }

    @JsonProperty("cardclidentifier")
    public String getCardclidentifier() {
        return cardclidentifier;
    }

    @JsonProperty("cardclidentifier")
    public void setCardclidentifier(String cardclidentifier) {
        this.cardclidentifier = cardclidentifier;
    }

    @JsonProperty("cardnumber")
    public String getCardnumber() {
        return cardnumber;
    }

    @JsonProperty("cardnumber")
    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    @JsonProperty("cardidentif")
    public String getCardidentif() {
        return cardidentif;
    }

    @JsonProperty("cardidentif")
    public void setCardidentif(String cardidentif) {
        this.cardidentif = cardidentif;
    }

    @JsonProperty("tcardnumb")
    public String getTcardnumb() {
        return tcardnumb;
    }

    @JsonProperty("tcardnumb")
    public void setTcardnumb(String tcardnumb) {
        this.tcardnumb = tcardnumb;
    }

    @JsonProperty("rname")
    public String getRname() {
        return rname;
    }

    @JsonProperty("rname")
    public void setRname(String rname) {
        this.rname = rname;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
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

    @JsonProperty("entkey")
    public String getEntkey() {
        return entkey;
    }

    @JsonProperty("entkey")
    public void setEntkey(String entkey) {
        this.entkey = entkey;
    }

    @JsonProperty("entval")
    public String getEntval() {
        return entval;
    }

    @JsonProperty("entval")
    public void setEntval(String entval) {
        this.entval = entval;
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

    public PrepareCardToCardTransferRequest opidentifier(String opidentifier) {
        this.opidentifier = opidentifier;
        return this;
    }

    public PrepareCardToCardTransferRequest clidentifier(String clidentifier) {
        this.clidentifier = clidentifier;
        return this;
    }

    public PrepareCardToCardTransferRequest langage(String langage) {
        this.langage = langage;
        return this;
    }

    public PrepareCardToCardTransferRequest country(String country) {
        this.country = country;
        return this;
    }

    public PrepareCardToCardTransferRequest variant(String variant) {
        this.variant = variant;
        return this;
    }

    public PrepareCardToCardTransferRequest cardclidentifier(String cardclidentifier) {
        this.cardclidentifier = cardclidentifier;
        return this;
    }

    public PrepareCardToCardTransferRequest cardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
        return this;
    }

    public PrepareCardToCardTransferRequest cardidentif(String cardidentif) {
        this.cardidentif = cardidentif;
        return this;
    }

    public PrepareCardToCardTransferRequest tcardnumb(String tcardnumb) {
        this.tcardnumb = tcardnumb;
        return this;
    }

    public PrepareCardToCardTransferRequest rname(String rname) {
        this.rname = rname;
        return this;
    }

    public PrepareCardToCardTransferRequest amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public PrepareCardToCardTransferRequest currency(String currency) {
        this.currency = currency;
        return this;
    }

    public PrepareCardToCardTransferRequest entkey(String entkey) {
        this.entkey = entkey;
        return this;
    }

    public PrepareCardToCardTransferRequest entval(String entval) {
        this.entval = entval;
        return this;
    }

    public PrepareCardToCardTransferRequest institutionId(String institutionId) {
        this.institutionId = institutionId;
        return this;
    }

    public PrepareCardToCardTransferRequest compte(String compte) {
        this.compte = compte;
        return this;
    }

    public PrepareCardToCardTransferRequest additionalProperties(Map<String,Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " opidentifier='" + getOpidentifier() + "'" +
            ", clidentifier='" + getClidentifier() + "'" +
            ", langage='" + getLangage() + "'" +
            ", country='" + getCountry() + "'" +
            ", variant='" + getVariant() + "'" +
            ", cardclidentifier='" + getCardclidentifier() + "'" +
            ", cardnumber='" + getCardnumber() + "'" +
            ", cardidentif='" + getCardidentif() + "'" +
            ", tcardnumb='" + getTcardnumb() + "'" +
            ", rname='" + getRname() + "'" +
            ", amount='" + getAmount() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", entkey='" + getEntkey() + "'" +
            ", entval='" + getEntval() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", compte='" + getCompte() + "'" +
            ", additionalProperties='" + getAdditionalProperties() + "'" +
            "}";
    }

}