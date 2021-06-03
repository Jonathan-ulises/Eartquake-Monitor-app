package com.macrobios.earthquakemonitor.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.macrobios.earthquakemonitor.Earthquake;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Earthquake.class}, version = 1)
public abstract class EarthquakeDataBase extends RoomDatabase {

    public abstract EqDAO eqDAO();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Patron SINGLETON-------------------------------------------------------------------------//

    //Variable statica, volatile se refiere que puede ser llamada desde cualquier hilo
    private static volatile EarthquakeDataBase INSTANCE;
    //Se optiene la base de datos
    public static EarthquakeDataBase getDatabase(final Context context){
        //Verifica si existe una instancia de la base de datos
        if(INSTANCE == null){
            //Si no ha sido creada, se procedera a crearse
            //Esto se ejecuta en un metodo synchronized, esto hace que las instrucciones dentro
            //del el se ejecuten en un solo hilo
            synchronized (EarthquakeDataBase.class){
                if(INSTANCE == null){
                    //Se crea la instancia de la base de datos.
                    //Esta necesita un contexto de ejecucion, clase de la base de datos y el nombre
                    //de la base de datos.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EarthquakeDataBase.class,
                            "earthquake_db")
                            .build();
                }

            }
        }

        //Retorna la instancia, si ya habia una existente simplemente la retorna; de lo contraio,
        //crea una nueva
        return INSTANCE;
    }

    //Patron SINGLETON-------------------------------------------------------------------------//
}
