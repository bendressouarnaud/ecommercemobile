package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.ankk.market.OpenApplication;
import com.ankk.market.beans.BeanActif;
import com.ankk.market.database.AchatDao;
import com.ankk.market.database.DbPool;
import com.ankk.market.database.ProduitDao;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Produit;

import java.util.List;

public class AchatRepository {

    // A t t r i b u t e s :
    AchatDao achatDao;
    OpenApplication app;


    // M e t h o d s :
    public AchatRepository(Application context){
        this.app = (OpenApplication)context;
        achatDao = app.getDb().achatDao();
    }


    // Update :
    public void update(Achat data){
        achatDao.update(data);
    }

    public void updateAll(Achat[] data){
        achatDao.updateAll(data);
    }

    // Get user back :
    public Achat getItem(int id){
        return achatDao.getByIdach(id);
    }

    // Get user :
    public List<Achat> getAll(){
        return achatDao.getAll();
    }
    public List<Achat> getAllByIdart(int idart){
        return achatDao.getAllByIdart(idart);
    }

    public List<Achat> getAllByIdartAndChoix(int idart, int etat){
        return achatDao.getAllByIdartAndChoix(idart, etat);
    }

    //
    public List<Achat> getAllByActif(int actif){
        return achatDao.getAllByActif(actif);
    }

    public List<Achat> getAllByIdartAndActif(int idart, int actif){
        return achatDao.getAllByIdartAndActif(idart, actif);
    }

    public LiveData<List<Achat>> getAllLive(){
        return achatDao.getAllLive();
    }

    public LiveData<List<Achat>> getAllLiveCommande(){
        return achatDao.getAllLiveCommande();
    }


    public LiveData<List<BeanActif>> getAllLiveActif(){
        return achatDao.getAllLiveActif();
    }

    // Insert
    public void insert(Achat... data){
        insert(null, data);
    }

    // Deletion :
    public void delete(Achat achat){
        achatDao.delete(achat);
    }

    public void deleteAll(Achat... achat){
        achatDao.deleteAll(achat);
    }

    // appel :
    void insert(Runnable completion, Achat... data){
        DbPool.post(()->{
            achatDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }

}
