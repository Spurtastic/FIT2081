package com.example.wk2pt2.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CarRepository {
    private CarDao mCarDao;
    private LiveData<List<Car>> mAllCars;

    CarRepository(Application application){
        CarDatabase db = CarDatabase.getDatabase(application);
        mCarDao = db.carDao();
        mAllCars = mCarDao.getAllCars();
    }

    LiveData<List<Car>> getAllCars(){ return mAllCars; }

    void insert(Car car){
        CarDatabase.databaseWriteExecutor.execute(() -> mCarDao.addCar(car));
    }

    void deleteAll(){
        CarDatabase.databaseWriteExecutor.execute(() -> {
            mCarDao.deleteAllCars();
        });
    }

    void deleteLast(){
        CarDatabase.databaseWriteExecutor.execute(() -> {
            mCarDao.deleteLastCar();
        });
    }
}
