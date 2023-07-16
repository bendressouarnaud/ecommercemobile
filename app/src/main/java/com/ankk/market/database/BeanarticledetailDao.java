package com.ankk.market.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ankk.market.beans.Beanarticledetail;

import java.util.List;

@Dao
public interface BeanarticledetailDao {

    @Query("SELECT * FROM Beanarticledetail")
    List<Beanarticledetail> getAll();

    @Query("SELECT * FROM Beanarticledetail")
    LiveData<List<Beanarticledetail>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Beanarticledetail data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Beanarticledetail... data);

    @Update
    int update(Beanarticledetail data);

    @Update
    int updateAll(Beanarticledetail... data);

    @Query("SELECT * FROM Beanarticledetail where idart =:id")
    Beanarticledetail getByIdart(int id);

}
