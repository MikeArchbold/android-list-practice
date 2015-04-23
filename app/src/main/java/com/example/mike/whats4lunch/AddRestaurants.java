package com.example.mike.whats4lunch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mike on 3/8/15.
 * Add or remove restaurants from the list
 */

//Array of options --> ArrayAdapter --> ListView
//List view: {views: restaurantsList.xml}
public class AddRestaurants extends ActionBarActivity{

    int deleteCounter = 3;
    String clickedItem = "";
    ArrayAdapter<String> adapter;
    Button btnAddRestaurant;
    ListView restaurantListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurants);

        btnAddRestaurant = (Button) findViewById(R.id.btnAddRestaurants);
        btnAddRestaurant.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText restaurantET = (EditText) findViewById(R.id.restaurantET);
                String restaurantInput = restaurantET.getText().toString();

                //checks to see if item already exists
                boolean alreadyExists = false;
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (restaurantInput.toLowerCase().equals(adapter.getItem(i).toLowerCase()))
                        alreadyExists = true;
                }

                if (alreadyExists) {
                    String message = new String(restaurantInput + " is already in the list");
                    Toast.makeText(AddRestaurants.this, message, Toast.LENGTH_LONG).show();
                } else {
                    adapter.add(restaurantET.getText().toString());
                    restaurantListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button btnFinish = (Button) findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take the current array from list
                String[] array = getStringArray(restaurantListView.getAdapter());
                Set<String> set = new HashSet<>(Arrays.asList(array));
                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(array));
                setSavedPreferences(arrayList);

                Log.d("test", "1: " +  arrayList.toString());

                //must pass intent and then start activity
                Intent intent = new Intent(AddRestaurants.this, MainActivity.class);
                startActivity(intent);
            }
        });

        populateListView();
        registerClickCallback();

        //on click listener for edit text
        //needs to not be obscured by soft keyboard when clicked
        final EditText restaurantET = (EditText) findViewById(R.id.restaurantET);
        restaurantET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                restaurantET.requestLayout();
                AddRestaurants.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
                return false;
            }
        });
    }

    public void setSavedPreferences(ArrayList<String> arrayList){
        Set<String> set = new HashSet<String>();
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
        Set<String> extractedSet = prefs.getStringSet("myKey", new HashSet<>(Arrays.asList("it's empty")));
        Log.d("test", "Well the string set  is" + extractedSet.toString());
        ArrayList<String> extractedList = new ArrayList<String>(extractedSet);
        return extractedList;
    }

    //place holder
    public void populateListView() {
        ArrayList<String> restaurantList = getSavedPreferences();

        //build the adapter
        adapter = new ArrayAdapter<>(this, R.layout.restaurant_list, restaurantList);

        //configure the list view
        restaurantListView = (ListView) findViewById(R.id.restaurantsListView);
        restaurantListView.setAdapter(adapter);
    }

    //keeps track of counter to delete items in the list
    public void registerClickCallback(){
        restaurantListView = (ListView) findViewById(R.id.restaurantsListView);

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                /*Delete counter keeps track of how many times item has been clicked in a row
                  Click an item three times in a row to delete*/
                TextView textView = (TextView) viewClicked;

                //subtract or reset counter
                if(clickedItem.equals(textView.getText().toString()))
                    deleteCounter--;
                else {
                    deleteCounter = 2;
                    clickedItem = textView.getText().toString();
                }

                //warn user of deletion and if 0 delete
                if(deleteCounter>0){
                    String message = "Click " + clickedItem + " " +
                            Integer.toString(deleteCounter) + " more times to delete";
                    Toast.makeText(AddRestaurants.this, message, Toast.LENGTH_LONG).show();
                }
                else{
                    adapter.remove(clickedItem);
                    restaurantListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    String message = clickedItem + " deleted";
                    Toast.makeText(AddRestaurants.this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String[] getStringArray(ListAdapter adapter){
        String[] a = new String[adapter.getCount()];

        for(int i=0; i<a.length; i++)
            a[i] = adapter.getItem(i).toString();

        return a;
    }
}
