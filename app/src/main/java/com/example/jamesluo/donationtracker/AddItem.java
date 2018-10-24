package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        final EditText shortDescription = (EditText) findViewById(R.id.short_description);
        final EditText fullDescription = (EditText) findViewById(R.id.full_description);
        final EditText value = (EditText) findViewById(R.id.value);
        final EditText category = (EditText) findViewById(R.id.category);
        final String locationOfDonation = getIntent().getStringExtra("Location of Donation");
        Button submit = (Button) findViewById(R.id.add_item_successful);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddItem.this, LocationInfo.class);
                String item_timestamp = timestamp.getText().toString();
                String item_short = shortDescription.getText().toString();
                String item_full = fullDescription.getText().toString();
                String item_value = value.getText().toString();
                String item_category = category.getText().toString();
                Map<String,String> item = new HashMap<>();
                item.put("location",locationOfDonation);
                item.put("timestamp",item_timestamp);
                item.put("shortDescription",item_short);
                item.put("fullDescription",item_full);
                item.put("value",item_value);
                item.put("category",item_category);
                final Item newItem = new Item(item);
                Model.setItems(locationOfDonation, newItem);
                startActivity(i);
            }
        });
        Button cancel = (Button) findViewById(R.id.cancel1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddItem.this, LocationInfo.class);
                startActivity(i);
            }
        });
    }
}
