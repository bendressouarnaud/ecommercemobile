package com.ankk.market.beans;

public class BeanItemPanier {
    Beanarticlelive article;
    int totalcomment, restant;
    double note;

    public BeanItemPanier() {
    }

    public Beanarticlelive getArticle() {
        return article;
    }

    public void setArticle(Beanarticlelive article) {
        this.article = article;
    }

    public int getTotalcomment() {
        return totalcomment;
    }

    public void setTotalcomment(int totalcomment) {
        this.totalcomment = totalcomment;
    }

    public int getRestant() {
        return restant;
    }

    public void setRestant(int restant) {
        this.restant = restant;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }
}
