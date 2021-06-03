package com.macrobios.earthquakemonitor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();

    public LiveData<List<Earthquake>> getEqList() {
        return eqList;
    }

    //Genera los datos de terremotos
    public void getEartquakes(){
        ArrayList<Earthquake> eqList = new ArrayList<>();
        eqList.add(new Earthquake("qwe", "Buenos Aires", 5.0, 564684221665L,  103.25, 98.8527));
        eqList.add(new Earthquake("asd", "Mexico", 9.0, 564684285236L,  103.25, 98.8527));
        eqList.add(new Earthquake("zxc", "Chiapas", 3.0, 963874221665L,  103.25, 98.8527));
        eqList.add(new Earthquake("rty", "Canada", 5.0, 564687496365L,  103.25, 98.8527));
        eqList.add(new Earthquake("fgh", "Chile", 1.6, 567785221665L,  103.25, 98.8527));

        this.eqList.setValue(eqList);
    }
}
