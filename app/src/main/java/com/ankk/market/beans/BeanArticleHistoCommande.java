package com.ankk.market.beans;

import java.util.List;

public class BeanArticleHistoCommande {
    int totalarticle, totalprix;
    List<Beanresumearticle> listearticle;

    public BeanArticleHistoCommande() {
    }

    public int getTotalarticle() {
        return totalarticle;
    }

    public void setTotalarticle(int totalarticle) {
        this.totalarticle = totalarticle;
    }

    public int getTotalprix() {
        return totalprix;
    }

    public void setTotalprix(int totalprix) {
        this.totalprix = totalprix;
    }

    public List<Beanresumearticle> getListearticle() {
        return listearticle;
    }

    public void setListearticle(List<Beanresumearticle> listearticle) {
        this.listearticle = listearticle;
    }
}
