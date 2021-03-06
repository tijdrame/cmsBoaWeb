package com.boa.web.response;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/* MEL31012020 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"infoClient"
})
@XmlRootElement
public class IdClientResponse  extends GenericResponse{

	@XmlAttribute(name = "idClient")
	@JsonProperty("idClient")
	private String idClient;
	@JsonProperty("compte")
	private String compte;
	@JsonProperty("institutionId")
	private String institutionId;
	public String getIdClient() {
		return idClient;
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient;
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

	
}


