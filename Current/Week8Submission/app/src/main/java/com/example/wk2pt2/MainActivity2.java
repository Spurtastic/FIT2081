package com.example.wk2pt2;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;

import com.example.wk2pt2.provider.Car;
import com.example.wk2pt2.provider.CarViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerAdapter carAdapter;
    CarViewModel mCarViewModel;
    Button deleteAll;
    DatabaseReference ref;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.my_recycler_view);
        carAdapter = new MyRecyclerAdapter();
        deleteAll = findViewById(R.id.deleteAll);

        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
        carAdapter = new MyRecyclerAdapter();
        recyclerView.setAdapter(carAdapter);

        FirebaseDatabase fbDB=FirebaseDatabase.getInstance();
        ref=fbDB.getReference("/cars");

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCarViewModel.deleteAll();
                ref.removeValue();
            }
        });

        mCarViewModel = new ViewModelProvider(this).get(CarViewModel.class);
        mCarViewModel.getAllCars().observe(this, newData -> {
            carAdapter.setCar(newData);
            carAdapter.notifyDataSetChanged();
//            Log.d("Week7Test", " ***********" + newData);
        });

//        carList = getIntent().getExtras().getParcelableArrayList("cars");
//        adapter.setCarData(carList);
//        adapter.notifyDataSetChanged();


    }




}
