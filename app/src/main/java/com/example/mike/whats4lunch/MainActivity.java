package com.example.mike.whats4lunch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends ActionBarActivity {

    int randomInt;
    ArrayList<String> myRestaurants;

    Random randomNumberGenerator = new Random();
    Button btnRandom;
    TextView whatsForLunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(getIntent().getExtras() != null){
            Intent intent = getIntent();
            myRestaurants = intent.getExtras().getStringArrayList("test");
            Log.d("restart", "3: " + myRestaurants.toString());
        }
        else {
            myRestaurants = new ArrayList();
            myRestaurants.add("Thai");
            myRestaurants.add("Bobby's Burgers");
            Log.d("test", "4: " + myRestaurants.toString());
            /*myRestaurants.add("Wild Willy's");
            myRestaurants.add("Sushi");
            myRestaurants.add("Wings Over");
            myRestaurants.add("test");
            myRestaurants.add("more test");
            myRestaurants.add("is it over");
            */
        }

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
                Log.d("test", myRestaurants.toString());
                int numberRestaurants = myRestaurants.size();
                randomInt = randomNumberGenerator.nextInt(numberRestaurants);
                whatsForLunch.setText(myRestaurants.get(randomInt));//[randomInt]);
            }
        });
    }

    private String getSavedRestaurants(){
        SharedPreferences prefs = getSharedPreferences("Save Restaurants", MODE_PRIVATE);
        Set<String> restaurantSet = new HashSet<String>(prefs.getStringSet("Save Restaurants", new HashSet<String>()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.addRestaurantsSetting:
                Intent intent = new Intent(MainActivity.this, AddRestaurants.class);
                intent.putStringArrayListExtra("restaurantsList", myRestaurants);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
