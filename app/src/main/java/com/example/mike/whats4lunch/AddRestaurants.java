package com.example.mike.whats4lunch;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mike on 3/8/15.
 * Add or remove restaurants from the list
 */

//Array of options --> ArrayAdapter --> ListView
//List view: {views: restaurantsList.xml}
public class AddRestaurants extends ActionBarActivity{
    //updating the add restaurants class from linux in android studio
    int deleteCounter = 3;
    String clickedItem = "";
    private static final String TAG = "com.example.mike";
    ArrayList<String> myRestaurants;
    ArrayAdapter<String> adapter;
    Button btnAddRestaurant;
    ListView restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_restaurants);

        //will hide the input box when activity is started
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnAddRestaurant = (Button) findViewById(R.id.btnAddRestaurants);
        btnAddRestaurant.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText restaurantET = (EditText) findViewById(R.id.restaurantET);
                String restaurantInput = restaurantET.getText().toString();

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
                    restaurantList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        Button btnFinish = (Button) findViewById(R.id.btnFinish);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take the current array from list
                String[] array = getStringArray(restaurantList.getAdapter());
                ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));
                Log.d("test", "1: " +  arrayList.toString());

                //must pass intent and then start activity
                Intent intent = new Intent(AddRestaurants.this, MainActivity.class);
                intent.putStringArrayListExtra("test", myRestaurants);
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

    public void populateListView() {
        //Create the list of item
        //String[] myRestaurants = {"Blue", "Green", "Purple", "Red"};

        //get intent data passed from Main
        //ArrayList<String> myRestaurants = new ArrayList();
        Intent intent = getIntent();
        myRestaurants = intent.getExtras().getStringArrayList("restaurantsList");

        String listString = "";
        for (String s : myRestaurants){
            listString += s + "\t";
        }

        Log.i(TAG, listString);

        //build the adapter
        adapter = new ArrayAdapter<String>(this, R.layout.restaurant_list, myRestaurants);

        //configure the list view
        restaurantList = (ListView) findViewById(R.id.restaurantsListView);
        restaurantList.setAdapter(adapter);
    }

    public void registerClickCallback(){
        restaurantList = (ListView) findViewById(R.id.restaurantsListView);

        restaurantList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                /*Delete counter keeps track of how many times item has been clicked in a row
                  Click an item three times in a row to delete*/
                TextView textView = (TextView) viewClicked;
                if(clickedItem.equals(textView.getText().toString()))
                    deleteCounter--;
                else {
                    deleteCounter = 2;
                    clickedItem = textView.getText().toString();
                }

                if(deleteCounter>0){
                    String message = "Click " + clickedItem + " " +
                            Integer.toString(deleteCounter) + " more times to delete";
                    Toast.makeText(AddRestaurants.this, message, Toast.LENGTH_LONG).show();
                }
                else{
                    adapter.remove(clickedItem);
                    restaurantList.setAdapter(adapter);
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
