package com.boa.web.request;

/**
 * ChangementCardRequest
 */
public class ChargementCardRequest {

    private String cartIdentifTarget;
    private String institutionId;
    private String montant;
    private String montantFrais;
    private String compteSource;
    private String compteCardTarget;
    private String dateTrans;
    private String refRel;
    private String libelle;
    private String langue;
    private String pays;
    private String variant;

    public String getCartIdentifTarget() {
        return this.cartIdentifTarget;
    }

    public void setCartIdentifTarget(String cartIdentifTarget) {
        this.cartIdentifTarget = cartIdentifTarget;
    }

    public String getInstitutionId() {
        return this.institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public String getMontant() {
        return this.montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getMontantFrais() {
        return this.montantFrais;
    }

    public void setMontantFrais(String montantFrais) {
        this.montantFrais = montantFrais;
    }

    public String getCompteSource() {
        return this.compteSource;
    }

    public void setCompteSource(String compteSource) {
        this.compteSource = compteSource;
    }

    public String getCompteCardTarget() {
        return this.compteCardTarget;
    }

    public void setCompteCardTarget(String compteCardTarget) {
        this.compteCardTarget = compteCardTarget;
    }

    public String getDateTrans() {
        return this.dateTrans;
    }

    public void setDateTrans(String dateTrans) {
        this.dateTrans = dateTrans;
    }

    public String getRefRel() {
        return this.refRel;
    }

    public void setRefRel(String refRel) {
        this.refRel = refRel;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVariant() {
        return this.variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    @Override
    public String toString() {
        return "{" +
            ", cartIdentifTarget='" + getCartIdentifTarget() + "'" +
            ", institutionId='" + getInstitutionId() + "'" +
            ", montant='" + getMontant() + "'" +
            ", montantFrais='" + getMontantFrais() + "'" +
            ", compteSource='" + getCompteSource() + "'" +
            ", dateTrans='" + getDateTrans() + "'" +
            ", refRel='" + getRefRel() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", country='" + getPays() + "'" +
            ", langue='" + getLangue() + "'" +
            ", pays='" + getPays() + "'" +
            ", variant='" + getVariant() + "'" +
            "}";
    }

}