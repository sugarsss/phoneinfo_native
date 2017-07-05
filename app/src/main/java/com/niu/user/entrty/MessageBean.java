package com.niu.user.entrty;

/**
 * Created by niu on 2017/7/1.
 * 本机短信信息
 */

public class MessageBean {

    private String name;
    private String phoneNumber;
    private String smsBody;
    private String date;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsBody='" + smsBody + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
