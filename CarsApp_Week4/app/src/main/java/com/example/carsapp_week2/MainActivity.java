package com.example.carsapp_week2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.carsapp_week2.provider.Car;
import com.example.carsapp_week2.provider.carViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {
    // integers
    private int seats_int;
    private int year_int;
    private int price_int;
    private int action_int;
    private int x_int;
    private int y_int;
    int size;


    // Strings
    private String model_string;
    private String maker_string;
    private String color_string;
    private String address_string;
    private String seats_string;
    private String year_string;
    private String price_string;

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

    //provide variables
    private carViewModel mCarViewModel;

    // recycler
    private MyRecyclerAdapter carAdapter;

    // not really sure how to parse data from the db to listview these are arraylists i used
    private ArrayList<String> makerArray= new ArrayList<String>();
    private ArrayAdapter makerAdapter;


    //layout
    private DrawerLayout drawer;


    //Database Reference
    private DatabaseReference myRef;



    // View object
    private View frameForTouch;
    private View frameForTouch2;

    // Text Views
    private TextView showTouch;

    //Gesture detector
    GestureDetector gestureTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // setting the carViewModel up for data retrieval
        carAdapter = new MyRecyclerAdapter();
        mCarViewModel = new ViewModelProvider(this).get(carViewModel.class);
        mCarViewModel.getVmAllCars().observe(this, newData ->{
            carAdapter.setCarData(newData);
            carAdapter.notifyDataSetChanged();
            // set up the list using the db
            TextView temp = findViewById(R.id.textView2);
            temp.setText(newData.size()+"");
            size = newData.size();
        });
        // setting the carViewModel up for data retrieval



        // setting drawer that comes out when you tap the three lines
        drawer = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener((new myNavigationListener()));
        // setting drawer that comes out when you tap the three lines


        // firebase setup
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Cars");
        // firebase setup



        // all the id retrievals for each individual input point
        model = (EditText) findViewById(R.id.Model);
        seats = (EditText) findViewById(R.id.Seats);
        color = (EditText) findViewById(R.id.color);
        year = (EditText) findViewById(R.id.Year);
        price = (EditText) findViewById(R.id.price);
        address = (EditText) findViewById(R.id.address_input);

        // all the id retrievals for each individual input point

        // Floating action button click listener for adding values
        floatingActionButton=  findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maker_string = maker.getText().toString();
                model_string = model.getText().toString();
                color_string = color.getText().toString();
                year_int = Integer.parseInt(year.getText().toString());
                seats_int = Integer.parseInt(seats.getText().toString());
                price_int = Integer.parseInt(price.getText().toString());
                Car addCar = new Car(maker_string,model_string, year_int,seats_int,color_string,price_int);
                mCarViewModel.insert(addCar);
                myRef.push().setValue(addCar);
                makerArray.add(maker_string);
//                makerAdapter.notifyDataSetChanged();
            }
        });
        // Floating action button click listener


        // SMS section to retrieve and store data
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
        //SMS section to retrieve and store data

        // touch interactions
        frameForTouch = findViewById(R.id.constraint_id);
        gestureTouchEvents = new GestureDetector(this, new gestureDetectorCar());



        frameForTouch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                gestureTouchEvents.onTouchEvent(event);
                return true;
            }
        });




    }
    View.OnClickListener UndoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            makerArray.remove(size-1);
            makerAdapter.notifyDataSetChanged();
        }
    };
    class myNavigationListener implements NavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if(id == R.id.last_car){
                if((size == 0)&&(makerArray.size()==0) ){

                }
                else{
                    mCarViewModel.deleteLast();
                    carAdapter.notifyDataSetChanged();
                    makerArray.remove(makerArray.size()-1);
//                    makerAdapter.notifyDataSetChanged();
                }
            }
            else if(id == R.id.remove_all_cars){
                mCarViewModel.deleteAll();
                makerAdapter.clear();

                myRef.removeValue();


            }
            else if(id == R.id.car_count){
                Toast.makeText(getApplicationContext(),"car fleet: "+ size , Toast.LENGTH_LONG).show();
            }
            else if (id == R.id.exit){
                finishAndRemoveTask();
            }
            else if (id == R.id.list_all_items){
                Intent listAllItems = new Intent(getApplicationContext(), cardView.class);

                startActivity(listAllItems);

            }
            else if (id == R.id.add_car_button){
                maker_string = maker.getText().toString();
                model_string = model.getText().toString();
                color_string = color.getText().toString();
                year_int = Integer.parseInt(year.getText().toString());
                seats_int = Integer.parseInt(seats.getText().toString());
                price_int = Integer.parseInt(price.getText().toString());
                Car addCar = new Car(maker_string,model_string, year_int,seats_int,color_string,price_int);
                mCarViewModel.insert(addCar);
                makerArray.add(maker_string);
                makerAdapter.notifyDataSetChanged();

            }
            drawer.closeDrawer(GravityCompat.START);

            return true;
        }
    }
    class gestureDetectorCar extends GestureDetector.SimpleOnGestureListener{

        private EditText model;
        private EditText maker;
        private EditText seats;
        private EditText color;
        private EditText year ;
        private EditText price;
        private EditText address;
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            model = findViewById(R.id.Model);
            model.setText("T-9000");

            maker = findViewById(R.id.Maker);
            maker.setText("SkyNet");

            seats = findViewById(R.id.Seats);
            seats.setText("1");

            price = findViewById(R.id.price);
            price.setText("20000.0");

            color = findViewById(R.id.color);
            color.setText("Silver");

            year = findViewById(R.id.Year);
            year.setText("1");

            address = findViewById(R.id.address_input);
            address.setText("Belmont road, 42nd avenue");

            return super.onDoubleTap(e);
        }


        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            seats = findViewById(R.id.Seats);
            if (!seats.getText().toString().equals("")){
                if(Integer.parseInt(seats.getText().toString())>9){
                    Toast.makeText(getApplicationContext(), "Max number of seats", Toast.LENGTH_SHORT).show();
                }
                else{
                    seats.setText(Integer.toString(Integer.parseInt(seats.getText().toString())+1));
                }
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            price = findViewById(R.id.price);
            price.setText(Float.toString(Float.parseFloat(price.getText().toString())- distanceX));
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            clearFields();
            super.onLongPress(e);
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
            clearFields();
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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);

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
    public void addVehicle(){
        maker_string = maker.getText().toString();
        model_string = model.getText().toString();
        color_string = color.getText().toString();
        year_int = Integer.parseInt(year.getText().toString());
        seats_int = Integer.parseInt(seats.getText().toString());
        price_int = Integer.parseInt(price.getText().toString());
        Car addCar = new Car(maker_string,model_string, year_int,seats_int,color_string,price_int);
        mCarViewModel.insert(addCar);
        makerArray.add(maker_string);
    }
    public void clearFields(){
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
        year.setText("0.0");
        seats.setText("0.0");
        price.setText("0.0");
        carEditor.clear().apply();
    }


}