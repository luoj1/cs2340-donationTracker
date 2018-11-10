package com.example.jamesluo.donationtracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

import java.util.HashMap

/**
 * Created by jamesluo on 10/23/18.
 */

class AddItem : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)
        val timestamp = findViewById<View>(R.id.timestamp) as EditText
        val itemName = findViewById<View>(R.id.item_name) as EditText
        val fullDescription = findViewById<View>(R.id.full_description) as EditText
        val value = findViewById<View>(R.id.value) as EditText
        val locationOfDonation = intent.getStringExtra("Location of Donation")
        val category = findViewById<View>(R.id.categoryOfItem) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Category.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        category.adapter = adapter
        val submit = findViewById<View>(R.id.add_item_successful) as Button


        submit.setOnClickListener {
            val i = Intent(this@AddItem, LocationInfo::class.java)
            i.putExtra("username", intent.getStringExtra("username"))
            i.putExtra("pw", intent.getStringExtra("pw"))
            i.putExtra("Name", intent.getStringExtra("Name"))
            i.putExtra("Type", intent.getStringExtra("Type"))
            i.putExtra("Longitude", intent.getStringExtra("Longitude"))
            i.putExtra("Latitude", intent.getStringExtra("Latitude"))
            i.putExtra("Address", intent.getStringExtra("Address"))
            i.putExtra("Phone", intent.getStringExtra("Phone"))
            Log.d("additem", "2")
            val item_timestamp = timestamp.text.toString()
            val item_name = itemName.text.toString()
            val item_full = fullDescription.text.toString()
            val item_value = value.text.toString()
            val item_category = category.selectedItem.toString()
            val item = HashMap<String, String>()
            item["location"] = locationOfDonation
            item["timestamp"] = item_timestamp
            item["ItemName"] = item_name
            item["fullDescription"] = item_full
            item["value"] = item_value
            item["category"] = item_category
            Log.d("additem", "3")
            //Model.setItems(locationOfDonation, newItem);
            ServerModel.addItems(this@AddItem, Locations::class.java, AddItem::class.java, item_category, item_name, item_full, item_timestamp, item_value, locationOfDonation, intent.getStringExtra("username"), intent.getStringExtra("pw"))
            Log.d("additem", "4")
            startActivity(i)
        }
        val cancel = findViewById<View>(R.id.cancel1) as Button
        cancel.setOnClickListener {
            val i = Intent(this@AddItem, LocationInfo::class.java)
            i.putExtra("username", intent.getStringExtra("username"))
            i.putExtra("pw", intent.getStringExtra("pw"))
            i.putExtra("Name", intent.getStringExtra("Name"))
            i.putExtra("Type", intent.getStringExtra("Type"))
            i.putExtra("Longitude", intent.getStringExtra("Longitude"))
            i.putExtra("Latitude", intent.getStringExtra("Latitude"))
            i.putExtra("Address", intent.getStringExtra("Address"))
            i.putExtra("Phone", intent.getStringExtra("Phone"))

            startActivity(i)
        }
    }
}
