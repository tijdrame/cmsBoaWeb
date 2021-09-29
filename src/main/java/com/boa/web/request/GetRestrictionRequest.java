package com.boa.web.request;

public class GetRestrictionRequest {
    private String cartIdentif;

    public GetRestrictionRequest() {
    }

    public GetRestrictionRequest(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    public String getCartIdentif() {
        return this.cartIdentif;
    }

    public void setCartIdentif(String cartIdentif) {
        this.cartIdentif = cartIdentif;
    }

    public GetRestrictionRequest cartIdentif(String cartIdentif) {
        setCartIdentif(cartIdentif);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " cartIdentif='" + getCartIdentif() + "'" +
            "}";
    }

}
