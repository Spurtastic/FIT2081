package edu.monash.fit2081.countryinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.app.NavUtils;
import androidx.appcompat.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CountryDetails extends AppCompatActivity {
    // text views
    private TextView name;
    private TextView capital;
    private TextView code;
    private TextView population;
    private TextView area;
    private TextView currency;
    private TextView language;
    private TextView region;

    // String items
    private String languages;
    private String wikiUrl;

    // Image view
    private ImageView flag;

    // Button
    private Button btnWiki;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        getSupportActionBar().setTitle(R.string.title_activity_country_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String selectedCountry = getIntent().getStringExtra("country");

        name = findViewById(R.id.country_name);
        capital =  findViewById(R.id.capital);
        code =  findViewById(R.id.country_code);
        population =  findViewById(R.id.population);
        area = findViewById(R.id.area);
        currency = findViewById(R.id.currency_id);
        language = findViewById(R.id.languages);
        flag = findViewById(R.id.flagView);
        region = findViewById(R.id.region);
        btnWiki = findViewById(R.id.wiki_button);
        btnWiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wiki = new Intent(getApplicationContext(), WebWiki.class);
                wiki.putExtra("key1", wikiUrl);
                startActivity(wiki);

            }
        });

        new GetCountryDetails().execute(selectedCountry);
    }

    private class GetCountryDetails extends AsyncTask<String, String, CountryInfo> {

        @Override
        protected CountryInfo doInBackground(String... params) {
            CountryInfo countryInfo = null;
            try {
                // Create URL
                String selectedCountry = params[0];
                URL webServiceEndPoint = new URL("https://restcountries.eu/rest/v2/name/" + selectedCountry); //

                // Create connection
                HttpsURLConnection myConnection = (HttpsURLConnection) webServiceEndPoint.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    //JSON data has arrived successfully, now we need to open a stream to it and get a reader
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                    //now use a JSON parser to decode data
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginArray(); //consume arrays's opening JSON brace
                    String keyName;
                    countryInfo = new CountryInfo(); //nested class (see below) to carry Country Data around in
                    boolean countryFound = false;
                    while (jsonReader.hasNext() && !countryFound) { //process array of objects
                        jsonReader.beginObject(); //consume object's opening JSON brace
                        while (jsonReader.hasNext()) {// process key/value pairs inside the current object
                            keyName = jsonReader.nextName();

                            if (keyName.equals("name")) {
                                countryInfo.setName(jsonReader.nextString());
                                if (countryInfo.getName().equalsIgnoreCase(selectedCountry)) {
                                    countryFound = true;
                                }
                            } else if (keyName.equals("alpha3Code")) {
                                countryInfo.setAlpha3Code(jsonReader.nextString());
                            } else if (keyName.equals("capital")) {
                                countryInfo.setCapital(jsonReader.nextString());
                            } else if (keyName.equals("population")) {
                                countryInfo.setPopulation(jsonReader.nextInt());
                            } else if (keyName.equals("area")) {
                                countryInfo.setArea(jsonReader.nextDouble());
                            } else if (keyName.equals("currencies")) {
                                jsonReader.beginArray();
                                {
                                    while(jsonReader.hasNext())
                                    {
                                        jsonReader.beginObject();
                                        {
                                            while(jsonReader.hasNext())
                                            {

                                                keyName = jsonReader.nextName();
                                                if(keyName.equals("name"))
                                                {
                                                    countryInfo.setCurrency(jsonReader.nextString());
                                                }
                                                else{
                                                    jsonReader.skipValue();
                                                }
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                }
                                jsonReader.endArray();

                            }
                            else if (keyName.equals("languages")) {
                                languages  ="";
                                jsonReader.beginArray();
                                {
                                    while(jsonReader.hasNext())
                                    {
                                        jsonReader.beginObject();
                                        {
                                            while(jsonReader.hasNext())
                                            {

                                                keyName = jsonReader.nextName();
                                                if(keyName.equals("name"))
                                                {
                                                    languages += ", ";
                                                    languages+= jsonReader.nextString();
                                                    countryInfo.setLanguages(languages.substring(2));
                                                }
                                                else{
                                                    jsonReader.skipValue();
                                                }
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                }
                                jsonReader.endArray();
                            }
                            else if (keyName.equals("alpha2Code")){
                                countryInfo.setAlpha2Code(jsonReader.nextString());
                            }
                            else if (keyName.equals("region")){
                                countryInfo.setRegion(jsonReader.nextString());
                            }
                            else {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                } else {
                    Log.i("INFO", "Error:  No response");
                }

                // All your networking logic should be here
            } catch (Exception e) {
                Log.i("INFO", "Error " + e.toString());
            }
            return countryInfo;
        }

        @Override
        protected void onPostExecute(CountryInfo countryInfo) {
            super.onPostExecute(countryInfo);
            name.setText(countryInfo.getName());
            capital.setText(countryInfo.getCapital());
            code.setText(countryInfo.getAlpha3Code());
            population.setText(Integer.toString(countryInfo.getPopulation()));
            area.setText(Double.toString(countryInfo.getArea()));
            currency.setText(countryInfo.getCurrency());
            language.setText(countryInfo.getLanguages());
            region.setText(countryInfo.getRegion());
            btnWiki.setText("Wiki "+countryInfo.getName());
            wikiUrl = "https://en.wikipedia.org/wiki/"+countryInfo.getName();

            new GetFlag().execute("https://flagcdn.com/144x108/"+countryInfo.getAlpha2Code().toLowerCase()+".png");



        }
    }
    private class GetFlag extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                java.net.URL url = new java.net.URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);

                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            flag.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CountryInfo {
        private String name;
        private String region;
        private String alpha3Code;
        private String alpha2Code;
        private String capital;
        private int population;
        private double area;
        private String currency;
        private String languages;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getAlpha2Code() {
            return alpha2Code;
        }

        public void setAlpha2Code(String alpha2Code) {
            this.alpha2Code = alpha2Code;
        }

        public String getLanguages() {
            return languages;
        }

        public void setLanguages(String languages) {
            this.languages = languages;
        }

        public void setCurrency(String currency) {this.currency = currency;}

        public String getCurrency() {return currency;}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlpha3Code() {
            return alpha3Code;
        }

        public void setAlpha3Code(String alpha3Code) {
            this.alpha3Code = alpha3Code;
        }

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public double getArea() {
            return area;
        }

        public void setArea(double area) {
            this.area = area;
        }
    }
}
