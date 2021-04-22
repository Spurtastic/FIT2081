package com.example.carsapp_week2.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class carViewModel extends AndroidViewModel {


    private carRepository vmCarRepo;
    private LiveData<List<Car>> vmAllCars;
    LiveData<Integer> size;

    public carViewModel(@NonNull Application application) {
        super(application);
        vmCarRepo = new carRepository(application);
        vmAllCars = vmCarRepo.getAllCars();
        size = vmCarRepo.getSize();
    }

    public LiveData<List<Car>> getVmAllCars() {
        return vmAllCars;
    }
    public LiveData<Integer> getSize(){return  size;}
    public void insert(Car car){
        vmCarRepo.insert(car);
    }
    public void deleteAll(){
        vmCarRepo.deleteAll();
    }
    public void deleteLast(){
        vmCarRepo.deleteLast();
    }

}
