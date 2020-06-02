package com.edgar_avc.supportingplanet.Model;

public class Usuario {
    private String CORREO;
    private String TIPO;
    private String CONTRASEÑA;
    private String AM;
    private String NOMBRE;
    private String AP;

    public Usuario() {
    }

    public Usuario(String CORREO, String TIPO, String CONTRASEÑA, String AM, String NOMBRE, String AP) {
        this.CORREO = CORREO;
        this.TIPO = TIPO;
        this.CONTRASEÑA = CONTRASEÑA;
        this.AM = AM;
        this.NOMBRE = NOMBRE;
        this.AP = AP;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getCONTRASEÑA() {
        return CONTRASEÑA;
    }

    public void setCONTRASEÑA(String CONTRASEÑA) {
        this.CONTRASEÑA = CONTRASEÑA;
    }

    public String getAM() {
        return AM;
    }

    public void setAM(String AM) {
        this.AM = AM;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getAP() {
        return AP;
    }

    public void setAP(String AP) {
        this.AP = AP;
    }
}
