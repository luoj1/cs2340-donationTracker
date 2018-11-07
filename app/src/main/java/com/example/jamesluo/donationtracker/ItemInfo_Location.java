package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by jamesluo on 10/23/18.
 */

public class ItemInfo_Location extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info_location);
        TextView location_name = (TextView) findViewById(R.id.item_location);
        TextView value = (TextView) findViewById(R.id.item_value);
        TextView category = (TextView) findViewById(R.id.item_category);
        TextView itemName = (TextView) findViewById(R.id.item_name);
        TextView fullDescription = (TextView) findViewById(R.id.item_fullDescription);
        TextView timestamp = (TextView) findViewById(R.id.item_timestamp);
        String item_location = "Location of Donation: " + getIntent().getStringExtra("Location");
        String item_timestamp = "Time stamp of Donation: " + getIntent().getStringExtra("Timestamp");
        String item_name = "Item Name: " + getIntent().getStringExtra("ItemName");
        String item_fullDescription = "Full Description: " + getIntent().getStringExtra("FullDescription");
        String item_value = "Value: $" + getIntent().getStringExtra("Value");
        String item_category = "Category: " + getIntent().getStringExtra("Category");
        location_name.setText(item_location);
        value.setText(item_value);
        category.setText(item_category);
        itemName.setText(item_name);
        fullDescription.setText(item_fullDescription);
        timestamp.setText(item_timestamp);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent in=new Intent(ItemInfo_Location.this, Locations.class);
        in.putExtra("username", getIntent().getStringExtra("username"));
        in.putExtra("pw", getIntent().getStringExtra("pw"));
        startActivity(in);
    }
}
