package com.example.carsapp_week2.provider;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface carDao {

    // remember Cars is the table name
    @Query("select * from Cars")
    LiveData<List<Car>> getAllCars();

    @Query("select * from Cars where maker_name = :makerName")
    List<Car> getCarMaker(String makerName);

    @Query("select * from Cars where model_name = :modelName")
    List<Car> getCarModel(String modelName);

    @Query("select * from Cars where year = :year")
    List<Car> getCarYear(int year);

    @Query("select * from Cars where seat_no = :seatNo")
    List<Car> getCarSeats(int seatNo);

    @Query("select * from Cars where color = :color")
    List<Car> getCarColor(String color);

    @Query("select * from Cars where price = :price")
    List<Car> getCarPrice(int price);

    @Insert
    void addCar(Car car);

    @Query("delete From Cars")
    void deleteAllCars();


}
