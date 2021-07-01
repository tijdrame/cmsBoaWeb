package com.boa.web.request;

public class DesactivateUserRequest {
    private String digitalId;
    private String pays ="";
    private String param1 ="";
    private String param2 ="";
    private String param3 ="";


    public DesactivateUserRequest() {
    }

    public DesactivateUserRequest(String digitalId, String pays, String param1, String param2, String param3) {
        this.digitalId = digitalId;
        this.pays = pays;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public String getDigitalId() {
        return this.digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getPays() {
        return this.pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getParam1() {
        return this.param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return this.param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return this.param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    public DesactivateUserRequest digitalId(String digitalId) {
        setDigitalId(digitalId);
        return this;
    }

    public DesactivateUserRequest pays(String pays) {
        setPays(pays);
        return this;
    }

    public DesactivateUserRequest param1(String param1) {
        setParam1(param1);
        return this;
    }

    public DesactivateUserRequest param2(String param2) {
        setParam2(param2);
        return this;
    }

    public DesactivateUserRequest param3(String param3) {
        setParam3(param3);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " digitalId='" + getDigitalId() + "'" +
            ", pays='" + getPays() + "'" +
            ", param1='" + getParam1() + "'" +
            ", param2='" + getParam2() + "'" +
            ", param3='" + getParam3() + "'" +
            "}";
    }

}
