package com.boa.web.response;

public class CardlessResponse extends GenericResponse {
    
    private Reply reply;

    public CardlessResponse() {
    }

    public CardlessResponse(Reply reply) {
        this.reply = reply;
    }

    public Reply getReply() {
        return this.reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public CardlessResponse reply(Reply reply) {
        this.reply = reply;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " reply='" + getReply() + "'" +
            "}";
    }

}