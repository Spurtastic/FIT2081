package com.example.wk2pt2.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car implements Parcelable {
    public static final String TABLE_NAME = "cars";
    public static final String COLUMN_ID = BaseColumns._ID;
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "carId")
    private int id;
    @ColumnInfo(name = "carMaker")
    private String maker;
    @ColumnInfo(name = "carModel")
    private String model;
    @ColumnInfo(name = "carYear")
    private int year;
    @ColumnInfo(name = "carColor")
    private String color;
    @ColumnInfo(name = "carSeats")
    private int seats;
    @ColumnInfo(name = "carPrice")
    private double price;


    public Car(String maker, String model, int year, String color, int seats, double price) {
        this.maker = maker;
        this.model = model;
        this.year = year;
        this.color = color;
        this.seats = seats;
        this.price = price;
    }

    protected Car(Parcel in) {
        maker = in.readString();
        model = in.readString();
        year = in.readInt();
        color = in.readString();
        seats = in.readInt();
        price = in.readDouble();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public int getSeats() {
        return seats;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maker);
        dest.writeString(model);
        dest.writeInt(year);
        dest.writeString(color);
        dest.writeInt(seats);
        dest.writeDouble(price);
    }
}
