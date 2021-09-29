package com.boa.web.response;

public class ChangeRestrictionResponse extends GenericResponse {
    private DataChangeRestriction data = new DataChangeRestriction();

    public ChangeRestrictionResponse() {
    }

    public ChangeRestrictionResponse(DataChangeRestriction data) {
        this.data = data;
    }

    public DataChangeRestriction getData() {
        return this.data;
    }

    public void setData(DataChangeRestriction data) {
        this.data = data;
    }

    public ChangeRestrictionResponse data(DataChangeRestriction data) {
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
