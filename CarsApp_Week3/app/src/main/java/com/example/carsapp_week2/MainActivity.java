package com.example.carsapp_week2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Strings
    private String model_string;
    private String maker_string;
    private String seats_string;
    private String color_string;
    private String year_string;
    private String price_string;
    private String address_string;

    // Shared preferences
    private SharedPreferences carData;
    private SharedPreferences makerData;

    // Editors
    private SharedPreferences.Editor carEditor;
    private SharedPreferences.Editor makerEditor;

    // EditText;
    private EditText model;
    private EditText maker;
    private EditText seats;
    private EditText color;
    private EditText year ;
    private EditText price;
    private EditText address;

    // Buttons
    private Button resetBtn;
    private Button loadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = (EditText) findViewById(R.id.Model);
        maker = (EditText) findViewById(R.id.Maker);
        seats = (EditText) findViewById(R.id.Seats);
        color = (EditText) findViewById(R.id.color);
        year = (EditText) findViewById(R.id.Year);
        price = (EditText) findViewById(R.id.price);
        address = (EditText) findViewById(R.id.address_input);
        loadBtn = (Button) findViewById(R.id.load_btn);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makerData = getSharedPreferences("makerData", 0);
                maker_string= makerData.getString("makers",maker_string);
                maker.setText(maker_string);
                System.out.println(maker_string);
            }
        });
        resetBtn = (Button) findViewById(R.id.button_reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carData = getSharedPreferences("carData",0);
                carEditor = carData.edit();
                model.setText("");
                model.setText("");
                maker.setText("");
                seats.setText("");
                color.setText("");
                year.setText("");
                price.setText("");
                address.setText("");
                carEditor.clear().apply();
                System.out.println("this ran");

            }
        });

    }
    protected void startSave(EditText inputText, String keyName ){
        int ID = inputText.getId();
        String inputTextString;
        carData = getSharedPreferences("carData", 0 );
        inputTextString = carData.getString(keyName,"");
        inputText = findViewById(ID);
        inputText.setText(inputTextString);
//        System.out.println("startSave ran");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("carApp", "onStart: ");
        startSave(model, "model");
        startSave(maker, "maker");
        startSave(seats, "seats");
        startSave(price, "price");
        startSave(year, "year");
        startSave(color,"color");
        startSave(address,"address");


//        System.out.println(maker_string);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("carApp", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("carApp", "onPause: ");
//        System.out.println(R.id.Model);
    }
    // here the method basically retrieves the integer id of the EditText parses it through to get a string to be placed into the bundle
    protected void killSave(EditText inputText, String editTextString, String keyName){
        int ID = inputText.getId();
        inputText = findViewById(ID);
        editTextString = inputText.getText().toString();
        carData = getSharedPreferences("carData", 0);
        carEditor = carData.edit();
        carEditor.putString(keyName,editTextString);
        carEditor.commit();
//        System.out.println("killSave ran");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("carApp", "onStop: ");
        killSave(model, model_string,"model");
        killSave(seats,seats_string,"seats");
        killSave(maker, maker_string, "maker");
        killSave(color,color_string,"color");
        killSave(year,year_string,"year");
        killSave(price,price_string,"price");
        killSave(address,address_string,"address");






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("carApp", "onDestroy: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("carApp", "onSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        Log.i("carApp", "onRestoreInstanceState: ");
        carData = getSharedPreferences("carData",0);
        carEditor = carData.edit();
        model.setText("");
        model.setText("");
        maker.setText("");
        seats.setText("");
        color.setText("");
        year.setText("");
        price.setText("");
        address.setText("");
        carEditor.clear().apply();

        // so that the maker data is also erased when the orientation is changed
        makerData = getSharedPreferences("makerData", 0 );
        makerEditor = makerData.edit();
        maker.setText("");
        makerEditor.clear().apply();

    }


    public void showToast(View view){
        maker = (EditText) findViewById(R.id.Maker);
        Toast.makeText(this,"You have added "+ "("+maker.getText()+")" , Toast.LENGTH_SHORT).show();
        maker = findViewById(R.id.Maker);
        maker_string = maker.getText().toString();
        makerData = getSharedPreferences("makerData", 0);
        makerEditor = makerData.edit();
        makerEditor.putString("makers",maker_string);
        makerEditor.commit();
    }


}