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
@JsonPropertyOrder({
"idoperation",
"clidentifier",
"compte", 
"institutionId" ,
"langue",
"pays",
"variant",
"sourceCardId",
"receiverCardId",
"montant",
"currency",
"entkey",
"entval"
})


public class PrepareCardToOwnCardTransferRequest {

	public String getCompte() {
		return compte;
	}

	public void setCompte(String compte) {
		this.compte = compte;
	}

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	@JsonProperty("idoperation")
	private String idoperation;
	@JsonProperty("clidentifier")
	private String clidentifier;
	public String getClidentifier() {
		return clidentifier;
	}

	public void setClidentifier(String clidentifier) {
		this.clidentifier = clidentifier;
	}

	@JsonProperty("compte")
	private String compte;
	@JsonProperty("institutionId")
	private String institutionId;
	
	@JsonProperty("langue")
	private String langue;
	@JsonProperty("pays")
	private String pays;
	@JsonProperty("variant")
	private String variant;
	@JsonProperty("sourceCardId")
	private String sourceCardId;
	@JsonProperty("receiverCardId")
	private String receiverCardId;
	@JsonProperty("montant")
	private Double montant;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("entkey")
	private String entkey;
	@JsonProperty("entval")
	private String entval;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("idoperation")
	public String getidoperation() {
	return idoperation;
	}

	@JsonProperty("idoperation")
	public void setidoperation(String idoperation) {
	this.idoperation = idoperation;
	}

	
	@JsonProperty("langue")
	public String getlangue() {
	return langue;
	}

	@JsonProperty("langue")
	public void setlangue(String langue) {
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

	@JsonProperty("srcaccnumb")
	public String getSourceCardId() {
	return sourceCardId;
	}

	@JsonProperty("srcaccnumb")
	public void setSourceCardId(String sourceCardId) {
	this.sourceCardId = sourceCardId;
	}

	@JsonProperty("receiverCardId")
	public String getreceiverCardId() {
	return receiverCardId;
	}

	@JsonProperty("receiverCardId")
	public void setreceiverCardId(String receiverCardId) {
	this.receiverCardId = receiverCardId;
	}

	@JsonProperty("montant")
	public Double getMontant() {
	return montant;
	}

	@JsonProperty("montant")
	public void setAmount(Double montant) {
	this.montant = montant;
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
}
