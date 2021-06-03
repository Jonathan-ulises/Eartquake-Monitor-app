package com.macrobios.earthquakemonitor.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.macrobios.earthquakemonitor.Earthquake;
import com.macrobios.earthquakemonitor.database.EarthquakeDataBase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MainRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        EarthquakeDataBase dataBase = EarthquakeDataBase.getDatabase(application);

        this.repository = new MainRepository(dataBase);
    }

    public LiveData<List<Earthquake>> getEqList() {

        return repository.getEqList();
    }

    public void downloadEarthquakes() {
        repository.downloadAndSaveEarthquakes();

        /*repository.getEartquakes(earthquakeList -> {
            eqList.setValue(earthquakeList);
        });*/

        /*repository.getEartquakes(new MainRepository.DownloadEarthquakesInternet() {
            @Override
            public void onEqsDownloaded(List<Earthquake> earthquakeList) {

            }
        });*/
    }


}
