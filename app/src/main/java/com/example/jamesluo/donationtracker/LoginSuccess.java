package com.example.jamesluo.donationtracker;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

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
        Button addphoto = (Button) findViewById(R.id.add_photo);
        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1234:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                }
        }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginSuccess.this, Login.class);
        startActivity(intent);
    }
}
