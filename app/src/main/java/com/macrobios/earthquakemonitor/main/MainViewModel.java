package com.macrobios.earthquakemonitor.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.macrobios.earthquakemonitor.Earthquake;

import java.util.List;

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

        /*repository.getEartquakes(new MainRepository.DownloadEarthquakesInternet() {
            @Override
            public void onEqsDownloaded(List<Earthquake> earthquakeList) {

            }
        });*/
    }


}
