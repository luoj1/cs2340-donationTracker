package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jamesluo on 10/10/18.
 */

public class LocationInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);
        TextView location_name = (TextView) findViewById(R.id.location_name);
        TextView location_type = (TextView) findViewById(R.id.location_type);
        TextView longitude = (TextView) findViewById(R.id.longitude);
        TextView latitude = (TextView) findViewById(R.id.latitude);
        TextView address = (TextView) findViewById(R.id.location_address);
        TextView phone = (TextView) findViewById(R.id.location_phone);
        String name = "Name: " + getIntent().getStringExtra("Name");
        String type = "Type: " + getIntent().getStringExtra("Type");
        String location_longitude = "Longitude: " + getIntent().getStringExtra("Longitude");
        String location_latitude = "Latitude: " + getIntent().getStringExtra("Latitude");
        String location_address= "Address: " + getIntent().getStringExtra("Address");
        String location_phone = "Phone number: " + getIntent().getStringExtra("Phone");
        String userId = getIntent().getStringExtra("id");
        Info info = Model.getInfo().get(userId);
        if(info.type .equals("Location Employee")) {
            //set add button visible
        }
        location_name.setText(name);
        location_type.setText(type);
        longitude.setText(location_longitude);
        latitude.setText(location_latitude);
        address.setText(location_address);
        phone.setText(location_phone);
        Button addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LocationInfo.this, AddItem.class);
                startActivity(i);
            }
        });
    }
}
