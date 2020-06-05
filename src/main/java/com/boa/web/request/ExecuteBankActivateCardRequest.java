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
"operationIdentif",
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



		public String getOperationIdentif() {
			return operationIdentif;
		}



		public void setOperationIdentif(String operationIdentif) {
			this.operationIdentif = operationIdentif;
		}



		@JsonProperty("operationIdentif")
	    
	    private String operationIdentif;
	
}
