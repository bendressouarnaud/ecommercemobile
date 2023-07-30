package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.models.Client;

import java.util.List;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM Client")
    List<Client> getAll();

    @Query("SELECT * FROM Client")
    LiveData<List<Client>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Client data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Client... data);

    @Update
    int update(Client data);

    @Update
    int updateAll(Client... data);

    @Query("SELECT * FROM Client where idcli =:id")
    Client getByIdcli(int id);

}
