package com.ankk.market;

import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDexApplication;

import com.ankk.market.database.MarketDatabase;

public class OpenApplication extends MultiDexApplication {

    // A t t r i b u t e s :
    MarketDatabase db;


    // M e t h o d s :
    @Override
    public void onCreate() {
        super.onCreate();

        // DB instance :
        db = MarketDatabase.getInstance(this);

        // Start 'Service' Init
        /*Intent intentInit = new Intent(this, InitDataService.class);
        bindService(intentInit, serviceStarterInit, Context.BIND_AUTO_CREATE);*/
    }

    // Return DATABASE instance :
    public MarketDatabase getDb(){
        return db;
    }

}
