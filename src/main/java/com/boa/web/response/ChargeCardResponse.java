package com.boa.web.response;

/**
 * ChargeCardResponse
 */
public class ChargeCardResponse extends GenericResponse {

    private String resultat;
    private String reference;
    private String texte;
    private Annulation annulation;

    public String getResultat() {
        return this.resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTexte() {
        return this.texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public Annulation getAnnulation() {
        return this.annulation;
    }

    public void setAnnulation(Annulation annulation) {
        this.annulation = annulation;
    }

    @Override
    public String toString() {
        return "{" +
            //" resultat='" + resultat + "'" +
            ", reference='" + reference + "'" +
            ", texte='" + texte + "'" +
            ", annulation='" + annulation + "'" +
            "}";
    }

}