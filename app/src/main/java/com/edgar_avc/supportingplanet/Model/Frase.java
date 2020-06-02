package com.edgar_avc.supportingplanet.Model;

public class Frase {
    private String key;
    private String value;

    public Frase() {
    }

    public Frase(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
