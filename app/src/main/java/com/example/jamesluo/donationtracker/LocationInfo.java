package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by jamesluo on 10/10/18.
 */

public class LocationInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);
        Toast.makeText(getApplicationContext(),getIntent().getStringExtra("Name")+getIntent().getStringExtra("Phone"), Toast.LENGTH_LONG).show();


    }
}
