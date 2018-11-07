package com.example.jamesluo.donationtracker;

import android.app.Activity;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by jamesluo on 10/10/18.
 */

public class LocationInfo extends Activity {
    private class SingleItem{
        String ItemName;
        String FullDescription;
        String Category;
        String Value;
        String TimeStamp;
        String Location;
    }
    public static String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);


        final TextView location_name = (TextView) findViewById(R.id.location_name);
        final TextView location_type = (TextView) findViewById(R.id.location_type);
        final TextView longitude = (TextView) findViewById(R.id.longitude);
        final TextView latitude = (TextView) findViewById(R.id.latitude);
        final TextView address = (TextView) findViewById(R.id.location_address);
        final TextView phone = (TextView) findViewById(R.id.location_phone);
        final String name = "Name: " + getIntent().getStringExtra("Name");
        Log.d("additem","3");
        final String type = "Type: " + getIntent().getStringExtra("Type");
        final String location_longitude = "Longitude: " + getIntent().getStringExtra("Longitude");
        final String location_latitude = "Latitude: " + getIntent().getStringExtra("Latitude");
        final String location_address= "Address: " + getIntent().getStringExtra("Address");
        final String location_phone = "Phone number: " + getIntent().getStringExtra("Phone");
        final String userId = getIntent().getStringExtra("id");
        Log.d("locinfo","4");
        Info info = Model.getInfo().get(userId);

        Button addItem = (Button) findViewById(R.id.add_item);
        addItem.setVisibility(View.INVISIBLE);

        ServerModel.buttonVisibility(addItem
                ,getIntent().getStringExtra("username")
                ,getIntent().getStringExtra("pw"));

        location_name.setText(name);
        location_type.setText(type);
        longitude.setText(location_longitude);
        latitude.setText(location_latitude);
        address.setText(location_address);
        phone.setText(location_phone);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationInfo.this, AddItem.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                i.putExtra("Location of Donation",getIntent().getStringExtra("Name"));
                i.putExtra("pw", getIntent().getStringExtra("pw"));
                i.putExtra("Name", getIntent().getStringExtra("Name"));
                i.putExtra("Type", getIntent().getStringExtra("Type"));
                i.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
                i.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
                i.putExtra("Address", getIntent().getStringExtra("Address"));
                i.putExtra("Phone", getIntent().getStringExtra("Phone"));
                startActivity(i);
            }
        });
        Log.d("beginresolve itemview","");
        /*final ListView listview = (ListView) findViewById(R.id.itemlist);
        if (Model.getItems(name) != null) {
            String[] values = new String[Model.getItems(name).size()];
            int tracker =0;
            for (Item i : Model.getItems(name)) {
                values[tracker] = Model.getItems(name).get(tracker).getItem().get("ItemName");
                tracker ++ ;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, values);
            listview.setAdapter(adapter);
        }
        Log.d("finishresolve itemview","");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //change to item
                Intent in = new Intent(LocationInfo.this, ItemInfo.class);
                in.putExtra("Location", Model.getItems(name).get(position).getItem().get("location"));
                in.putExtra("Timestamp", Model.getItems(name).get(position).getItem().get("timestamp"));
                in.putExtra("ItemName", Model.getItems(name).get(position).getItem().get("ItemName"));
                in.putExtra("FullDescription", Model.getItems(name).get(position).getItem().get("fullDescription"));
                in.putExtra("Value", Model.getItems(name).get(position).getItem().get("value"));
                in.putExtra("Category", Model.getItems(name).get(position).getItem().get("category"));
                in.putExtra("username", getIntent().getStringExtra("username"));
                in.putExtra("pw", getIntent().getStringExtra("pw"));
                in.putExtra("Name", getIntent().getStringExtra("Name"));
                in.putExtra("Type", getIntent().getStringExtra("Type"));
                in.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
                in.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
                in.putExtra("Address", getIntent().getStringExtra("Address"));
                in.putExtra("Phone", getIntent().getStringExtra("Phone"));
                startActivity(in);
            }
        });*/

        final ListView searchResult = (ListView) findViewById(R.id.searchResult);
        final EditText nameOfItem = (EditText) findViewById(R.id.searchName);
        String searchName = nameOfItem.toString();
        Button searchByName = (Button) findViewById(R.id.searchByName);


        final ArrayList<SingleItem> items_list = new ArrayList<>();
        final String[] items_displays =  new String[1];


        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerModel.searchItemsByNameLoc(LocationInfo.this, ItemInfo.class, searchResult, getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"), nameOfItem.getText().toString(),
                        getIntent().getStringExtra("Name"), getIntent().getStringExtra("Type"), getIntent().getStringExtra("Longitude"), getIntent().getStringExtra("Latitude"),
                        getIntent().getStringExtra("Phone"), getIntent().getStringExtra("Address"));


            }
        });
        final Spinner categoryOfItem = (Spinner) findViewById(R.id.searchCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryOfItem.setAdapter(adapter);
        Button searchByCategory = (Button) findViewById(R.id.searchByCategory);
        searchByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerModel.searchItemsByCategoryLoc(LocationInfo.this,ItemInfo.class, searchResult,
                        getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"),
                        categoryOfItem.getSelectedItem().toString(),getIntent().getStringExtra("Name"), getIntent().getStringExtra("Type"), getIntent().getStringExtra("Longitude"), getIntent().getStringExtra("Latitude"),
                        getIntent().getStringExtra("Phone"), getIntent().getStringExtra("Address"));
            }
        });
        ServerModel.getItems(LocationInfo.this, ItemInfo.class, searchResult, getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"),
                getIntent().getStringExtra("Name"), getIntent().getStringExtra("Type"), getIntent().getStringExtra("Longitude"), getIntent().getStringExtra("Latitude"),
                getIntent().getStringExtra("Phone"), getIntent().getStringExtra("Address"));
        /*final ListView searchResult = (ListView) findViewById(R.id.searchResult);
        int tracker =0 ;
        for (Location l : Model.getLocations()) {
            items_displays[tracker] = Model.getLocations().get(tracker).getLocation().get("Name");
            tracker ++ ;
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items_displays);
        searchResult.setAdapter(adapter2);

        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //go to item info


            }
        });*/


    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent in=new Intent(LocationInfo.this, Locations.class);
        in.putExtra("pw", getIntent().getStringExtra("pw"));
        in.putExtra("username", getIntent().getStringExtra("username"));
        in.putExtra("Name", getIntent().getStringExtra("Name"));
        in.putExtra("Type", getIntent().getStringExtra("Type"));
        in.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
        in.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
        in.putExtra("Address", getIntent().getStringExtra("Address"));
        in.putExtra("Phone", getIntent().getStringExtra("Phone"));
        startActivity(in);
    }

}
