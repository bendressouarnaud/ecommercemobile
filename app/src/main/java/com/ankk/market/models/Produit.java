package com.ankk.market.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Produit {

    @PrimaryKey
    int idprd;

    @ColumnInfo(name = "libelle")
    String libelle;

    @ColumnInfo(name = "lienweb")
    String lienweb;

    @ColumnInfo(name = "choix")
    int choix;

    public Produit() {
    }

    public int getIdprd() {
        return idprd;
    }

    public void setIdprd(int idprd) {
        this.idprd = idprd;
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

    public int getChoix() {
        return choix;
    }

    public void setChoix(int choix) {
        this.choix = choix;
    }
}
