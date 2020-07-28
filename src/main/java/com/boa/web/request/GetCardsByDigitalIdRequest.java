package com.boa.web.request;

public class GetCardsByDigitalIdRequest {
    private String digitalId, langue;

    public GetCardsByDigitalIdRequest() {
    }

    public GetCardsByDigitalIdRequest(String digitalId, String langue) {
        this.digitalId = digitalId;
        this.langue = langue;
    }

    public String getDigitalId() {
        return this.digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getLangue() {
        return this.langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public GetCardsByDigitalIdRequest digitalId(String digitalId) {
        this.digitalId = digitalId;
        return this;
    }

    public GetCardsByDigitalIdRequest langue(String langue) {
        this.langue = langue;
        return this;
    }

    

    @Override
    public String toString() {
        return "{" +
            " digitalId='" + getDigitalId() + "'" +
            ", langue='" + getLangue() + "'" +
            "}";
    }

}