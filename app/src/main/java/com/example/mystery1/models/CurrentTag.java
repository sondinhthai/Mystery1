package com.example.mystery1.models;

public class CurrentTag {
    private String tag;
    private String textLanguage;
    private String deviceID;

    public CurrentTag() {
    }

    public CurrentTag(String tag, String textLanguage, String deviceID) {
        this.tag = tag;
        this.textLanguage = textLanguage;
        this.deviceID = deviceID;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTextLanguage() {
        return textLanguage;
    }

    public void setTextLanguage(String textLanguage) {
        this.textLanguage = textLanguage;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }
}
