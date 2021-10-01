package com.boa.web.request;

public class GetCardLimitsRequest {
    private String cartIdentif;
    private String langue;
    private String typeoper;

    public GetCardLimitsRequest() {
    }

    public GetCardLimitsRequest(String cartIdentif, String langue, String typeoper) {
        this.cartIdentif = cartIdentif;
        this.langue = langue;
        this.typeoper = typeoper;
    }

    public String getCartIdentif() {
        return this.cartIdentif;
    }

    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getTypeoper() {
        return this.typeoper;
    }

    public void setTypeoper(String typeoper) {
        this.typeoper = typeoper;
    }

    public GetCardLimitsRequest cartIdentif(String cartIdentif) {
        setCartIdentif(cartIdentif);
        return this;
    }

    public GetCardLimitsRequest langue(String langue) {
        setLangue(langue);
        return this;
    }

    public GetCardLimitsRequest typeoper(String typeoper) {
        setTypeoper(typeoper);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " cartIdentif='" + getCartIdentif() + "'" +
            ", langue='" + getLangue() + "'" +
            ", typeoper='" + getTypeoper() + "'" +
            "}";
    }

}
