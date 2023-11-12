package com.ankk.market.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ankk.market.beans.Beanarticledetail;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Client;
import com.ankk.market.models.Commande;
import com.ankk.market.models.Commune;
import com.ankk.market.models.Produit;
import com.ankk.market.models.Sousproduit;

@Database(entities = {Produit.class, Sousproduit.class, Beanarticledetail.class, Achat.class, Client.class,
        Commune.class, Commande.class},
        version = 2, exportSchema = true)

public abstract class MarketDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MarketDatabase instance;


    // Method :
    public abstract ProduitDao produitDao();
    public abstract SousproduitDao sousproduitDao();
    public abstract BeanarticledetailDao beanarticledetailDao();
    public abstract AchatDao achatDao();
    public abstract ClientDao clientDao();
    public abstract CommuneDao communeDao();
    public abstract CommandeDao commandeDao();


    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Client "
                    + " ADD COLUMN codeinvitation TEXT");
        }
    };


    // --- INSTANCE ---
    public synchronized static MarketDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MarketDatabase.class, "mrktdatabase.db")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

}
