package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.ankk.market.OpenApplication;
import com.ankk.market.beans.BeanCommande;
import com.ankk.market.database.CommandeDao;
import com.ankk.market.database.CommuneDao;
import com.ankk.market.database.DbPool;
import com.ankk.market.models.Commande;
import com.ankk.market.models.Commune;

import java.util.List;

public class CommandeRepository {

    // A t t r i b u t e s :
    CommandeDao commandeDao;
    OpenApplication app;


    // M e t h o d s :
    public CommandeRepository(Application context){
        this.app = (OpenApplication)context;
        commandeDao = app.getDb().commandeDao();
    }


    // Update :
    public void update(Commande data){
        commandeDao.update(data);
    }

    public void updateAll(Commande[] data){
        commandeDao.updateAll(data);
    }

    // Get  :
    public List<Commande> getAll(){
        return commandeDao.getAll();
    }

    // BeanCommande
    public List<BeanCommande> getAllGroupByDatesHeure(){
        return commandeDao.getAllGroupByDatesHeure();
    }

    public List<Commande> getAllByDatesHeure(String dte, String hr){
        return commandeDao.getAllByDatesHeure(dte, hr);
    }

    // Insert
    public void insert(Commande... data){
        insert(null, data);
    }

    // appel :
    void insert(Runnable completion, Commande... data){
        DbPool.post(()->{
            commandeDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }

}
