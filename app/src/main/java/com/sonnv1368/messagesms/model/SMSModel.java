package com.sonnv1368.messagesms.model;

/**
 * Created by sonnv on 8/17/2016.
 */
public class SMSModel {
    private boolean isSent;
    private String address;
    private String date;
    private String body;

    public SMSModel() {
    }

    public SMSModel(boolean isSent, String address, String date, String body) {
        this.isSent = isSent;
        this.address = address;
        this.date = date;
        this.body = body;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
