package com.boa.web.response;

import java.util.Map;

public class ConsultationSoldeResponse extends GenericResponse{
    private Map<String, Object> data;

    public ConsultationSoldeResponse() {
    }


    public ConsultationSoldeResponse(Map<String,Object> data) {
        this.data = data;
    }

    public Map<String,Object> getData() {
        return this.data;
    }

    public void setData(Map<String,Object> data) {
        this.data = data;
    }

    public ConsultationSoldeResponse data(Map<String,Object> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " data='" + getData() + "'" +
            "}";
    }
}
