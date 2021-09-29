package com.boa.web.response;

public class GetRestrictionResponse extends GenericResponse{
    private DataGetRestriction data = new DataGetRestriction() ;

    public GetRestrictionResponse() {
    }

    public GetRestrictionResponse(DataGetRestriction data) {
        this.data = data;
    }

    public DataGetRestriction getData() {
        return this.data;
    }

    public void setData(DataGetRestriction data) {
        this.data = data;
    }

    public GetRestrictionResponse data(DataGetRestriction data) {
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
