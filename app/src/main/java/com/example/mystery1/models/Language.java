package com.example.mystery1.models;

public class Language {
    private String textLanguage;
    private String tag;

    public Language() {
    }

    public Language(String textLanguage) {
        this.textLanguage = textLanguage;
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
}
