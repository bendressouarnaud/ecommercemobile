package com.ankk.market.beans;

public class Beanarticledetail {

    Integer idart, prix, reduction, note, articlerestant;
    String libelle, lienweb;

    public Beanarticledetail() {
    }

    public Integer getIdart() {
        return idart;
    }

    public void setIdart(Integer idart) {
        this.idart = idart;
    }

    public Integer getPrix() {
        return prix;
    }

    public void setPrix(Integer prix) {
        this.prix = prix;
    }

    public Integer getReduction() {
        return reduction;
    }

    public void setReduction(Integer reduction) {
        this.reduction = reduction;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Integer getArticlerestant() {
        return articlerestant;
    }

    public void setArticlerestant(Integer articlerestant) {
        this.articlerestant = articlerestant;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLienweb() {
        return lienweb;
    }

    public void setLienweb(String lienweb) {
        this.lienweb = lienweb;
    }
}
