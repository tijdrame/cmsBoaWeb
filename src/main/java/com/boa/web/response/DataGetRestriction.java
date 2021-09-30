package com.boa.web.response;

public class DataGetRestriction {
    private Integer cnp;
    private Integer contactless;
    private Integer gab;
    private Integer tpe;
    private String statut;
    private Integer respc;


    public DataGetRestriction() {
    }

    public DataGetRestriction(Integer cnp, Integer contactless, Integer gab, Integer tpe, String statut, Integer respc) {
        this.cnp = cnp;
        this.contactless = contactless;
        this.gab = gab;
        this.tpe = tpe;
        this.statut = statut;
        this.respc = respc;
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

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Integer getRespc() {
        return this.respc;
    }

    public void setRespc(Integer respc) {
        this.respc = respc;
    }

    public DataGetRestriction cnp(Integer cnp) {
        setCnp(cnp);
        return this;
    }

    public DataGetRestriction contactless(Integer contactless) {
        setContactless(contactless);
        return this;
    }

    public DataGetRestriction gab(Integer gab) {
        setGab(gab);
        return this;
    }

    public DataGetRestriction tpe(Integer tpe) {
        setTpe(tpe);
        return this;
    }

    public DataGetRestriction statut(String statut) {
        setStatut(statut);
        return this;
    }

    public DataGetRestriction respc(Integer respc) {
        setRespc(respc);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " cnp='" + getCnp() + "'" +
            ", contactless='" + getContactless() + "'" +
            ", gab='" + getGab() + "'" +
            ", tpe='" + getTpe() + "'" +
            ", statut='" + getStatut() + "'" +
            ", respc='" + getRespc() + "'" +
            "}";
    }


    
}