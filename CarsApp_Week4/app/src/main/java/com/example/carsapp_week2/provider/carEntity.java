package com.example.carsapp_week2.provider;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cars")
public class carEntity {
    // car number for unique keys
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "car_id")
    private int car_id;

    //maker name
    @ColumnInfo(name = "maker_name")
    private String maker_name;

    //model name
    @ColumnInfo(name = "model_name")
    private String model_name;

    //Year
    @ColumnInfo(name = "year")
    private int Year;

    //Number of seats
    @ColumnInfo(name = "seat_no")
    private int seat_no;

    //Color specified
    @ColumnInfo(name = "color")
    private String color;

    //Price of car
    @ColumnInfo(name = "price")
    private int price;


    public  carEntity(int car_id, String maker_name, String model_name, int Year, int seat_no, String color, int price){
        this.car_id = car_id;
        this.maker_name = maker_name;
        this.model_name = model_name;
        this.Year = Year;
        this.seat_no = seat_no;
        this.color = color;
        this.price = price;

    }


    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(@NonNull int car_id) {
        this.car_id = car_id;
    }

    public String getMaker_name() {
        return maker_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public int getYear() {
        return Year;
    }

    public int getSeat_no() {
        return seat_no;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }
}
