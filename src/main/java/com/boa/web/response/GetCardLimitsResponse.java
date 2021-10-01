package com.boa.web.response;

public class GetCardLimitsResponse extends GenericResponse{
    private DataGetCardLimit data = new DataGetCardLimit() ;

    public GetCardLimitsResponse() {
    }

    public GetCardLimitsResponse(DataGetCardLimit data) {
        this.data = data;
    }

    public DataGetCardLimit getData() {
        return this.data;
    }

    public void setData(DataGetCardLimit data) {
        this.data = data;
    }

    public GetCardLimitsResponse data(DataGetCardLimit data) {
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
