package com.ankk.market.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.models.Sousproduit;

import java.util.List;

@Dao
public interface SousproduitDao {

    @Query("SELECT * FROM Sousproduit")
    List<Sousproduit> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Sousproduit data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Sousproduit... data);

    @Update
    int update(Sousproduit data);

    @Query("SELECT * FROM Sousproduit where idprd =:id")
    List<Sousproduit> getAllByIdprd(int id);

    @Query("SELECT * FROM Sousproduit where idspr =:id")
    Sousproduit getByIdspr(int id);

}
