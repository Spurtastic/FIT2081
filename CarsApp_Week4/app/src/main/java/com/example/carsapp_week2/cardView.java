package com.example.carsapp_week2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class cardView extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    SharedPreferences fromMain;

    // stored Gsons
    String carListGson;
    String modelListGson;
    String yearListGson;
    String seatListGson;
    String colorListGson;
    String priceListGson;


    // Type to be converted to
    Type array;

    //retrieving the list
    ArrayList<String> carList;
    ArrayList<String> modelList;
    ArrayList<String> yearList;
    ArrayList<String> seatList;
    ArrayList<String> colorList;
    ArrayList<String> priceList;


    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView =  findViewById(R.id.rv);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        fromMain = getSharedPreferences("cList",0);

        carListGson = fromMain.getString("CAR_LIST","");
        modelListGson = fromMain.getString("MODEL_LIST","");
        yearListGson = fromMain.getString("YEAR_LIST","");
        seatListGson = fromMain.getString("SEAT_LIST","");
        colorListGson = fromMain.getString("COLOR_LIST","");
        priceListGson = fromMain.getString("PRICE_LIST","");





        array = new TypeToken<ArrayList<String>>() {}.getType();
        gson = new Gson();
        carList = gson.fromJson(carListGson, array);   // convert the carlist gson to an array
        modelList =gson.fromJson(modelListGson,array);
        yearList =gson.fromJson(yearListGson,array);
        seatList =gson.fromJson(seatListGson,array);
        colorList =gson.fromJson(colorListGson,array);
        priceList =gson.fromJson(priceListGson,array);

        adapter = new MyRecyclerAdapter(carList,modelList,yearList,seatList,colorList,priceList);
        recyclerView.setAdapter(adapter);

    }
}