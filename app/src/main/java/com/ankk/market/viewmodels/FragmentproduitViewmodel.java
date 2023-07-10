package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;
import com.ankk.market.repositories.ProduitRepository;
import com.ankk.market.repositories.SousproduitRepository;

import java.util.List;

public class FragmentproduitViewmodel extends ViewModel {

    // A t t t r i b u t e s :
    ProduitRepository produitRepository;
    SousproduitRepository sousproduitRepository;
    List<Produit> produits;


    // M e t h o d s  :
    public FragmentproduitViewmodel(Application app) {
        produitRepository = new ProduitRepository(app);
        sousproduitRepository = new SousproduitRepository(app);
    }

    // Call DATA from database :
    public List<Produit> getAllProduit(){
        return produitRepository.getAll();
    }

    public LiveData<List<Produit>> getAllProduitLive(){
        return produitRepository.getAllLive();
    }

    // Insert
    public void insert(Produit pt){
        produitRepository.insert(pt);
    }

    public List<Sousproduit> getAllByIdprd(int id){
        return sousproduitRepository.getAllByIdprd(id);
    }

    // Update
    public void updateProduit(Produit p){
        produitRepository.update(p);
    }
}