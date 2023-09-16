package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.ankk.market.OpenApplication;
import com.ankk.market.database.DbPool;
import com.ankk.market.database.ProduitDao;
import com.ankk.market.models.Produit;

import java.util.List;

public class ProduitRepository {

    // A t t r i b u t e s :
    ProduitDao produitDao;
    OpenApplication app;


    // M e t h o d s :
    public ProduitRepository(Application context){
        this.app = (OpenApplication)context;
        produitDao = app.getDb().produitDao();
    }


    // Update :
    public void update(Produit data){
        produitDao.update(data);
    }

    public void updateAll(Produit[] data){
        produitDao.updateAll(data);
    }

    // Get user back :
    public Produit getItem(int id){
        return produitDao.getByIdprd(id);
    }

    public Produit getItemLib(String lib){
        return produitDao.getByLibelle(lib);
    }

    // Get user :
    public List<Produit> getAll(){
        return produitDao.getAll();
    }

    public LiveData<List<Produit>> getAllLive(){
        return produitDao.getAllLive();
    }

    // Insert
    public void insert(Produit... data){
        insert(null, data);
    }

    // appel :
    void insert(Runnable completion, Produit... data){
        DbPool.post(()->{
            produitDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }
}
