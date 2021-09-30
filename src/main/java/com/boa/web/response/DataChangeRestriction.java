package com.boa.web.response;

public class DataChangeRestriction {
    private String statut;
    private Integer respc;

    public DataChangeRestriction() {
    }

    public DataChangeRestriction(String statut, Integer respc) {
        this.statut = statut;
        this.respc = respc;
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

    public DataChangeRestriction statut(String statut) {
        setStatut(statut);
        return this;
    }

    public DataChangeRestriction respc(Integer respc) {
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
