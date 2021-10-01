package com.boa.web.response;

import java.util.ArrayList;
import java.util.List;

public class DataGetCardLimit {
    private Integer respc;
    private String statut;
    private String typeoper;
    private List<Limit> limits = new ArrayList<>();

    public DataGetCardLimit() {
    }

    public DataGetCardLimit(Integer respc, String statut, String typeoper, List<Limit> limits) {
        this.respc = respc;
        this.statut = statut;
        this.typeoper = typeoper;
        this.limits = limits;
    }

    public Integer getRespc() {
        return this.respc;
    }

    public void setRespc(Integer respc) {
        this.respc = respc;
    }

    public String getStatut() {
        return this.statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTypeoper() {
        return this.typeoper;
    }

    public void setTypeoper(String typeoper) {
        this.typeoper = typeoper;
    }

    public List<Limit> getLimits() {
        return this.limits;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }

    public DataGetCardLimit respc(Integer respc) {
        setRespc(respc);
        return this;
    }

    public DataGetCardLimit statut(String statut) {
        setStatut(statut);
        return this;
    }

    public DataGetCardLimit typeoper(String typeoper) {
        setTypeoper(typeoper);
        return this;
    }

    public DataGetCardLimit limits(List<Limit> limits) {
        setLimits(limits);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " respc='" + getRespc() + "'" +
            ", statut='" + getStatut() + "'" +
            ", typeoper='" + getTypeoper() + "'" +
            ", limits='" + getLimits() + "'" +
            "}";
    }


}
