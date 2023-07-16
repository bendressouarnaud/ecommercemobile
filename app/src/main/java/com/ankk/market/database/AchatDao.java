package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.models.Achat;
import com.ankk.market.models.Produit;

import java.util.List;

@Dao
public interface AchatDao {

    @Query("SELECT * FROM Achat")
    List<Achat> getAll();

    @Query("SELECT * FROM Achat where idart=:id")
    List<Achat> getAllByIdart(int id);

    @Query("SELECT * FROM Achat")
    LiveData<List<Achat>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Achat data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Achat... data);

    @Update
    int update(Achat data);

    @Update
    int updateAll(Achat... data);

    @Query("SELECT * FROM Achat where idach=:id")
    Achat getByIdach(int id);

}
