package com.ankk.market.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Beanarticledetail {

    @PrimaryKey
    int idart;

    @ColumnInfo(name = "prix")
    int prix;

    @ColumnInfo(name = "reduction")
    int reduction;

    @ColumnInfo(name = "note")
    int note;

    @ColumnInfo(name = "articlerestant")
    int articlerestant;

    @ColumnInfo(name = "libelle")
    String libelle;

    @ColumnInfo(name = "lienweb")
    String lienweb;

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
