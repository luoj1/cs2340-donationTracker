package com.example.jamesluo.donationtracker;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        Button logout = (Button) findViewById(R.id.logout);
        Toast.makeText(getApplicationContext(),"welcome "+getIntent().getStringExtra("username"),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"uid in ls: " + getIntent().getStringExtra("pw"),Toast.LENGTH_SHORT).show();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginSuccess.this, MainActivity.class);
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
                Intent i = new Intent(LoginSuccess.this, Locations.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                i.putExtra("pw", getIntent().getStringExtra("pw"));
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginSuccess.this, Login.class);
        startActivity(intent);
    }
}
