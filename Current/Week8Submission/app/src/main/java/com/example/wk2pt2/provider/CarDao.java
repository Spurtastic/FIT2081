package com.example.wk2pt2.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CarDao {

    @Query("Select * from cars")
    LiveData<List<Car>> getAllCars();

    @Query("Select * from cars where carMaker=:name")
    List<Car> getCarMaker(String name);

    @Query("Select * from cars where carModel=:name")
    List<Car> getCarModel(String name);

    @Query("Select * from cars where carYear=:name")
    List<Car> getCarYear(String name);

    @Query("Select * from cars where carSeats=:name")
    List<Car> getCarSeats(String name);

    @Query("Select * from cars where carColor=:name")
    List<Car> getCarColor(String name);

    @Query("Select * from cars where carPrice=:name")
    List<Car> getCarPrice(String name);

    @Insert
    void addCar(Car car);

    @Query("Delete from cars where carId = (Select max(carId) from cars)")
    void deleteLastCar();

    @Query("Delete from cars")
    void deleteAllCars();

    @Query("Delete from cars where carYear < :year")
    void deleteOldCars(int year);
}
