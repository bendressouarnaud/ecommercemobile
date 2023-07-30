package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.models.Client;
import com.ankk.market.models.Commune;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.ClientRepository;
import com.ankk.market.repositories.CommuneRepository;

import java.util.List;

public class ClientViewmodel extends ViewModel {

    // A t t r i b u t e s :
    ClientRepository clientRepository;
    CommuneRepository communeRepository;

    // M e t h o d s  :
    public ClientViewmodel(Application app) {
        clientRepository = new ClientRepository(app);
        communeRepository = new CommuneRepository(app);
    }

    // Insert CLIENT
    public void insert(Client data){
        clientRepository.insert(data);
    }

    // Get COMPTE :
    public List<Client> getCompte(){
        return clientRepository.getAll();
    }

    // get ALL COMMUNE
    public List<Commune> getAllCommune(){
        return communeRepository.getAll();
    }

}
