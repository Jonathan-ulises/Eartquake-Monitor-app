package com.macrobios.earthquakemonitor.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.macrobios.earthquakemonitor.Earthquake;
import com.macrobios.earthquakemonitor.api.RequestStatus;
import com.macrobios.earthquakemonitor.api.StatusWithDescription;
import com.macrobios.earthquakemonitor.database.EarthquakeDataBase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private MainRepository repository;
    private MutableLiveData<StatusWithDescription> statusMutableLiveData = new MutableLiveData<>();

    public LiveData<StatusWithDescription> getStatusWithDescription() {
        return statusMutableLiveData;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);

        EarthquakeDataBase dataBase = EarthquakeDataBase.getDatabase(application);

        this.repository = new MainRepository(dataBase);
    }

    public LiveData<List<Earthquake>> getEqList() {

        return repository.getEqList();
    }

    public void downloadEarthquakes() {
        statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.LOADING, ""));
        repository.downloadAndSaveEarthquakes(new MainRepository.DownloadStatusListener() {
            @Override
            public void downloadSucces() {
                statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.DONE, ""));
            }

            @Override
            public void downloadError(String message) {
                statusMutableLiveData.setValue(new StatusWithDescription(RequestStatus.ERROR, message));
            }
        });

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
