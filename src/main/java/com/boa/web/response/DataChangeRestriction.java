package com.boa.web.response;

public class DataChangeRestriction {
    private String statut;
    private String respc;

    public DataChangeRestriction() {
    }

    public DataChangeRestriction(String statut, String respc) {
        this.statut = statut;
        this.respc = respc;
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

    public DataChangeRestriction statut(String statut) {
        setStatut(statut);
        return this;
    }

    public DataChangeRestriction respc(String respc) {
        setRespc(respc);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " statut='" + getStatut() + "'" +
            ", respc='" + getRespc() + "'" +
            "}";
    }

}
