package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.models.Produit;

import java.util.List;

@Dao
public interface ProduitDao {

    @Query("SELECT * FROM Produit")
    List<Produit> getAll();

    @Query("SELECT * FROM Produit")
    LiveData<List<Produit>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Produit data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Produit... data);

    @Update
    int update(Produit data);

    @Update
    int updateAll(Produit... data);

    @Query("SELECT * FROM Produit where idprd =:id")
    Produit getByIdprd(int id);

}
