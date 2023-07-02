package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.models.Produit;
import com.ankk.market.repositories.ProduitRepository;

import java.util.List;

public class FragmentproduitViewmodel extends ViewModel {

    // A t t t r i b u t e s :
    ProduitRepository produitRepository;
    List<Produit> produits;


    // M e t h o d s  :
    public FragmentproduitViewmodel(Application app) {
        produitRepository = new ProduitRepository(app);
    }

    // Call DATA from database :
    public List<Produit> getAll(){
        return produitRepository.getAll();
    }

    // Insert
    public void insert(Produit pt){
        produitRepository.insert(pt);
    }
}