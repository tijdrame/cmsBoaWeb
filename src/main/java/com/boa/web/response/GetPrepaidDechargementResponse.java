package com.boa.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
"Dechargement"
})

public class GetPrepaidDechargementResponse extends GenericResponse{

	/*@JsonProperty("compte")
	private String compte;
	@JsonProperty("compteSource")
	private String compteSource;
	@JsonProperty("cartIdentifTarget")
	private String cartIdentifTarget;
	@JsonProperty("compteCardTarget")
	private String compteCardTarget;
	@JsonProperty("institutionId")
	private String institutionId;*/
	
	
	private String reference;
	private String resultat;
	private String texte;

			
			
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getResultat() {
		return resultat;
	}
	public void setResultat(String resultat) {
		this.resultat = resultat;
	}
	public String getTexte() {
		return texte;
	}
	public void setTexte(String texte) {
		this.texte = texte;
	}
	/*public String getCompte() {
		return compte;
	}
	public void setCompte(String compte) {
		this.compte = compte;
	}
	public String getCompteSource() {
		return compteSource;
	}
	public void setCompteSource(String compteSource) {
		this.compteSource = compteSource;
	}
	public String getCartIdentifTarget() {
		return cartIdentifTarget;
	}
	public void setCartIdentifTarget(String cartIdentifTarget) {
		this.cartIdentifTarget = cartIdentifTarget;
	}
	public String getCompteCardTarget() {
		return compteCardTarget;
	}
	public void setCompteCardTarget(String compteCardTarget) {
		this.compteCardTarget = compteCardTarget;
	}
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}*/
/*	public String getMontant() {
		return montant;
	}
	public void setMontant(String montant) {
		this.montant = montant;
	}
	public String getMontantFrais() {
		return montantFrais;
	}
	public void setMontantFrais(String montantFrais) {
		this.montantFrais = montantFrais;
	}
	public String getDateTrans() {
		return dateTrans;
	}
	public void setDateTrans(String dateTrans) {
		this.dateTrans = dateTrans;
	}
	public String getRefRel() {
		return refRel;
	}
	public void setRefRel(String refRel) {
		this.refRel = refRel;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}*/

			
			
	/*@JsonProperty("montant")
	private String montant;
	@JsonProperty("montantFrais")
	private String montantFrais;
	@JsonProperty("dateTrans")
	private String dateTrans;
	@JsonProperty("refRel")
	private String refRel;
	@JsonProperty("libelle")
	private String libelle;
	@JsonProperty("country")
	private String country;
	
	*/
	
}
