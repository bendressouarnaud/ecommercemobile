package com.ankk.market.database;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Produit data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Produit... data);

    @Update
    int update(Produit data);

    @Query("SELECT * FROM Produit where idprd =:id")
    Produit getByIdprd(int id);

}
