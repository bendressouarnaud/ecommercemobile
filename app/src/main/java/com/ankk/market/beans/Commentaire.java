package com.ankk.market.beans;

import java.util.Date;

public class Commentaire {
    private Long idcmt;
    private String commentaire, dates, appreciation;
    private int idcli, idart, note;

    public Commentaire() {
    }

    public Long getIdcmt() {
        return idcmt;
    }

    public void setIdcmt(Long idcmt) {
        this.idcmt = idcmt;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
