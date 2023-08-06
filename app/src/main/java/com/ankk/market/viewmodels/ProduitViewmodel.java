package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterDisplayProduit;
import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.adapters.AdapterSousproduit;
import com.ankk.market.models.Produit;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ProduitRepository;

public class ProduitViewmodel extends ViewModel {

    // A t t t r i b u t e s :
    AdapterSousproduit adapter;
    AdapterDisplayProduit adapterDisplayProduit;
    ProduitRepository produitRepository;


    // M e t h o d s :
    public ProduitViewmodel(Application app) {
        adapter = new AdapterSousproduit(app.getApplicationContext());
        adapterDisplayProduit = new AdapterDisplayProduit(app.getApplicationContext());
        produitRepository = new ProduitRepository(app);
    }

    public AdapterSousproduit getAdapter() {
        return adapter;
    }

    public AdapterDisplayProduit getAdapterDisplayProduit() {
        return adapterDisplayProduit;
    }

    public ProduitRepository getProduitRepository() {
        return produitRepository;
    }
}
