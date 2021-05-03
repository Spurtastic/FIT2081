package com.example.wk2pt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wk2pt2.provider.Car;
import com.example.wk2pt2.provider.CarViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    public static final String ACTIVITY_LIFECYCLE = "ActivityLifecycle";
    //    private TextView myTextView; //Best Practice to declare but don't initialise
//    private EditText myEditText; //Initialise in onCreate
    private EditText maker, model, year, color, seats, price;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ListView listView;
    private List<String> dataSource;
    private ArrayList<Car> carList;
    private ArrayAdapter<String> adapter;
    private MyRecyclerAdapter carAdapter;
    public int counter;
    private FloatingActionButton fab;
    private TextView double_price;
    private static CarViewModel mCarViewModel;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    Context self;
    Intent intent;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity_main);
        sharedPreferences = getSharedPreferences("MainActivity", MODE_PRIVATE);
        self = this;

        maker = findViewById(R.id.etMaker);
        model = findViewById(R.id.etModel);
        year = findViewById(R.id.etYear);
        color = findViewById(R.id.etColor);
        seats = findViewById(R.id.etSeats);
        price = findViewById(R.id.etPrice);

        maker.setText(sharedPreferences.getString("maker", "")); //Restore the value of maker after closing the app
        model.setText(sharedPreferences.getString("model", ""));
        year.setText(Integer.toString(sharedPreferences.getInt("year",2021)));
        color.setText(sharedPreferences.getString("color", ""));
        seats.setText(Integer.toString(sharedPreferences.getInt("seats",4)));
        price.setText(Integer.toString(sharedPreferences.getInt("price",100)));
        counter = sharedPreferences.getInt("counter",0);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar); //Use this toolbar instead of androids toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle); //Similar to onClickListener
        toggle.syncState(); //Update the state of the drawer (open/closed)
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        dataSource = new ArrayList<>();
        carList = new ArrayList<>();
        listView = findViewById(R.id.listview);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataSource);
        listView.setAdapter(adapter);

        //******************************************************************
        mCarViewModel = new ViewModelProvider(this).get(CarViewModel.class);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               addNewItemsButton();
            }
        });

        FirebaseDatabase fbDB=FirebaseDatabase.getInstance();
        ref=fbDB.getReference("/cars");

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        /* Create and instantiate the local broadcast receiver
           This class listens to messages come from class SMSReceiver
         */
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();

        /*
         * Register the broadcast handler with the intent filter that is declared in class SMSReceiver @line 10
         * */
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
    }



    public void addNewItemsButton(){
        String makerTxt = maker.getText().toString(); //Retrieve Value in maker and convert to a String
        String modelTxt = model.getText().toString();
        int yearInt = Integer.parseInt(year.getText().toString());
        String colorTxt = color.getText().toString();
        int seatInt = Integer.parseInt(seats.getText().toString());
        double priceDoub = Double.parseDouble(price.getText().toString());
        //Create a new view model and.insert(car);

        counter++;
        Car car = new Car(makerTxt,modelTxt,yearInt,colorTxt,seatInt,priceDoub);
        mCarViewModel.insert(car);
        ref.push().setValue(car);
        dataSource.add(makerTxt + " | " + modelTxt);
        adapter.notifyDataSetChanged();
//        Toast.makeText(this, "We added a new car (" + makerTxt + "), you have " + counter +" cars", Toast.LENGTH_LONG).show(); //Create the Toast object and show using .show()
    }

    public void clearAllItemsButton(){
        counter = 0;
        mCarViewModel.deleteAll();
        dataSource.clear();
        adapter.notifyDataSetChanged();
        ref.removeValue();
    }

    public void clearLastItemButton(){
        if(dataSource.size() > 0) {
            dataSource.remove(dataSource.size() - 1);
            adapter.notifyDataSetChanged();
            mCarViewModel.deleteLast();
            counter--;
        }
    }

    public void doublePrice(){
        double_price = findViewById(R.id.doubled_price);
        String doubled_val = price.getText().toString();
        int finalval = Integer.parseInt(doubled_val);
        double_price.setText(String.valueOf(finalval * 2));
    }

    public void listAllItems(){
        intent = new Intent(this, MainActivity2.class);
//        intent.putParcelableArrayListExtra("cars", carList);
        startActivity(intent);
    }

    public void clearFields(){
        maker.setText("");
        model.setText("");
        year.setText("");
        color.setText("");
        seats.setText("");
        price.setText("");

        SharedPreferences.Editor editor = sharedPreferences.edit(); //Open the sharedPreferences with an editor
        editor.remove("maker");
        editor.remove("model");
        editor.remove("year");
        editor.remove("color");
        editor.remove("seats");
        editor.remove("price");
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_LIFECYCLE, "OnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_LIFECYCLE, "OnStop");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_LIFECYCLE, "OnResume");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_LIFECYCLE, "OnDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit(); //Open the sharedPreferences with an editor
        editor.putString("maker", maker.getText().toString());
        editor.putString("model", model.getText().toString());
        editor.putInt("year", Integer.parseInt(year.getText().toString()));
        editor.putString("color", color.getText().toString());
        editor.putInt("seats", Integer.parseInt(seats.getText().toString()));
        editor.putInt("price", Integer.parseInt(price.getText().toString()));
        editor.putInt("counter", counter);
        editor.apply();
        Log.i(ACTIVITY_LIFECYCLE, "OnPause");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        SharedPreferences.Editor editor = sharedPreferences.edit(); //Open SharedPreferences file to Edit
//        outState.putInt("counter", counter);
        Log.i(ACTIVITY_LIFECYCLE, "OnSaveInstanceState");

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState) {
        super.onRestoreInstanceState(inState);
//        counter = inState.getInt("counter");
        Log.i(ACTIVITY_LIFECYCLE, "OnRestoreInstanceState");

    }

    /**
     * Used to Inflate a Menu (Used to tell android what to open
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * Called when an option item gets clicked
     * Used to extract menu item and select the appropriate behaviour
     * @param item input reference to which button was clicked
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.clear_fields){
            //Add clear_fields method here
            clearFields();
        }
        return true;
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
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            /*
             * String Tokenizer is used to parse the incoming message
             * The protocol is to have the account holder name and account number separate by a semicolon
             * */

            StringTokenizer sT = new StringTokenizer(msg, ";");
            String strMaker = sT.nextToken();
            String strModel = sT.nextToken();
            String strYear = sT.nextToken();
            String strColor = sT.nextToken();
            String strSeats = sT.nextToken();
            String strPrice = sT.nextToken();

            /*
             * Now, its time to update the UI
             * */
            maker.setText(strMaker);
            model.setText(strModel);
            year.setText(strYear);
            color.setText(strColor);
            if(Integer.parseInt(strSeats) < 4 || Integer.parseInt(strSeats) > 10) {
                Toast.makeText(self, "Invalid number of seats", Toast.LENGTH_LONG).show();
                seats.setText("4");
            }else{
                seats.setText(strSeats);
            }
            price.setText(strPrice);
        }
    }


    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();
            if (id == R.id.add_car) {
                addNewItemsButton();
            } else if (id == R.id.remove_last_car) {
                clearLastItemButton();
            } else if(id == R.id.remove_all_cars){
                clearAllItemsButton();
            } else if(id == R.id.double_price){
                doublePrice();
            } else if(id == R.id.list_all_items){
                listAllItems();
            }
            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }

    }

//Declare permissions in manifest
//Request Permission in Main
//Accept Permission in UI
//
//Go to manifest/code and give permissions for receiver line 17 in smstokenizer(manifest)
//
//Register receiver in code
//-Accept both SMSReceiver and myBroadcastReceiver
//-Both Nested and Standalone Receiver
//
//Register receiver in manifest
//-Only SMSReceiver
//-Don't Have myBroadcastReceiver (nested receiver)
//
//Set intent filter action string for the broadcast we want to receiver
}