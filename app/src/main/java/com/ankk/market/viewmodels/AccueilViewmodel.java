package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.models.Achat;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;

import java.util.List;

public class AccueilViewmodel extends ViewModel {

    // A t t r i b u t e s :
    int totalCommande, alerte;
    AchatRepository achatRepository;


    // M e t h o d s  :
    public AccueilViewmodel(Application app) {
        achatRepository = new AchatRepository(app);
    }

    public List<Achat> getAllByIdart(int idart){
        return achatRepository.getAllByIdart(idart);
    }

    public LiveData<List<Achat>> getAllAchatLive(){
        return achatRepository.getAllLive();
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
}