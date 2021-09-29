package com.boa.web.request;

public class ChangeRestrictionRequest {
    private String cartIdentif;
    private String cnp;
    private String contactless;
    private String gab;
    private String tpe;

    public ChangeRestrictionRequest() {
    }

    public ChangeRestrictionRequest(String cartIdentif, String cnp, String contactless, String gab, String tpe) {
        this.cartIdentif = cartIdentif;
        this.cnp = cnp;
        this.contactless = contactless;
        this.gab = gab;
        this.tpe = tpe;
    }

    public String getCartIdentif() {
        return this.cartIdentif;
    }

    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    public String getCnp() {
        return this.cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getContactless() {
        return this.contactless;
    }

    public void setContactless(String contactless) {
        this.contactless = contactless;
    }

    public String getGab() {
        return this.gab;
    }

    public void setGab(String gab) {
        this.gab = gab;
    }

    public String getTpe() {
        return this.tpe;
    }

    public void setTpe(String tpe) {
        this.tpe = tpe;
    }

    public ChangeRestrictionRequest cartIdentif(String cartIdentif) {
        setCartIdentif(cartIdentif);
        return this;
    }

    public ChangeRestrictionRequest cnp(String cnp) {
        setCnp(cnp);
        return this;
    }

    public ChangeRestrictionRequest contactless(String contactless) {
        setContactless(contactless);
        return this;
    }

    public ChangeRestrictionRequest gab(String gab) {
        setGab(gab);
        return this;
    }

    public ChangeRestrictionRequest tpe(String tpe) {
        setTpe(tpe);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " cartIdentif='" + getCartIdentif() + "'" +
            ", cnp='" + getCnp() + "'" +
            ", contactless='" + getContactless() + "'" +
            ", gab='" + getGab() + "'" +
            ", tpe='" + getTpe() + "'" +
            "}";
    }

}
