package com.ankk.market.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ankk.market.adapters.AdapterListArticle;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.beans.Beanarticlelive;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;
import com.ankk.market.repositories.AchatRepository;
import com.ankk.market.repositories.BeanarticledetailRepository;
import com.ankk.market.repositories.ProduitRepository;
import com.ankk.market.repositories.SousproduitRepository;

import java.util.ArrayList;
import java.util.List;

public class DetailViewmodel extends ViewModel {

    // A t t t r i b u t e s :
    BeanarticledetailRepository beanarticledetailRepository;
    AchatRepository achatRepository;
    AdapterListArticle adapter;
    List<Produit> produits;
    List<Beanarticlelive> beanarticlelives;
    boolean valideCommande = false, refreshAdapter = false;
    int iddet = 0, mode =0 ;
    String libSousProduit ="";


    // M e t h o d s  :
    public DetailViewmodel(Application app) {
        beanarticledetailRepository = new BeanarticledetailRepository(app);
        achatRepository = new AchatRepository(app);
        adapter = new AdapterListArticle(app.getApplicationContext());
        beanarticlelives = new ArrayList<>();
    }

    //
    public List<Beanarticledetail> getAllArticle(){
        return beanarticledetailRepository.getAll();
    }

    public List<Beanarticledetail> getAllArticleByIddet(int id){
        return beanarticledetailRepository.getAllByIddet(id);
    }

    // Get All ACHAT :
    public List<Achat> getAllByIdart(int idart){
        return achatRepository.getAllByIdart(idart);
    }

    public LiveData<List<Achat>> getAllAchatLive(){
        return achatRepository.getAllLive();
    }

    //
    public LiveData<List<Achat>> getAllLiveCommande(){
        return achatRepository.getAllLiveCommande();
    }

    public LiveData<List<Beanarticledetail>> getAllArticleLive(){
        return beanarticledetailRepository.getAllLive();
    }

    // Insert
    public void insertArticle(Beanarticledetail bl){
        beanarticledetailRepository.insert(bl);
    }

    /*public List<Sousproduit> getAllByIdprd(int id){
        return sousproduitRepository.getAllByIdprd(id);
    }

    // Update
    public void updateProduit(Produit p){
        produitRepository.update(p);
    }*/


    public AdapterListArticle getAdapter() {
        return adapter;
    }

    public void setAdapter(AdapterListArticle adapter) {
        this.adapter = adapter;
    }

    public AchatRepository getAchatRepository() {
        return achatRepository;
    }

    public boolean isValideCommande() {
        return valideCommande;
    }

    public void setValideCommande(boolean valideCommande) {
        this.valideCommande = valideCommande;
    }

    public int getIddet() {
        return iddet;
    }

    public void setIddet(int iddet) {
        this.iddet = iddet;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getLibSousProduit() {
        return libSousProduit;
    }

    public void setLibSousProduit(String libSousProduit) {
        this.libSousProduit = libSousProduit;
    }

    public boolean isRefreshAdapter() {
        return refreshAdapter;
    }

    public void setRefreshAdapter(boolean refreshAdapter) {
        this.refreshAdapter = refreshAdapter;
    }

    public List<Beanarticlelive> getBeanarticlelives() {
        return beanarticlelives;
    }

    public void setBeanarticlelives(List<Beanarticlelive> beanarticlelives) {
        this.beanarticlelives = beanarticlelives;
    }

    public BeanarticledetailRepository getBeanarticledetailRepository() {
        return beanarticledetailRepository;
    }
}