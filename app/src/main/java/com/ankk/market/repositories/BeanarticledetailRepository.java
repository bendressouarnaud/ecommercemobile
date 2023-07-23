package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.ankk.market.OpenApplication;
import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.database.BeanarticledetailDao;
import com.ankk.market.database.DbPool;
import com.ankk.market.database.ProduitDao;
import com.ankk.market.models.Produit;

import java.util.List;

public class BeanarticledetailRepository {

    // A t t r i b u t e s :
    BeanarticledetailDao beanarticledetailDao;
    OpenApplication app;


    // M e t h o d s :
    public BeanarticledetailRepository(Application context){
        this.app = (OpenApplication)context;
        beanarticledetailDao = app.getDb().beanarticledetailDao();
    }


    // Update :
    public void update(Beanarticledetail data){
        beanarticledetailDao.update(data);
    }

    public void updateAll(Beanarticledetail[] data){
        beanarticledetailDao.updateAll(data);
    }

    // Get user back :
    public Beanarticledetail getItem(int id){
        return beanarticledetailDao.getByIdart(id);
    }

    // Get user :
    public List<Beanarticledetail> getAll(){
        return beanarticledetailDao.getAll();
    }

    // Get ARTICLE Based on iddet :
    public List<Beanarticledetail> getAllByIddet(int id){
        return beanarticledetailDao.getAllByIddet(id);
    }

    public LiveData<List<Beanarticledetail>> getAllLive(){
        return beanarticledetailDao.getAllLive();
    }

    // Insert
    public void insert(Beanarticledetail... data){
        insert(null, data);
    }

    // appel :
    void insert(Runnable completion, Beanarticledetail... data){
        DbPool.post(()->{
            beanarticledetailDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }

}
