package com.ankk.market.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;

@Database(entities = {Produit.class, Sousproduit.class, Beanarticledetail.class, Achat.class},
        version = 1, exportSchema = true)

public abstract class MarketDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MarketDatabase instance;


    // Method :
    public abstract ProduitDao produitDao();
    public abstract SousproduitDao sousproduitDao();
    public abstract BeanarticledetailDao beanarticledetailDao();
    public abstract AchatDao achatDao();


    // --- INSTANCE ---
    public synchronized static MarketDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MarketDatabase.class, "mrktdatabase.db")
                    //.addMigrations(MIGRATION_7_8)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

}
