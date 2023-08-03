package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.beans.BeanActif;
import com.ankk.market.models.Achat;
import com.ankk.market.models.Produit;

import java.util.List;

@Dao
public interface AchatDao {

    @Query("SELECT * FROM Achat")
    List<Achat> getAll();

    @Query("SELECT * FROM Achat where idart=:id")
    List<Achat> getAllByIdart(int id);

    @Query("SELECT * FROM Achat where actif=:act")
    List<Achat> getAllByActif(int act);

    @Query("SELECT * FROM Achat where idart=:id and actif=:etat")
    List<Achat> getAllByIdartAndChoix(int id, int etat);

    @Query("SELECT * FROM Achat where idart=:id and actif=:act order by idach desc")
    List<Achat> getAllByIdartAndActif(int id, int act);

    @Query("SELECT * FROM Achat")
    LiveData<List<Achat>> getAllLive();

    @Query("SELECT * FROM Achat where actif=1")
    LiveData<List<Achat>> getAllLiveCommande();

    @Query("SELECT distinct idart, actif FROM Achat where actif=1")
    LiveData<List<BeanActif>> getAllLiveActif();

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

    @Delete
    void delete(Achat data);

    @Delete
    void deleteAll(Achat... data);

}
