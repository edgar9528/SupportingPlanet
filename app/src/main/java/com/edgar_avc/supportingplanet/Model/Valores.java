package com.edgar_avc.supportingplanet.Model;

public class Valores {
    private String PESANDO;
    private String PESO;

    public Valores() {
    }

    public Valores(String PESANDO, String PESO) {
        this.PESANDO = PESANDO;
        this.PESO = PESO;
    }

    public String getPESANDO() {
        return PESANDO;
    }

    public void setPESANDO(String PESANDO) {
        this.PESANDO = PESANDO;
    }

    public String getPESO() {
        return PESO;
    }

    public void setPESO(String PESO) {
        this.PESO = PESO;
    }
}
