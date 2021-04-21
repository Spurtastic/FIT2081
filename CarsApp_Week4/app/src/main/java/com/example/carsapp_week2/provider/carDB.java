package com.example.carsapp_week2.provider;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Car.class},version = 1)
public abstract class carDB extends RoomDatabase {

    public static final String CAR_STORAGE_DATABASE_NAME = "car_dataBase";

    public abstract carDao CarDao();

    public static volatile carDB INSTANCE;

    public static final int NUMBER_OF_THREADS = 4;

    // this is the area to manipulate the data base
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static carDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (carDB.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),carDB.class, CAR_STORAGE_DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }


}
