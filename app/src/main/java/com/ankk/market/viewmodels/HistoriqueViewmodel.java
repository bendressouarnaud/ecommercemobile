package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterArticleCommande;
import com.ankk.market.adapters.AdapterListPanier;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ClientRepository;

public class HistoriqueViewmodel extends ViewModel {

    // A t t r i b u t e s :
    ClientRepository clientRepository;
    AdapterArticleCommande adapterArticleCommande;


    // M e t h o d s  :
    public HistoriqueViewmodel(Application app) {
        clientRepository = new ClientRepository(app);
        adapterArticleCommande = new AdapterArticleCommande(app.getApplicationContext());
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AdapterArticleCommande getAdapterArticleCommande() {
        return adapterArticleCommande;
    }
}
