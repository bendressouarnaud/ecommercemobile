package com.ankk.market.repositories;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.ankk.market.OpenApplication;
import com.ankk.market.database.ClientDao;
import com.ankk.market.database.DbPool;
import com.ankk.market.database.ProduitDao;
import com.ankk.market.models.Client;

import java.util.List;

public class ClientRepository {

    // A t t r i b u t e s :
    ClientDao clientDao;
    OpenApplication app;


    // M e t h o d s :
    public ClientRepository(Application context){
        this.app = (OpenApplication)context;
        clientDao = app.getDb().clientDao();
    }


    // Update :
    public void update(Client data){
        clientDao.update(data);
    }

    public void updateAll(Client[] data){
        clientDao.updateAll(data);
    }

    // Get user back :
    public Client getItem(int id){
        return clientDao.getByIdcli(id);
    }

    // Get user :
    public List<Client> getAll(){
        return clientDao.getAll();
    }

    public LiveData<List<Client>> getAllLive(){
        return clientDao.getAllLive();
    }

    // Insert
    public void insert(Client... data){
        insert(null, data);
    }

    // Delete
    public void deleteAll(Client... data){
        clientDao.deleteAll(data);
    }

    // appel :
    void insert(Runnable completion, Client... data){
        DbPool.post(()->{
            clientDao.insert(data);

            // Perform an action :
            if(completion != null) {
                new Handler(Looper.getMainLooper()).post(completion);
            }
        });
    }

}
