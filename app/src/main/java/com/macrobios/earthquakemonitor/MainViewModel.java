package com.macrobios.earthquakemonitor;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macrobios.earthquakemonitor.api.EarthquakeClient;
import com.macrobios.earthquakemonitor.api.EarthquakeJSONResponse;
import com.macrobios.earthquakemonitor.api.Feature;

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

    private MainRepository repository = new MainRepository();

    public void getEarthquakes() {
        repository.getEartquakes(earthquakeList -> {
            eqList.setValue(earthquakeList);
        });
    }


}
