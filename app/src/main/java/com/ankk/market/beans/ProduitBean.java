package com.ankk.market.beans;

public class ProduitBean {

    String url, lib;

    public ProduitBean() {
    }

    public ProduitBean(String url, String lib) {
        this.url = url;
        this.lib = lib;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLib() {
        return lib;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }
}
