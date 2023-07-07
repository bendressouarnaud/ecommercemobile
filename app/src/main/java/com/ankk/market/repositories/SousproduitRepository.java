package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.ankk.market.OpenApplication;
import com.ankk.market.database.DbPool;
import com.ankk.market.database.ProduitDao;
import com.ankk.market.database.SousproduitDao;
import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;

import java.util.List;

public class SousproduitRepository {

    // A t t r i b u t e s :
    SousproduitDao sousproduitDao;
    OpenApplication app;


    // M e t h o d s :
    public SousproduitRepository(Application context){
        this.app = (OpenApplication)context;
        sousproduitDao = app.getDb().sousproduitDao();
    }


    // Update :
    public void update(Sousproduit data){
        sousproduitDao.update(data);
    }

    // Get item :
    public Sousproduit getItem(int id){
        return sousproduitDao.getByIdspr(id);
    }

    // Get items by idprd :
    public List<Sousproduit> getAllByIdprd(int id){
        return sousproduitDao.getAllByIdprd(id);
    }

    // Get ALL :
    public List<Sousproduit> getAll(){
        return sousproduitDao.getAll();
    }

    // Insert
    public void insert(Sousproduit... data){
        insert(null, data);
    }

    // appel :
    void insert(Runnable completion, Sousproduit... data){
        DbPool.post(()->{
            sousproduitDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }

}
