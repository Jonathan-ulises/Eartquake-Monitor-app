package com.macrobios.earthquakemonitor;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macrobios.earthquakemonitor.api.EarthquakeClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    //Genera los datos de terremotos
    public void getEartquakes(){
        EarthquakeClient.EartquakeService eqService = EarthquakeClient.getInstance().getService();
        eqService.getEartquakes().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                List<Earthquake> earthquakeList = parseEarhquakes(response.body());
                eqList.setValue(earthquakeList);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    /**
     * Convierte el resultado JSON(String) del servicio en datos que la clase pueda utilizar.
     * @param responseString Respuesta del servicio
     * @return Lista de terremotos
     */
    private List<Earthquake> parseEarhquakes(String responseString){
        ArrayList<Earthquake> eqL = new ArrayList<>();
        try {
            //Respuesta completa
            JSONObject jsonResponse = new JSONObject(responseString);

            //Objeto Features
            JSONArray featuresJsonArray = jsonResponse.getJSONArray("features");

            for(int i = 0; i < featuresJsonArray.length(); i++){
                JSONObject jsonFeature = featuresJsonArray.getJSONObject(i);
                String id = jsonFeature.getString("id");

                //Objeto features.properties
                JSONObject jsonProperties = jsonFeature.getJSONObject("properties");
                double magnitud = jsonProperties.getDouble("mag");
                String place = jsonProperties.getString("place");
                long time = jsonProperties.getLong("time");

                //Objeto features.geometry
                JSONObject jsonGeometry = jsonFeature.getJSONObject("geometry");
                JSONArray coordinatesJsonArray = jsonGeometry.getJSONArray("coordinates");
                double longitude = coordinatesJsonArray.getDouble(0);
                double latitude = coordinatesJsonArray.getDouble(1);

                Earthquake eq = new Earthquake(id, place, magnitud, time, latitude, longitude);
                eqL.add(eq);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return eqL;
    }
}
