package com.ankk.market.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Achat {

    @PrimaryKey(autoGenerate = true)
    int idach;

    @ColumnInfo(name = "idart")
    int idart;

    @ColumnInfo(name = "actif")
    int actif;

    public Achat() {
    }

    public int getIdach() {
        return idach;
    }

    public void setIdach(int idach) {
        this.idach = idach;
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public int getActif() {
        return actif;
    }

    public void setActif(int actif) {
        this.actif = actif;
    }
}
