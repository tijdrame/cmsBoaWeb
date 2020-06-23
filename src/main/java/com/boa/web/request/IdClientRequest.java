package com.boa.web.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="IdClientRequest")
public class IdClientRequest {

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
