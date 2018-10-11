package com.example.jamesluo.donationtracker;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner userSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                startActivity(i);
            }
        });

        ArrayList<Location> location = null;
        try{
            AssetManager aMgr= getAssets();
            InputStream ipStream = aMgr.open("LocationData.csv");
            Model.buildLocationCSV(ipStream);
        }catch (Exception e) {
            e.printStackTrace();
        }
        for (Location l : Model.getLocations())
            Log.d("-----------------", l.getLocation().get("Longitude"));
        //ArrayList<Location> location = Model.buildLocationXLSX("./LocationData.xlsx");
        Button locationData = (Button) findViewById(R.id.location);
        locationData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Locations.class);
                startActivity(i);
            }
        });
    }
}