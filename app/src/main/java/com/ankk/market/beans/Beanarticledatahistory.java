package com.ankk.market.beans;


import java.util.List;


public class Beanarticledatahistory {

    List<Imagesupplement> images;
    List<Commentaire> comments;
    String article, entreprise, modaliteretour, descriptionproduit, contact;
    int prix, reduction, nombrearticle;

    public Beanarticledatahistory() {
    }

    public List<Imagesupplement> getImages() {
        return images;
    }

    public void setImages(List<Imagesupplement> images) {
        this.images = images;
    }

    public List<Commentaire> getComments() {
        return comments;
    }

    public void setComments(List<Commentaire> comments) {
        this.comments = comments;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getModaliteretour() {
        return modaliteretour;
    }

    public void setModaliteretour(String modaliteretour) {
        this.modaliteretour = modaliteretour;
    }

    public String getDescriptionproduit() {
        return descriptionproduit;
    }

    public void setDescriptionproduit(String descriptionproduit) {
        this.descriptionproduit = descriptionproduit;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public int getNombrearticle() {
        return nombrearticle;
    }

    public void setNombrearticle(int nombrearticle) {
        this.nombrearticle = nombrearticle;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
