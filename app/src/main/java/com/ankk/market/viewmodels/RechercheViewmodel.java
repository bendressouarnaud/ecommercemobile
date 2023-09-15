package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.repositories.ProduitRepository;

public class RechercheViewmodel extends ViewModel {

    // A t t r i b u t e s :
    ProduitRepository produitRepository;


    // M e t h o d s :
    public RechercheViewmodel(Application app) {
        produitRepository = new ProduitRepository(app);
    }

    public ProduitRepository getProduitRepository() {
        return produitRepository;
    }
}
