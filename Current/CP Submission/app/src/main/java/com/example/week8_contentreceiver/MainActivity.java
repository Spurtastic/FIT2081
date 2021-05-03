package com.example.week8_contentreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView carCount;
    Button refreshCarCount;
    Button deleteOldCars;
    EditText etCarYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carCount=findViewById(R.id.tvCarCount);
        Uri uri= Uri.parse("content://fit2081.app.Jason/cars");
//        Uri uri= Uri.parse("content://fit2081.app.Jason/cars");
        Cursor result= getContentResolver().query(uri,null,null,null);
        Log.d("Week8",result.getCount() + " *******");
        carCount.setText(result.getCount()+"");

        refreshCarCount = findViewById(R.id.btnRefreshCarCount);
        refreshCarCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = getContentResolver().query(uri, null, null, null);
                carCount.setText(res.getCount() + "") ;
            }
        });

        deleteOldCars = findViewById(R.id.btnRemoveOldCars);
        etCarYear = findViewById(R.id.etOldCarYear);
        deleteOldCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int deletedRows;
                deletedRows = getContentResolver().delete(uri,"carYear <" + etCarYear.getText().toString(),null);

            }
        });

    }
}