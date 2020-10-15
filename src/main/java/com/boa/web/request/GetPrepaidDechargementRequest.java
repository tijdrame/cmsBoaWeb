package com.boa.web.request;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="dechargement")
public class GetPrepaidDechargementRequest {
	
	

	//private String compte ;
	private String institutionId ;
	private String compteTarget ;
	private String cartIdentifSource ;
	private String compteCardSource ;
	private String montant ;
	private String montantFrais ;
	private String dateTrans ;
	private String refRel ;
	private String libelle ;
	private String reftrans;
	private String pays ;
	private String langue ;
	private String variant ;
	public String getReftrans() {
		return reftrans;
	}
	public void setReftrans(String reftrans) {
		this.reftrans = reftrans;
	}
	

  
	public String getLangue() {
		return langue;
	}
	public void setLangue(String langue) {
		this.langue = langue;
	}
	public String getVariant() {
		return variant;
	}
	public void setVariant(String variant) {
		this.variant = variant;
	}
	/*public String getCompte() {
		return compte;
	}
	public void setCompte(String compte) {
		this.compte = compte;
	}*/

	public String getCompteTarget() {
		return this.compteTarget;
	}

	public void setCompteTarget(String compteTarget) {
		this.compteTarget = compteTarget;
	}

	public String getCartIdentifSource() {
		return this.cartIdentifSource;
	}

	public void setCartIdentifSource(String cartIdentifSource) {
		this.cartIdentifSource = cartIdentifSource;
	}

	public String getCompteCardSource() {
		return this.compteCardSource;
	}

	public void setCompteCardSource(String compteCardSource) {
		this.compteCardSource = compteCardSource;
	}
	
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public String getMontant() {
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
	

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	@Override
	public String toString() {
		return "{" +
			" institutionId='" + institutionId + "'" +
			", compteTarget='" + compteTarget + "'" +
			", cartIdentifSource='" + cartIdentifSource + "'" +
			", compteCardSource='" + compteCardSource + "'" +
			", montant='" + montant + "'" +
			", montantFrais='" + montantFrais + "'" +
			", dateTrans='" + dateTrans + "'" +
			", refRel='" + refRel + "'" +
			", libelle='" + libelle + "'" +
			", reftrans='" + reftrans + "'" +
			", pays='" + pays + "'" +
			", langue='" + langue + "'" +
			", variant='" + variant + "'" +
			"}";
	}

}
