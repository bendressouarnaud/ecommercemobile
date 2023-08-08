package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterDetailHorizontal;
import com.ankk.market.adapters.AdapterDisplayProduit;
import com.ankk.market.adapters.AdapterSousproduit;
import com.ankk.market.repositories.ProduitRepository;

public class DetailActivityViewModel extends ViewModel {

    // A t t r i b u t s
    AdapterDetailHorizontal adapter;



    // Methods :
    public DetailActivityViewModel(Application app) {
        adapter = new AdapterDetailHorizontal(app.getApplicationContext());
    }


    public AdapterDetailHorizontal getAdapter() {
        return adapter;
    }
}
