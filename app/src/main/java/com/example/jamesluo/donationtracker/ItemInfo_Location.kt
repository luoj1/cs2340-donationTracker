package com.example.jamesluo.donationtracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView

/**
 * Created by jamesluo on 10/23/18.
 */

class ItemInfo_Location : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info_location)
        val location_name = findViewById(R.id.item_location) as TextView
        val value = findViewById(R.id.item_value) as TextView
        val category = findViewById(R.id.item_category) as TextView
        val itemName = findViewById(R.id.item_name) as TextView
        val fullDescription = findViewById(R.id.item_fullDescription) as TextView
        val timestamp = findViewById(R.id.item_timestamp) as TextView
        val item_location = "Location of Donation: " + intent.getStringExtra("Location")
        val item_timestamp = "Time stamp of Donation: " + intent.getStringExtra("Timestamp")
        val item_name = "Item Name: " + intent.getStringExtra("ItemName")
        val item_fullDescription = "Full Description: " + intent.getStringExtra("FullDescription")
        val item_value = "Value: $" + intent.getStringExtra("Value")
        val item_category = "Category: " + intent.getStringExtra("Category")
        location_name.text = item_location
        value.text = item_value
        category.text = item_category
        itemName.text = item_name
        fullDescription.text = item_fullDescription
        timestamp.text = item_timestamp

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val `in` = Intent(this@ItemInfo_Location, Locations::class.java)
        `in`.putExtra("username", intent.getStringExtra("username"))
        `in`.putExtra("pw", intent.getStringExtra("pw"))
        startActivity(`in`)
    }
}
