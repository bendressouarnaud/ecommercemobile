package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.beans.BeanCommande;
import com.ankk.market.models.Commande;

import java.util.List;

@Dao
public interface CommandeDao {

    @Query("SELECT * FROM Commande")
    List<Commande> getAll();

    @Query("select dates,heure,count(idcde) as tot,sum(prix) as montant from Commande group by dates,heure")
    List<BeanCommande> getAllGroupByDatesHeure();

    @Query("select * from Commande where dates=:dte and heure=:hr")
    List<Commande> getAllByDatesHeure(String dte, String hr);

    /*@Query("SELECT * FROM Achat where actif=1")
    LiveData<List<Achat>> getAllLiveCommande();*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Commande data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Commande... data);

    @Update
    int update(Commande data);

    @Update
    int updateAll(Commande... data);
}
