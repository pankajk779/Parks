package com.example.parks.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParkViewModel extends ViewModel {
    private MutableLiveData<List<Park>> selectedParkList= new MutableLiveData<List<Park>>();
    private MutableLiveData<Park> selectedPark=new MutableLiveData<Park>();

    public void setParkList(List<Park> parkList){
        selectedParkList.setValue(parkList);
    }
    public void setSelectedPark(Park park){
        selectedPark.setValue(park);
    }
    public LiveData<Park> getSelectedPark(){
        return selectedPark;
    }
    public LiveData<List<Park>> getSelectedParks(){
        return selectedParkList;
    }

}
