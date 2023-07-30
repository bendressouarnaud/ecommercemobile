package com.ankk.market.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Client {

    @PrimaryKey
    int idcli;

    @ColumnInfo(name = "nom")
    String nom;

    @ColumnInfo(name = "prenom")
    String prenom;

    @ColumnInfo(name = "email")
    String email;

    @ColumnInfo(name = "numero")
    String numero;

    @ColumnInfo(name = "commune")
    int commune;

    @ColumnInfo(name = "adresse")
    String adresse;

    @ColumnInfo(name = "genre")
    int genre;

    @ColumnInfo(name = "fcmtoken")
    String fcmtoken;

    public Client() {
    }

    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCommune() {
        return commune;
    }

    public void setCommune(int commune) {
        this.commune = commune;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }
}
