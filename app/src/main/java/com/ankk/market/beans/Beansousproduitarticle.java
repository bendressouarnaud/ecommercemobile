package com.ankk.market.beans;


import java.util.List;

public class Beansousproduitarticle {
    String detail;
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
}
