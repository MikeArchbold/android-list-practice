package com.example.mike.whats4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    int randomInt;
    ArrayList<String> restaurants;
    Random randomNumberGenerator = new Random();

    Button btnRandom;
    TextView whatsForLunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int totalRestaurants = 5;
        restaurants = new ArrayList();
        restaurants.add("Thai");
        restaurants.add("Bobby's Burgers");
        restaurants.add("Wild Willy's");
        restaurants.add("Sushi");
        restaurants.add("Wings Over");

        Intent intent = getIntent();
        intent.putStringArrayListExtra("restaurantsList", restaurants);
        //intent.putStringArrayListExtra("restaurantsList", restaurants);
        //startService(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addRandomButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addRandomButton(){
        btnRandom = (Button) findViewById(R.id.btnRandom);
        whatsForLunch = (TextView) findViewById(R.id.whatsForLunch);

        btnRandom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                randomInt = randomNumberGenerator.nextInt(4);
                whatsForLunch.setText(restaurants.get(randomInt));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent settingsIntent;

        switch(item.getItemId()){
            case R.id.addRestaurantsSetting:
                settingsIntent = new Intent(getApplicationContext(), AddRestaurants.class);
                startActivity(settingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
