package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.ankk.market.OpenApplication;
import com.ankk.market.database.CommuneDao;
import com.ankk.market.database.DbPool;
import com.ankk.market.models.Commune;

import java.util.List;

public class CommuneRepository {

    // A t t r i b u t e s :
    CommuneDao communeDao;
    OpenApplication app;


    // M e t h o d s :
    public CommuneRepository(Application context){
        this.app = (OpenApplication)context;
        communeDao = app.getDb().communeDao();
    }


    // Update :
    public void update(Commune data){
        communeDao.update(data);
    }

    public void updateAll(Commune[] data){
        communeDao.updateAll(data);
    }

    // Get user back :
    public Commune getItem(int id){
        return communeDao.getByIdcom(id);
    }

    // Get user :
    public List<Commune> getAll(){
        return communeDao.getAll();
    }

    public LiveData<List<Commune>> getAllLive(){
        return communeDao.getAllLive();
    }

    // Insert
    public void insert(Commune... data){
        insert(null, data);
    }

    // appel :
    void insert(Runnable completion, Commune... data){
        DbPool.post(()->{
            communeDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }

}
