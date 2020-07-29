package com.boa.web.request;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * GetInfoClientRequest
 */
public class GetInfoClientRequest {

    @XmlAttribute(name = "compte")
	private String compte ;
	@XmlAttribute(name = "institutionId")
	private String institutionId ;
	
	
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
			"}";
	}
}