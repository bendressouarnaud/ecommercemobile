package com.ankk.market.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ankk.market.repositories.ProduitRepository;

public class InitDataService extends Service {

    // A t t r i b u t e s  :
    private final IBinder binder = new LocalBinder();
    ProduitRepository produitRepository;



    // M e t h o d s  :
    public class LocalBinder extends Binder {
        public InitDataService getService() {
            return InitDataService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //

        //initRepo();
    }


    private void initRepo() {
        produitRepository = new ProduitRepository(getApplication());
    }
}
