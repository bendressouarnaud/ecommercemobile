package com.ankk.market.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Sousproduit {

    @PrimaryKey
    Integer idspr;

    @ColumnInfo(name = "libelle")
    String libelle;

    @ColumnInfo(name = "lienweb")
    String lienweb;

    @ColumnInfo(name = "idprd")
    Integer idprd;

    public Sousproduit() {
    }

    public Integer getIdspr() {
        return idspr;
    }

    public void setIdspr(Integer idspr) {
        this.idspr = idspr;
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

    public Integer getIdprd() {
        return idprd;
    }

    public void setIdprd(Integer idprd) {
        this.idprd = idprd;
    }
}
