package com.ankk.market.beans;


import com.ankk.market.models.Client;

public class BeanCustomerCreation {
    int flag;
    Client clt;

    public BeanCustomerCreation() {
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
}
