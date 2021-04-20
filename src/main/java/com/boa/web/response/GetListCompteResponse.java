package com.boa.web.response;

public class GetListCompteResponse extends GenericResponse {
    
    private String accounts;
    private String amessage;

    public GetListCompteResponse() {
    }

    public GetListCompteResponse(String accounts, String amessage) {
        this.accounts = accounts;
        this.amessage = amessage;
    }

    public String getAccounts() {
        return this.accounts;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public String getAmessage() {
        return this.amessage;
    }

    public void setAmessage(String amessage) {
        this.amessage = amessage;
    }

    public GetListCompteResponse accounts(String accounts) {
        setAccounts(accounts);
        return this;
    }

    public GetListCompteResponse amessage(String amessage) {
        setAmessage(amessage);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " accounts='" + getAccounts() + "'" +
            ", amessage='" + getAmessage() + "'" +
            "}";
    }

}
