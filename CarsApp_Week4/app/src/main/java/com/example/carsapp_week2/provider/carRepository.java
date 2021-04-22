package com.example.carsapp_week2.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class carRepository {
    private carDao rcarDao;
    private LiveData<List<Car>> rallCars;
    LiveData<Integer> size;

    carRepository(Application application){
        carDB database = carDB.getDatabase(application);
        rcarDao = database.CarDao();
        rallCars = rcarDao.getAllCars();
        size = rcarDao.getSize();
    }

    LiveData<Integer> getSize(){return size;}
    LiveData<List<Car>> getAllCars(){
        return rallCars;
    }
    void insert(Car car){
        carDB.databaseWriteExecutor.execute(() -> rcarDao.addCar(car));
    }

    void deleteAll(){
        carDB.databaseWriteExecutor.execute(()->{
            rcarDao.deleteAllCars();
        });
    }
    void deleteLast(){
        carDB.databaseWriteExecutor.execute(()->{ rcarDao.deleteLastCar();});
    }




}
