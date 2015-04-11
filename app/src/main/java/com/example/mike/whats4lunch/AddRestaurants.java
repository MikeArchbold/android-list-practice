package com.example.mike.whats4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mike on 3/8/15.
 * Add or remove restaurants from the list
 */

//Array of options --> ArrayAdapter --> ListView
//List view: {views: restaurantsList.xml}
public class AddRestaurants extends ActionBarActivity{
    //updating the add restaurants class from linux in android studio
    private static final String TAG = "com.example.mike";
    Button btnAddRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurants);

        btnAddRestaurant = (Button) findViewById(R.id.btnAddRestaurants);
        populateListView();
        registerClickCallback();
    }

    private void populateListView() {
        //Create the list of item
        //String[] myRestaurants = {"Blue", "Green", "Purple", "Red"};

        //get intent data passed from Main
        ArrayList<String> myRestaurants = new ArrayList();
        Intent intent = this.getIntent();
        myRestaurants = intent.getStringArrayListExtra("restaurantsList");

        String listString = "";
        for (String s : myRestaurants){
            listString += s + "\t";
        }

        Log.i(TAG, listString);
        //Intent intent = getIntent();
        //myRestaurants = intent.getStringArrayListExtra("restaurantsList");

        //build the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.restaurant_list, myRestaurants);

        //configure the list view
        ListView restaurantList = (ListView) findViewById(R.id.restaurantsListView);
        restaurantList.setAdapter(adapter);
    }

    private void registerClickCallback(){
        ListView restaurantList = (ListView) findViewById(R.id.restaurantsListView);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(AddRestaurants.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
