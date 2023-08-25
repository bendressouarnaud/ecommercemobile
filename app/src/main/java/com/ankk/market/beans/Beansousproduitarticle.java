package com.ankk.market.beans;


import java.util.List;

public class Beansousproduitarticle {
    String detail;
    int iddet;
    List<Beanresumearticle> liste;

    public Beansousproduitarticle() {
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<Beanresumearticle> getListe() {
        return liste;
    }

    public void setListe(List<Beanresumearticle> liste) {
        this.liste = liste;
    }

    public int getIddet() {
        return iddet;
    }

    public void setIddet(int iddet) {
        this.iddet = iddet;
    }
}
