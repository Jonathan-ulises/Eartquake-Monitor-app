package com.macrobios.earthquakemonitor.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.macrobios.earthquakemonitor.Earthquake;

import java.util.List;

@Dao
public interface EqDAO {

    //INSERCION DE DATOS
    //@Insert
    //Si varias datos tienen la misma ID, se replace el viejo por el nuevo.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> eqList);

    //CONSULTA DE DATOS
    @Query("SELECT * FROM earthquakes")
    List<Earthquake> getEarthquakes();

    @Query("SELECT * FROM earthquakes WHERE magnitud > :myMagnitude")
    List<Earthquake> getEarthquakesWithMagnitudeAbove(double myMagnitude);

    @Delete
    void deleteEarthquake(Earthquake earthquake);

    @Update
    void updateEarthquake(Earthquake earthquake);
}
