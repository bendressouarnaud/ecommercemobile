package com.ankk.market.beans;

public class BeanCommandeProjection {

    int iduser, nbrearticle, traites, demandeconfirme, demandeorigine, montant, emissions, livres;
    String dates, heure;

    public BeanCommandeProjection() {
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getNbrearticle() {
        return nbrearticle;
    }

    public void setNbrearticle(int nbrearticle) {
        this.nbrearticle = nbrearticle;
    }

    public int getTraites() {
        return traites;
    }

    public void setTraites(int traites) {
        this.traites = traites;
    }

    public int getDemandeconfirme() {
        return demandeconfirme;
    }

    public void setDemandeconfirme(int demandeconfirme) {
        this.demandeconfirme = demandeconfirme;
    }

    public int getDemandeorigine() {
        return demandeorigine;
    }

    public void setDemandeorigine(int demandeorigine) {
        this.demandeorigine = demandeorigine;
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

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getEmissions() {
        return emissions;
    }

    public void setEmissions(int emissions) {
        this.emissions = emissions;
    }

    public int getLivres() {
        return livres;
    }

    public void setLivres(int livres) {
        this.livres = livres;
    }
}
