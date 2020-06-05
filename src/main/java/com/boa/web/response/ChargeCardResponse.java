package com.boa.web.response;

/**
 * ChargeCardResponse
 */
public class ChargeCardResponse extends GenericResponse {

    private String resultat;
    private String reference;
    private String texte;

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

}