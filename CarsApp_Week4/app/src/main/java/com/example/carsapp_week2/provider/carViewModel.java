package com.example.carsapp_week2.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class carViewModel extends AndroidViewModel {


    private carRepository vmCarRepo;
    private LiveData<List<Car>> vmAllCars;

    public carViewModel(@NonNull Application application) {
        super(application);
        vmCarRepo = new carRepository(application);
        vmAllCars = vmCarRepo.getAllCars();
    }

    public LiveData<List<Car>> getVmAllCars() {
        return vmAllCars;
    }
    public void insert(Car car){
        vmCarRepo.insert(car);
    }
    public void deleteAll(){
        vmCarRepo.deleteAll();
    }
}
