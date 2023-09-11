package com.ankk.market.beans;

import com.ankk.market.models.Client;
import com.ankk.market.models.Commune;

import java.util.List;

public class BeanCustomerAuth {
    int flag;
    Client clt;
    List<Commune> commune;

    public BeanCustomerAuth() {
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Client getClt() {
        return clt;
    }

    public void setClt(Client clt) {
        this.clt = clt;
    }

    public List<Commune> getCommune() {
        return commune;
    }

    public void setCommune(List<Commune> commune) {
        this.commune = commune;
    }
}
