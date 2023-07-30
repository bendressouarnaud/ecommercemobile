package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.models.Commune;

import java.util.List;

@Dao
public interface CommuneDao {

    @Query("SELECT * FROM Commune")
    List<Commune> getAll();

    @Query("SELECT * FROM Commune")
    LiveData<List<Commune>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Commune data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Commune... data);

    @Update
    int update(Commune data);

    @Update
    int updateAll(Commune... data);

    @Query("SELECT * FROM Commune where idcom =:id")
    Commune getByIdcom(int id);
}
