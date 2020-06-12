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
"institutionId" ,
"langue",
"cartIdentif",
"idoperation",
})

public class ExecuteBankActivateCardRequest {

	
	    @JsonProperty("compte")
	    private String compte;
	   
	    @JsonProperty("institutionId")
	    private String institutionId;
	   
	    @JsonProperty("langue")
	    private String langue;
	    
	    
	    @JsonProperty("cartIdentif")
	    private String cartIdentif;
		
		@JsonProperty("idoperation")
	    private String idoperation;
	
	    
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



		public String getLangue() {
			return langue;
		}



		public void setLangue(String langue) {
			this.langue = langue;
		}



		public String getCartIdentif() {
			return cartIdentif;
		}



		public void setCartIdentif(String cartIdentif) {
			this.cartIdentif = cartIdentif;
		}


	public ExecuteBankActivateCardRequest() {
	}

	public ExecuteBankActivateCardRequest(String compte, String institutionId, String langue, String cartIdentif, String idoperation) {
		this.compte = compte;
		this.institutionId = institutionId;
		this.langue = langue;
		this.cartIdentif = cartIdentif;
		this.idoperation = idoperation;
	}

	public String getIdoperation() {
		return this.idoperation;
	}

	public void setIdoperation(String idoperation) {
		this.idoperation = idoperation;
	}

	public ExecuteBankActivateCardRequest compte(String compte) {
		this.compte = compte;
		return this;
	}

	public ExecuteBankActivateCardRequest institutionId(String institutionId) {
		this.institutionId = institutionId;
		return this;
	}

	public ExecuteBankActivateCardRequest langue(String langue) {
		this.langue = langue;
		return this;
	}

	public ExecuteBankActivateCardRequest cartIdentif(String cartIdentif) {
		this.cartIdentif = cartIdentif;
		return this;
	}

	public ExecuteBankActivateCardRequest idoperation(String idoperation) {
		this.idoperation = idoperation;
		return this;
	}


	@Override
	public String toString() {
		return "{" +
			" compte='" + getCompte() + "'" +
			", institutionId='" + getInstitutionId() + "'" +
			", langue='" + getLangue() + "'" +
			", cartIdentif='" + getCartIdentif() + "'" +
			", idoperation='" + getIdoperation() + "'" +
			"}";
	}




		
	
}
