package com.boa.web.response;

public class DesactivateUserResponse extends GenericResponse {
    private Integer data;

    public DesactivateUserResponse() {
    }

    public DesactivateUserResponse(Integer data) {
        this.data = data;
    }

    public Integer getData() {
        return this.data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public DesactivateUserResponse data(Integer data) {
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
