package com.boa.web.response;

public class Annulation {
    public String rcod, rmsg;

    public Annulation() {
    }

    public Annulation(String rcod, String rmsg) {
        this.rcod = rcod;
        this.rmsg = rmsg;
    }

    public String getRcod() {
        return this.rcod;
    }

    public void setRcod(String rcod) {
        this.rcod = rcod;
    }

    public String getRmsg() {
        return this.rmsg;
    }

    public void setRmsg(String rmsg) {
        this.rmsg = rmsg;
    }

    public Annulation rcod(String rcod) {
        this.rcod = rcod;
        return this;
    }

    public Annulation rmsg(String rmsg) {
        this.rmsg = rmsg;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " rcod='" + getRcod() + "'" +
            ", rmsg='" + getRmsg() + "'" +
            "}";
    }

}