package com.macrobios.earthquakemonitor.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macrobios.earthquakemonitor.Earthquake;
import com.macrobios.earthquakemonitor.database.EarthquakeDataBase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MainRepository repository;

    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);

        EarthquakeDataBase dataBase = EarthquakeDataBase.getDatabase(application);

        this.repository = new MainRepository(dataBase);
    }

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    public void getEarthquakes() {
        repository.getEartquakes(earthquakeList -> {
            eqList.setValue(earthquakeList);
        });

        /*repository.getEartquakes(new MainRepository.DownloadEarthquakesInternet() {
            @Override
            public void onEqsDownloaded(List<Earthquake> earthquakeList) {

            }
        });*/
    }


}
