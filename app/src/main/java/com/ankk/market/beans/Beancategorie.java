package com.ankk.market.beans;

import java.util.List;

public class Beancategorie {

    // Attributes :
    String sousproduit;
    List<Detail> details;

    // Methods
    public Beancategorie() {
    }

    public String getSousproduit() {
        return sousproduit;
    }

    public void setSousproduit(String sousproduit) {
        this.sousproduit = sousproduit;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
}
