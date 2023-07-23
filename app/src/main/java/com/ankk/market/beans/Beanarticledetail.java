package com.ankk.market.beans;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Beanarticledetail {

    @PrimaryKey
    int idart;

    @ColumnInfo(name = "iddet")
    int iddet;

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

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public int getIddet() {
        return iddet;
    }

    public void setIddet(int iddet) {
        this.iddet = iddet;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getArticlerestant() {
        return articlerestant;
    }

    public void setArticlerestant(int articlerestant) {
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
