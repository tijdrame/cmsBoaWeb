package com.boa.web.request;

public class GetRestrictionRequest {
    private String cartIdentif;
    private String langue;

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

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    @Override
    public String toString() {
        return "{" +
            " cartIdentif='" + getCartIdentif() + "'" +
            " langue='" + getLangue() + "'" +
            "}";
    }

}
