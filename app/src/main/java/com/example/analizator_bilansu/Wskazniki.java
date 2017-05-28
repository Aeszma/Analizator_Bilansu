package com.example.analizator_bilansu;

public class Wskazniki {
    private int id;
    private String nazwa;
    private float wartoscObecna;
    private float wartoscUbiegla;
    private String rok;

    public Wskazniki() {
    }

    public Wskazniki(String rok, String nazwa, float wartoscObecna, float wartoscUbiegla) {
        this.rok = rok;
        this.nazwa = nazwa;
        this.wartoscObecna = wartoscObecna;
        this.wartoscUbiegla = wartoscUbiegla;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public float getWartoscObecna() {
        return wartoscObecna;
    }

    public void setWartoscObecna(float wartoscObecna) {
        this.wartoscObecna = wartoscObecna;
    }

    public float getWartoscUbiegla() {
        return wartoscUbiegla;
    }

    public void setWartoscUbiegla(float wartoscUbiegla) {
        this.wartoscUbiegla = wartoscUbiegla;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
}