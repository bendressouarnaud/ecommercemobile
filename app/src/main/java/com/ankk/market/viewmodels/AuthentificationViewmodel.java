package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.models.Client;
import com.ankk.market.models.Commune;
import com.ankk.market.repositories.ClientRepository;
import com.ankk.market.repositories.CommuneRepository;

import java.util.List;

public class AuthentificationViewmodel extends ViewModel {

    // A t t r i b u t e s :
    ClientRepository clientRepository;
    CommuneRepository communeRepository;


    // M e t h o d s  :
    public AuthentificationViewmodel(Application app) {
        clientRepository = new ClientRepository(app);
        communeRepository = new CommuneRepository(app);
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public CommuneRepository getCommuneRepository() {
        return communeRepository;
    }
}
