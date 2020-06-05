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
"compte", 
"institutionId" ,
"langue",
"sourceCardId",
"receiverCardId",
"montant",
"currency", "pays"
})


public class ExecuteCardToOwnCardTransferRequest {
	
	
	@JsonProperty("idoperation")
	private String idoperation;
	@JsonProperty("compte")
	private String compte;
	@JsonProperty("institutionId")
	private String institutionId;
	
	@JsonProperty("langue")
	private String langue;

	@JsonProperty("sourceCardId")
	private String sourceCardId;
	@JsonProperty("receiverCardId")
	private String receiverCardId;
	@JsonProperty("montant")
	private Double montant;
	@JsonProperty("currency")
	private String currency;

	private String pays;
	
	
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

	

	

	@JsonProperty("sourceCardId")
	public String getsourceCardId() {
	return sourceCardId;
	}

	@JsonProperty("sourceCardId")
	public void setsourceCardId(String sourceCardId) {
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

	

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

	public String getIdoperation() {
		return this.idoperation;
	}

	public void setIdoperation(String idoperation) {
		this.idoperation = idoperation;
	}

	public String getLangue() {
		return this.langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public String getSourceCardId() {
		return this.sourceCardId;
	}

	public void setSourceCardId(String sourceCardId) {
		this.sourceCardId = sourceCardId;
	}

	public String getReceiverCardId() {
		return this.receiverCardId;
	}

	public void setReceiverCardId(String receiverCardId) {
		this.receiverCardId = receiverCardId;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}
	public void setAdditionalProperties(Map<String,Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}

}
