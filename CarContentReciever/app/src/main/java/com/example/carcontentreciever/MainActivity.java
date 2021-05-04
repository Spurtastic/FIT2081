package com.example.carcontentreciever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    // EditText
    EditText editTextOfOldCar;

    // TextViews
    TextView textView;

    // Buttons
    Button btnRemoveOldCars;
    Button btnRefreshCarCount;
    Button addVios;

    // Cursor
    Cursor result;

    //car
    Car vios;

    //Databases
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvCarCount);
        Uri carAppUri = Uri.parse("content://carApp.contpro/Cars");
        result = getContentResolver().query(carAppUri, null, null, null);

        // calling the firebase here
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Cars");
        //

        // Resets the car count that you have//
        btnRefreshCarCount = findViewById(R.id.btnRefreshCarCount);
        btnRefreshCarCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = getContentResolver().query(carAppUri, null, null, null);
                textView.setText(result.getCount()+"");
            }
        });

        addVios = findViewById(R.id.add_vios);
        addVios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vios = new Car("Toyota", "Vios", 2020, 5,"white",80000);
                ContentValues values = new ContentValues();
                values.put("maker_name",vios.getMaker_name());
                values.put("model_name", vios.getModel_name());
                values.put("year", vios.getYear());
                values.put("seat_no", vios.getSeat_no());
                values.put("color", vios.getColor());
                values.put("price", vios.getPrice());
                Uri uri2 = getContentResolver().insert(carAppUri, values);
                myRef.push().setValue(vios);

            }
        });



        // runs the query to delete based on column name
        editTextOfOldCar = findViewById(R.id.etOldCarYear);
        btnRemoveOldCars = findViewById(R.id.btnRemoveOldCars);
        btnRemoveOldCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // What i want is to remove all cars with the year less than the stipulated year including the one with that year
                // i.e. if the input is 2000, it will delete cars that are lesser than 2000 as well as the car that was born on 2000
                // Delete * from Cars where Year <?
                int deletedRows;
                deletedRows = getContentResolver().delete(carAppUri,"year <" + editTextOfOldCar.getText().toString(),null);
            }
        });
        textView.setText(result.getCount()+"");


    }
}