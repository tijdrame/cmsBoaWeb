package com.boa.web.response;

public class DataGetRestriction {
    private String cnp;
    private String contactless;
    private String gab;
    private String tpe;
    private String statut;
    private String respc;



    public DataGetRestriction() {
    }

    public DataGetRestriction(String cnp, String contactless, String gab, String tpe, String statut, String respc) {
        this.cnp = cnp;
        this.contactless = contactless;
        this.gab = gab;
        this.tpe = tpe;
        this.statut = statut;
        this.respc = respc;
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

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getRespc() {
        return this.respc;
    }

    public void setRespc(String respc) {
        this.respc = respc;
    }

    public DataGetRestriction cnp(String cnp) {
        setCnp(cnp);
        return this;
    }

    public DataGetRestriction contactless(String contactless) {
        setContactless(contactless);
        return this;
    }

    public DataGetRestriction gab(String gab) {
        setGab(gab);
        return this;
    }

    public DataGetRestriction tpe(String tpe) {
        setTpe(tpe);
        return this;
    }

    public DataGetRestriction statut(String statut) {
        setStatut(statut);
        return this;
    }

    public DataGetRestriction respc(String respc) {
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