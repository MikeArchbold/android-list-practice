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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends ActionBarActivity {

    int randomInt;
    ArrayList<String> myRestaurantList;
    final String emptyListFiller = "Add new restaurants from options";
    final String defaultText = "Whats 4 Luuunch?!";
    Random randomNumberGenerator = new Random();
    Button btnRandom;
    TextView whatsForLunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myRestaurantList = getSavedPreferences();

        //check if this is the first time starting app
        if(myRestaurantList.get(0).equals("[it's empty]")) {
            myRestaurantList = new ArrayList<>();
            myRestaurantList.add(emptyListFiller);
            Log.d("test", "First time app is running loading defaults");
            setSavedPreferences(myRestaurantList);
       }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addRandomButton(myRestaurantList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void addRandomButton(final List<String> myRestaurantList){
        btnRandom = (Button) findViewById(R.id.btnRandom);
        whatsForLunch = (TextView) findViewById(R.id.whatsForLunch);

        btnRandom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0){
                Log.d("test", myRestaurantList.toString());
                int numberRestaurants = myRestaurantList.size();
                randomInt = randomNumberGenerator.nextInt(numberRestaurants);
                whatsForLunch.setText(myRestaurantList.get(randomInt).toString());//[randomInt]);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.addRestaurantsSetting:
                Log.d("test", "add restaurant settings");
                Intent intent = new Intent(MainActivity.this, AddRestaurants.class);
                //removing intent extra and replacing with saved preference
                intent.putStringArrayListExtra("restaurantsList", myRestaurantList);
                startActivity(intent);
                break;
            //clear all data and reset text view value
            case R.id.clearAllData:
                Log.d("test", "clear all data");
                myRestaurantList.clear();
                myRestaurantList.add(emptyListFiller);
                whatsForLunch = (TextView) findViewById(R.id.whatsForLunch);
                whatsForLunch.setText(defaultText);
                setSavedPreferences(myRestaurantList);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSavedPreferences(ArrayList<String> arrayList){
        Set<String> set = new HashSet<>();
        set.addAll(arrayList);
        Log.d("test", "The arrayList " + arrayList);
        Log.d("test", "The set " + set);
        //get shared pref and set it equal to null(clear it)
        //then commit changes that have been passed
        SharedPreferences prefs = getSharedPreferences("Saved Restaurants", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet("myKey", set);
        editor.commit();
    }

    public ArrayList<String> getSavedPreferences(){
        SharedPreferences prefs = getSharedPreferences("Saved Restaurants", MODE_PRIVATE);
        Set<String> extractedSet = prefs.getStringSet("myKey", new HashSet<String>(Arrays.asList("it's empty")));
        Log.d("test", "Well the string set  is" + extractedSet.toString());
        ArrayList<String> extractedList = new ArrayList<String>(extractedSet);
        return extractedList;
    }
}
