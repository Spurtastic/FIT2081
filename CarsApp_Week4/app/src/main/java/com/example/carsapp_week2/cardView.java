package com.example.carsapp_week2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carsapp_week2.provider.carViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class cardView extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerAdapter adapter;


    // view model
    public static carViewModel mCarViewModel;





    private FloatingActionButton floatingActionButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        recyclerView =  findViewById(R.id.rv);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager

        adapter = new MyRecyclerAdapter();
        recyclerView.setAdapter(adapter);

        mCarViewModel = new ViewModelProvider(this).get(carViewModel.class);
        mCarViewModel.getVmAllCars().observe(this, newData ->{
            adapter.setCarData(newData);
            adapter.notifyDataSetChanged();
        });

        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });


    }
}