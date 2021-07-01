package com.boa.web.response;

public class DesactivateUserResponse extends GenericResponse {
    private String data;

    public DesactivateUserResponse() {
    }

    public DesactivateUserResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DesactivateUserResponse data(String data) {
        setData(data);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " data='" + getData() + "'" +
            "}";
    }

}
