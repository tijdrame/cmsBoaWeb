package com.boa.web.response;

import javax.xml.bind.annotation.XmlAttribute;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GetInfoClientResponse
 */
public class GetInfoClientResponse extends GenericResponse{

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