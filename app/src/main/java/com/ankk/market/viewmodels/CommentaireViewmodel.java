package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterArticleCommande;
import com.ankk.market.repositories.ClientRepository;

public class CommentaireViewmodel extends ViewModel {

    // A t t r i b u t e s :
    int idart, note;
    ClientRepository clientRepository;


    // M e t h o d s  :
    public CommentaireViewmodel(Application app) {
        clientRepository = new ClientRepository(app);
    }

    public int getIdart() {
        return idart;
    }

    public void setIdart(int idart) {
        this.idart = idart;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }
}