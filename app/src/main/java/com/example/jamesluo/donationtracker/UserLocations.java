package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by jamesluo on 10/16/18.
 */

public class UserLocations extends Activity {
    //copy paste Locations.java and the xml layout for location
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //setContentView(R.layout.activity_userlocation);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[Model.getInfo().get(getIntent().getStringExtra("id")).donationLocation.size()];
        int tracker =0 ;
        for (Location l : Model.getInfo().get(getIntent().getStringExtra("id")).donationLocation) {
            values[tracker] = Model.getLocations().get(tracker).getLocation().get("Name");
            tracker ++ ;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //Intent in = new Intent(UserLocations.this, UserLocationInfo.class);
                //in.putExtra("Name", Model.getLocations().get(position).getLocation().get("Name"));
                //in.putExtra("Type", Model.getLocations().get(position).getLocation().get("Type"));
                //in.putExtra("Longitude", Model.getLocations().get(position).getLocation().get("Longitude"));
                //in.putExtra("Latitude", Model.getLocations().get(position).getLocation().get("Latitude"));
                //in.putExtra("Address", Model.getLocations().get(position).getLocation().get("Street Address"));
                //in.putExtra("Phone", Model.getLocations().get(position).getLocation().get("Phone"));
                //startActivity(in);
            }
        });
    }


}