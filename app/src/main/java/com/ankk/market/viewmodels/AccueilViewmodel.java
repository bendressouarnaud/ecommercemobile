package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ClientRepository;
import com.ankk.market.repositories.CommandeRepository;
import com.ankk.market.repositories.ProduitRepository;

import java.util.List;

public class AccueilViewmodel extends ViewModel {

    // A t t r i b u t e s :
    int totalCommande, alerte;
    AchatRepository achatRepository;
    CommandeRepository commandeRepository;
    ClientRepository clientRepository;
    ProduitRepository produitRepository;


    // M e t h o d s  :
    public AccueilViewmodel(Application app) {
        commandeRepository = new CommandeRepository(app);
        achatRepository = new AchatRepository(app);
        clientRepository = new ClientRepository(app);
        produitRepository = new ProduitRepository(app);
    }

    public List<Achat> getAllByIdart(int idart){
        return achatRepository.getAllByIdart(idart);
    }

    public LiveData<List<Achat>> getAllAchatLive(){
        return achatRepository.getAllLive();
    }

    public LiveData<List<Achat>> getAllLiveCommande(){
        return achatRepository.getAllLiveCommande();
    }

    public int getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(int totalCommande) {
        this.totalCommande = totalCommande;
    }

    public int getAlerte() {
        return alerte;
    }

    public void setAlerte(int alerte) {
        this.alerte = alerte;
    }

    public CommandeRepository getCommandeRepository() {
        return commandeRepository;
    }

    public AchatRepository getAchatRepository() {
        return achatRepository;
    }

    public void setAchatRepository(AchatRepository achatRepository) {
        this.achatRepository = achatRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public ProduitRepository getProduitRepository() {
        return produitRepository;
    }
}
