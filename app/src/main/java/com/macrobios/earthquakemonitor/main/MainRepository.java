package com.macrobios.earthquakemonitor.main;

import androidx.lifecycle.LiveData;

import com.macrobios.earthquakemonitor.Earthquake;
import com.macrobios.earthquakemonitor.api.EarthquakeClient;
import com.macrobios.earthquakemonitor.api.EarthquakeJSONResponse;
import com.macrobios.earthquakemonitor.api.Feature;
import com.macrobios.earthquakemonitor.database.EarthquakeDataBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private EarthquakeDataBase dataBase;

    public MainRepository(EarthquakeDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public LiveData<List<Earthquake>> getEqList(){
        return dataBase.eqDAO().getEarthquakes();
    }

    //Genera los datos de terremotos xxx
    //Descarga los datos del servidor y los guarda en la base de datos local
    public void downloadAndSaveEarthquakes(){
        EarthquakeClient.EartquakeService eqService = EarthquakeClient.getInstance().getService();
        eqService.getEartquakes().enqueue(new Callback<EarthquakeJSONResponse>() {
            @Override
            public void onResponse(Call<EarthquakeJSONResponse> call, Response<EarthquakeJSONResponse> response) {
                List<Earthquake> earthquakeList = getEartquakesWithMoshi(response.body());

                //Guardar datos en la base de datos local-------
                EarthquakeDataBase.databaseWriteExecutor.execute(() -> {
                    dataBase.eqDAO().insertAll(earthquakeList);
                });
                //----------------------------------------------

            }



            @Override
            public void onFailure(Call<EarthquakeJSONResponse> call, Throwable t) {

            }
        });
    }

    //Manipulacion de la respuesta con la libreria Moshi

    /**
     * Convierte la respuesta en una lista de Earthquakes
     * @param body
     * @return
     */
    private List<Earthquake> getEartquakesWithMoshi(EarthquakeJSONResponse body) {
        ArrayList<Earthquake> eqL = new ArrayList<>();

        List<Feature> features = body.getFeatures();
        for (Feature feature: features) {
            String id = feature.getId();
            double magnitud = feature.getProperties().getMag();
            String place = feature.getProperties().getPlace();
            long time = feature.getProperties().getTime();
            double lon = feature.getGeometry().getLongitude();
            double lat = feature.getGeometry().getLatitude();

            Earthquake eq = new Earthquake(id, place, magnitud, time, lat, lon);
            eqL.add(eq);
        }

        return eqL;
    }
}
