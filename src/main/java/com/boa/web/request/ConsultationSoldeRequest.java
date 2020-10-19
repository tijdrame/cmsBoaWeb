package com.boa.web.request;

public class ConsultationSoldeRequest {
    private String compte;
    private String cartIdentif;
    private String pays;
    private String langue;


    public ConsultationSoldeRequest() {
    }

    public ConsultationSoldeRequest(String compte, String cartIdentif, String pays, String langue) {
        this.compte = compte;
        this.cartIdentif = cartIdentif;
        this.pays = pays;
        this.langue = langue;
    }

    public String getCompte() {
        return this.compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getCartIdentif() {
        return this.cartIdentif;
    }

    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public ConsultationSoldeRequest compte(String compte) {
        this.compte = compte;
        return this;
    }

    public ConsultationSoldeRequest cartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
        return this;
    }

    public ConsultationSoldeRequest pays(String pays) {
        this.pays = pays;
        return this;
    }

    public ConsultationSoldeRequest langue(String langue) {
        this.langue = langue;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " compte='" + getCompte() + "'" +
            ", cartIdentif='" + getCartIdentif() + "'" +
            ", pays='" + getPays() + "'" +
            ", langue='" + getLangue() + "'" +
            "}";
    }

}
