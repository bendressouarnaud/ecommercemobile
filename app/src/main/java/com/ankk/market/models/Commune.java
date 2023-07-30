package com.ankk.market.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Commune {

    @PrimaryKey
    int idcom;

    @ColumnInfo(name = "libelle")
    String libelle;

    public Commune() {
    }

    public int getIdcom() {
        return idcom;
    }

    public void setIdcom(int idcom) {
        this.idcom = idcom;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
