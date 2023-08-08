package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterDetailHorizontal;
import com.ankk.market.adapters.AdapterDisplayDetail;
import com.ankk.market.adapters.AdapterDisplayProduit;
import com.ankk.market.adapters.AdapterSousproduit;
import com.ankk.market.repositories.ProduitRepository;

public class DetailActivityViewModel extends ViewModel {

    // A t t r i b u t s
    AdapterDetailHorizontal adapter;
    AdapterDisplayDetail adapterDisplayDetail;



    // Methods :
    public DetailActivityViewModel(Application app) {
        adapter = new AdapterDetailHorizontal(app.getApplicationContext());
        adapterDisplayDetail = new AdapterDisplayDetail(app.getApplicationContext());
    }


    public AdapterDetailHorizontal getAdapter() {
        return adapter;
    }

    public AdapterDisplayDetail getAdapterDisplayDetail() {
        return adapterDisplayDetail;
    }
}
