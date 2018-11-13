package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamesluo on 10/23/18.
 */

public class AddItem extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);
        final EditText timestamp = (EditText) findViewById(R.id.timestamp);
        final EditText itemName = (EditText) findViewById(R.id.item_name);
        final EditText fullDescription = (EditText) findViewById(R.id.full_description);
        final EditText value = (EditText) findViewById(R.id.value);
        final String locationOfDonation = getIntent().getStringExtra("Location of Donation");
        final Spinner category = (Spinner) findViewById(R.id.categoryOfItem);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        Button submit = (Button) findViewById(R.id.add_item_successful);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(AddItem.this, LocationInfo.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                i.putExtra("pw",getIntent().getStringExtra("pw"));
                i.putExtra("Name", getIntent().getStringExtra("Name"));
                i.putExtra("Type", getIntent().getStringExtra("Type"));
                i.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
                i.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
                i.putExtra("Address", getIntent().getStringExtra("Address"));
                i.putExtra("Phone", getIntent().getStringExtra("Phone"));
                Log.d("additem","2");
                String item_location = locationOfDonation;
                String item_timestamp = timestamp.getText().toString();
                String item_name = itemName.getText().toString();
                String item_full = fullDescription.getText().toString();
                String item_value = value.getText().toString();
                String item_category = category.getSelectedItem().toString();
                Map<String,String> item = new HashMap<>();
                item.put("location",locationOfDonation);
                item.put("timestamp",item_timestamp);
                item.put("ItemName",item_name);
                item.put("fullDescription",item_full);
                item.put("value",item_value);
                item.put("category",item_category);
                final Item newItem = new Item(item);
                Log.d("additem","3");
                //Model.setItems(locationOfDonation, newItem);
                ServerModel.addItems(AddItem.this, Locations.class, AddItem.class
                        , item_category
                        ,item_name
                        ,item_full
                        ,item_timestamp
                        ,item_value
                        ,item_location
                        ,getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"));
                Log.d("additem","4");
                startActivity(i);
            }
        });
        Button cancel = (Button) findViewById(R.id.cancel1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddItem.this, LocationInfo.class);
                i.putExtra("username", getIntent().getStringExtra("username"));
                i.putExtra("pw",getIntent().getStringExtra("pw"));
                i.putExtra("Name", getIntent().getStringExtra("Name"));
                i.putExtra("Type", getIntent().getStringExtra("Type"));
                i.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
                i.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
                i.putExtra("Address", getIntent().getStringExtra("Address"));
                i.putExtra("Phone", getIntent().getStringExtra("Phone"));

                startActivity(i);
            }
        });
    }
}
