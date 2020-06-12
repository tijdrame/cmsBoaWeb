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
"compte",
"institutionId",
"langue",
"pays",
"variant",
"cartIdentif",
"action",
"entKey",
"entValue"
})
public class GetCardBankActivationParametersRequest {
	

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
@JsonProperty("cartIdentif")
private String cartIdentif;
@JsonProperty("action")
private String action;
@JsonProperty("entKey")
private String entKey;
@JsonProperty("entValue")
private String entValue;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();


public String getLangue() {
	return langue;
}
public void setLangue(String langue) {
	this.langue = langue;
}
public String getPays() {
	return pays;
}
public void setPays(String pays) {
	this.pays = pays;
}
public String getVariant() {
	return variant;
}
public void setVariant(String variant) {
	this.variant = variant;
}
public String getCartIdentif() {
	return cartIdentif;
}
public void setCartIdentif(String cartIdentif) {
	this.cartIdentif = cartIdentif;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
public String getEntKey() {
	return entKey;
}
public void setEntKey(String entKey) {
	this.entKey = entKey;
}
public String getEntValue() {
	return entValue;
}
public void setEntValue(String entValue) {
	this.entValue = entValue;
}
public Map<String, Object> getAdditionalProperties() {
	return additionalProperties;
}
public void setAdditionalProperties(Map<String, Object> additionalProperties) {
	this.additionalProperties = additionalProperties;
}

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


	@Override
	public String toString() {
		return "{" +
			" compte='" + compte + "'" +
			", institutionId='" + institutionId + "'" +
			", langue='" + langue + "'" +
			", pays='" + pays + "'" +
			", variant='" + variant + "'" +
			", cartIdentif='" + cartIdentif + "'" +
			", action='" + action + "'" +
			", entKey='" + entKey + "'" +
			", entValue='" + entValue + "'" +
			", additionalProperties='" + additionalProperties + "'" +
			"}";
	}

	
	
	

}
