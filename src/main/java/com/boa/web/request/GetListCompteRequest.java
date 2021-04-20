package com.boa.web.request;

public class GetListCompteRequest {
    
    private String country;
    private String comptes;

    public GetListCompteRequest() {
    }

    public GetListCompteRequest(String country, String comptes) {
        this.country = country;
        this.comptes = comptes;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComptes() {
        return this.comptes;
    }

    public void setComptes(String comptes) {
        this.comptes = comptes;
    }

    public GetListCompteRequest country(String country) {
        setCountry(country);
        return this;
    }

    public GetListCompteRequest comptes(String comptes) {
        setComptes(comptes);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " country='" + getCountry() + "'" +
            ", comptes='" + getComptes() + "'" +
            "}";
    }

}
