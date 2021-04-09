package com.boa.web.response;

public class VerifSeuilResponse extends GenericResponse {
    private String rCode;
    private String rMessage;

    public VerifSeuilResponse() {
    }

    public VerifSeuilResponse(String rCode, String rMessage) {
        this.rCode = rCode;
        this.rMessage = rMessage;
    }

    public String getRCode() {
        return this.rCode;
    }

    public void setRCode(String rCode) {
        this.rCode = rCode;
    }

    public String getRMessage() {
        return this.rMessage;
    }

    public void setRMessage(String rMessage) {
        this.rMessage = rMessage;
    }

    public VerifSeuilResponse rCode(String rCode) {
        setRCode(rCode);
        return this;
    }

    public VerifSeuilResponse rMessage(String rMessage) {
        setRMessage(rMessage);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " rCode='" + getRCode() + "'" +
            ", rMessage='" + getRMessage() + "'" +
            "}";
    }

}
