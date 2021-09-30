package com.boa.web.request;

public class ChangeRestrictionRequest {
    private String cartIdentif;
    private Integer cnp;
    private Integer contactless;
    private Integer gab;
    private Integer tpe;


    public ChangeRestrictionRequest() {
    }

    public ChangeRestrictionRequest(String cartIdentif, Integer cnp, Integer contactless, Integer gab, Integer tpe) {
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

    public Integer getCnp() {
        return this.cnp;
    }

    public void setCnp(Integer cnp) {
        this.cnp = cnp;
    }

    public Integer getContactless() {
        return this.contactless;
    }

    public void setContactless(Integer contactless) {
        this.contactless = contactless;
    }

    public Integer getGab() {
        return this.gab;
    }

    public void setGab(Integer gab) {
        this.gab = gab;
    }

    public Integer getTpe() {
        return this.tpe;
    }

    public void setTpe(Integer tpe) {
        this.tpe = tpe;
    }

    public ChangeRestrictionRequest cartIdentif(String cartIdentif) {
        setCartIdentif(cartIdentif);
        return this;
    }

    public ChangeRestrictionRequest cnp(Integer cnp) {
        setCnp(cnp);
        return this;
    }

    public ChangeRestrictionRequest contactless(Integer contactless) {
        setContactless(contactless);
        return this;
    }

    public ChangeRestrictionRequest gab(Integer gab) {
        setGab(gab);
        return this;
    }

    public ChangeRestrictionRequest tpe(Integer tpe) {
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
