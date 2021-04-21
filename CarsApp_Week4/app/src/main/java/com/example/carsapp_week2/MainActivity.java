package com.example.carsapp_week2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;


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
    private SharedPreferences sp;

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
    private FloatingActionButton floatingActionButton;
    private FloatingActionButton floatingActionButton2;

    // ArrayList
    ArrayList<String> carList = new ArrayList<String>();
    ArrayAdapter carAdapter;
    ArrayList<String> priceList = new ArrayList<String>();
    ArrayList<String> modelList = new ArrayList<String>();
    ArrayList<String> seatList = new ArrayList<String>();
    ArrayList<String> yearList = new ArrayList<String>();
    ArrayList<String> colorList = new ArrayList<String>();


    //layout
    DrawerLayout drawer;

    //Gson
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Setting listview adapter
        ListView listView = findViewById(R.id.listView);
        carAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, carList);
        listView.setAdapter(carAdapter);
        // Setting listview adapter


        // setting drawer that comes out when you tap the three lines
        drawer = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener((new myNavigationListener()));
        // setting drawer that comes out when you tap the three lines





        // all the id retrievals for each individual input point
        model = (EditText) findViewById(R.id.Model);
        seats = (EditText) findViewById(R.id.Seats);
        color = (EditText) findViewById(R.id.color);
        year = (EditText) findViewById(R.id.Year);
        price = (EditText) findViewById(R.id.price);
        address = (EditText) findViewById(R.id.address_input);
        // all the id retrievals for each individual input point

        // Floating action button click listener
        floatingActionButton=  findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maker = findViewById(R.id.Maker);
                maker_string = maker.getText().toString();
                makerData = getSharedPreferences("makerData", 0);
                makerEditor = makerData.edit();
                makerEditor.putString("makers",maker_string);
                makerEditor.apply();


                //price data on the side
                price = findViewById(R.id.price);
                price_string = price.getText().toString();
                priceList.add(price_string);


                //color data on the side
                color = findViewById(R.id.color);
                color_string = color.getText().toString();
                colorList.add(color_string);


                //seat data on the side
                seats = findViewById(R.id.Seats);
                seats_string = seats.getText().toString();
                seatList.add(seats_string);


                //year data on the side
                year = findViewById(R.id.Year);
                year_string = year.getText().toString();
                yearList.add(year_string);

                //model data on the side
                model = findViewById(R.id.Model);
                model_string = model.getText().toString();
                modelList.add(model_string);

                // maker added
                carList.add(maker_string);
                carAdapter.notifyDataSetChanged();
            }
        });
        // Floating action button click listener


        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in
         * class SMSReceiver @line 11
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(orderReceiver.SMS_FILTER));

    }
    View.OnClickListener UndoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            carList.remove(carList.size()-1);
            carAdapter.notifyDataSetChanged();
        }
    };
    class myNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.last_car){
                if(carList.size()==0){
                    "".isEmpty(); // do nothing
                }
                else{
                carList.remove(carList.size()-1);
                carAdapter.notifyDataSetChanged();}
            }
            else if(id == R.id.remove_all_cars){
                carAdapter.clear();

            }
            else if(id == R.id.car_count){
                Toast.makeText(getApplicationContext(),"car fleet: "+ carList.size() , Toast.LENGTH_LONG).show();
            }
            else if (id == R.id.exit){
                finishAndRemoveTask();

            }
            else if (id == R.id.list_all_items){
                Intent listAllItems = new Intent(getApplicationContext(), cardView.class);

                String carListGson = gson.toJson(carList);
                String modelListGson = gson.toJson(modelList);
                String yearListGson = gson.toJson(yearList);
                String seatListGson = gson.toJson(seatList);
                String colorListGson = gson.toJson(colorList);
                String priceListGson = gson.toJson(priceList);

                carData =getSharedPreferences("cList",0);
                carEditor=carData.edit();
                carEditor.putString("CAR_LIST", carListGson);
                carEditor.putString("MODEL_LIST",modelListGson);
                carEditor.putString("YEAR_LIST",yearListGson);
                carEditor.putString("SEAT_LIST",seatListGson);
                carEditor.putString("COLOR_LIST",colorListGson);
                carEditor.putString("PRICE_LIST",priceListGson);
                carEditor.apply();
                startActivity(listAllItems);

            }
            else if (id == R.id.add_car_button){
                // maker data for viewing
//                maker = (EditText) findViewById(R.id.Maker);
                maker = findViewById(R.id.Maker);
                maker_string = maker.getText().toString();
                makerData = getSharedPreferences("makerData", 0);
                makerEditor = makerData.edit();
                price = findViewById(R.id.price);
                price_string = price.getText().toString();
                makerEditor.putString("makers",maker_string);
                makerEditor.apply();


                //price data on the side
                price = findViewById(R.id.price);
                price_string = price.getText().toString();
                priceList.add(price_string);


                carList.add(maker_string);
                carAdapter.notifyDataSetChanged();

            }


            drawer.closeDrawer(GravityCompat.START);

            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.clear_fields){
            model = (EditText) findViewById(R.id.Model);
            maker = (EditText) findViewById(R.id.Maker);
            seats = (EditText) findViewById(R.id.Seats);
            color = (EditText) findViewById(R.id.color);
            year = (EditText) findViewById(R.id.Year);
            price = (EditText) findViewById(R.id.price);
            address = (EditText) findViewById(R.id.address_input);
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
        }
        return super.onOptionsItemSelected(item);
    }

    class MyBroadCastReceiver extends BroadcastReceiver {

        /*
         * This method 'onReceive' will get executed every time class SMSReceive sends a broadcast
         * */
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
             * Retrieve the message from the intent
             * */
            String msg = intent.getStringExtra(orderReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, ";");
            address_string = sT.nextToken();
            price_string= sT.nextToken();
            seats_string= sT.nextToken();
            model_string= sT.nextToken();
            maker_string= sT.nextToken();
            color_string= sT.nextToken();
            year_string= sT.nextToken();
            /*
             * Now, its time to update the UI
             * */
            address.setText(address_string);
            price.setText(price_string);
            boolean lessThanFour = (4>Integer.parseInt(seats_string));
            boolean greaterThanEight =(8<Integer.parseInt(seats_string));
            if(lessThanFour || greaterThanEight){
                String notRight = "seats are not sent correctly";
                seats.setText(notRight);
            }
            else{
                seats.setText(seats_string);
            }

            model.setText(model_string);
            maker.setText(maker_string);
            color.setText(color_string);
            year.setText(year_string);
        }
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
        model = (EditText) findViewById(R.id.Model);
        maker = (EditText) findViewById(R.id.Maker);
        seats = (EditText) findViewById(R.id.Seats);
        color = (EditText) findViewById(R.id.color);
        year = (EditText) findViewById(R.id.Year);
        price = (EditText) findViewById(R.id.price);
        address = (EditText) findViewById(R.id.address_input);
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
        model = (EditText) findViewById(R.id.Model);
        maker = (EditText) findViewById(R.id.Maker);
        seats = (EditText) findViewById(R.id.Seats);
        color = (EditText) findViewById(R.id.color);
        year = (EditText) findViewById(R.id.Year);
        price = (EditText) findViewById(R.id.price);
        address = (EditText) findViewById(R.id.address_input);
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

    }


    public void showSnackBar(View view){
        maker = (EditText) findViewById(R.id.Maker);
        Snackbar.make(view, "You have added "+ "("+maker.getText()+")" , Snackbar.LENGTH_LONG).show();
        maker = findViewById(R.id.Maker);
        maker_string = maker.getText().toString();
        makerData = getSharedPreferences("makerData", 0);
        makerEditor = makerData.edit();
        makerEditor.putString("makers",maker_string);
        makerEditor.commit();
    }


}