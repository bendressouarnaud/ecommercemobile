package com.ankk.market.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Commande {

    @PrimaryKey(autoGenerate = true)
    int idcde;

    @ColumnInfo(name = "idart")
    int idart;

    @ColumnInfo(name = "dates")
    String dates;

    @ColumnInfo(name = "heure")
    String heure;

    @ColumnInfo(name = "prix")
    int prix;

    @ColumnInfo(name = "traite")
    int traite;

    public Commande() {
    }

    public int getIdcde() {
        return idcde;
    }

    public void setIdcde(int idcde) {
        this.idcde = idcde;
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getTraite() {
        return traite;
    }

    public void setTraite(int traite) {
        this.traite = traite;
    }
}
