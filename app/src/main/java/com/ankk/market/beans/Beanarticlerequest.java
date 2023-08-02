package com.ankk.market.beans;

import java.util.ArrayList;
import java.util.List;

public class Beanarticlerequest {

    //
    int idcli;
    List<BeanActif> liste;

    public Beanarticlerequest() {
        liste = new ArrayList<>();
    }

    public int getIdcli() {
        return idcli;
    }

    public void setIdcli(int idcli) {
        this.idcli = idcli;
    }

    public List<BeanActif> getListe() {
        return liste;
    }

    public void setListe(List<BeanActif> liste) {
        this.liste = liste;
    }
}
