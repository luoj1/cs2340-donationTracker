package com.example.jamesluo.donationtracker


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

/**
 * Created by jamesluo on 10/10/18.
 */

class LocationInfo : Activity() {
    private inner class SingleItem {
        internal var ItemName: String? = null
        internal var FullDescription: String? = null
        internal var Category: String? = null
        internal var Value: String? = null
        internal var TimeStamp: String? = null
        internal var Location: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_info)


        val location_name = findViewById<View>(R.id.location_name) as TextView
        val location_type = findViewById<View>(R.id.location_type) as TextView
        val longitude = findViewById<View>(R.id.longitude) as TextView
        val latitude = findViewById<View>(R.id.latitude) as TextView
        val address = findViewById<View>(R.id.location_address) as TextView
        val phone = findViewById<View>(R.id.location_phone) as TextView
        val name = "Name: " + intent.getStringExtra("Name")
        Log.d("additem", "3")
        val type = "Type: " + intent.getStringExtra("Type")
        val location_longitude = "Longitude: " + intent.getStringExtra("Longitude")
        val location_latitude = "Latitude: " + intent.getStringExtra("Latitude")
        val location_address = "Address: " + intent.getStringExtra("Address")
        val location_phone = "Phone number: " + intent.getStringExtra("Phone")
        val userId = intent.getStringExtra("id")
        Log.d("locinfo", "4")

        val addItem = findViewById<View>(R.id.add_item) as Button
        addItem.visibility = View.INVISIBLE

        ServerModel.buttonVisibility(addItem, intent.getStringExtra("username"), intent.getStringExtra("pw"))

        location_name.text = name
        location_type.text = type
        longitude.text = location_longitude
        latitude.text = location_latitude
        address.text = location_address
        phone.text = location_phone

        addItem.setOnClickListener {
            val i = Intent(this@LocationInfo, AddItem::class.java)
            i.putExtra("username", intent.getStringExtra("username"))
            i.putExtra("Location of Donation", intent.getStringExtra("Name"))
            i.putExtra("pw", intent.getStringExtra("pw"))
            i.putExtra("Name", intent.getStringExtra("Name"))
            i.putExtra("Type", intent.getStringExtra("Type"))
            i.putExtra("Longitude", intent.getStringExtra("Longitude"))
            i.putExtra("Latitude", intent.getStringExtra("Latitude"))
            i.putExtra("Address", intent.getStringExtra("Address"))
            i.putExtra("Phone", intent.getStringExtra("Phone"))
            startActivity(i)
        }
        Log.d("beginresolve itemview", "")
        /*final ListView listview = (ListView) findViewById(R.id.itemlist);
        if (Model.getItems(name) != null) {
            String[] values = new String[Model.getItems(name).size()];
            int tracker =0;
            for (Item i : Model.getItems(name)) {
                values[tracker] = Model.getItems(name).get(tracker).getItem().get("ItemName");
                tracker ++ ;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, values);
            listview.setAdapter(adapter);
        }
        Log.d("finishresolve itemview","");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //change to item
                Intent in = new Intent(LocationInfo.this, ItemInfo.class);
                in.putExtra("Location", Model.getItems(name).get(position).getItem().get("location"));
                in.putExtra("Timestamp", Model.getItems(name).get(position).getItem().get("timestamp"));
                in.putExtra("ItemName", Model.getItems(name).get(position).getItem().get("ItemName"));
                in.putExtra("FullDescription", Model.getItems(name).get(position).getItem().get("fullDescription"));
                in.putExtra("Value", Model.getItems(name).get(position).getItem().get("value"));
                in.putExtra("Category", Model.getItems(name).get(position).getItem().get("category"));
                in.putExtra("username", getIntent().getStringExtra("username"));
                in.putExtra("pw", getIntent().getStringExtra("pw"));
                in.putExtra("Name", getIntent().getStringExtra("Name"));
                in.putExtra("Type", getIntent().getStringExtra("Type"));
                in.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
                in.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
                in.putExtra("Address", getIntent().getStringExtra("Address"));
                in.putExtra("Phone", getIntent().getStringExtra("Phone"));
                startActivity(in);
            }
        });*/

        val searchResult = findViewById<View>(R.id.searchResult) as ListView
        val nameOfItem = findViewById<View>(R.id.searchName) as EditText
        val searchByName = findViewById<View>(R.id.searchByName) as Button


        searchByName.setOnClickListener {
            ServerModel.searchItemsByNameLoc(this@LocationInfo, ItemInfo::class.java, searchResult, intent.getStringExtra("username"), intent.getStringExtra("pw"), nameOfItem.text.toString(),
                    intent.getStringExtra("Name"), intent.getStringExtra("Type"), intent.getStringExtra("Longitude"), intent.getStringExtra("Latitude"),
                    intent.getStringExtra("Phone"), intent.getStringExtra("Address"))
        }
        val categoryOfItem = findViewById<View>(R.id.searchCategory) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Category.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoryOfItem.adapter = adapter
        val searchByCategory = findViewById<View>(R.id.searchByCategory) as Button
        searchByCategory.setOnClickListener {
            ServerModel.searchItemsByCategoryLoc(this@LocationInfo, ItemInfo::class.java, searchResult,
                    intent.getStringExtra("username"), intent.getStringExtra("pw"),
                    categoryOfItem.selectedItem.toString(), intent.getStringExtra("Name"), intent.getStringExtra("Type"), intent.getStringExtra("Longitude"), intent.getStringExtra("Latitude"),
                    intent.getStringExtra("Phone"), intent.getStringExtra("Address"))
        }
        ServerModel.getItems(this@LocationInfo, ItemInfo::class.java, searchResult, intent.getStringExtra("username"), intent.getStringExtra("pw"),
                intent.getStringExtra("Name"), intent.getStringExtra("Type"), intent.getStringExtra("Longitude"), intent.getStringExtra("Latitude"),
                intent.getStringExtra("Phone"), intent.getStringExtra("Address"))
        /*final ListView searchResult = (ListView) findViewById(R.id.searchResult);
        int tracker =0 ;
        for (Location l : Model.getLocations()) {
            items_displays[tracker] = Model.getLocations().get(tracker).getLocation().get("Name");
            tracker ++ ;
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items_displays);
        searchResult.setAdapter(adapter2);

        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //go to item info


            }
        });*/


    }

    override fun onBackPressed() {
        super.onBackPressed()
        val `in` = Intent(this@LocationInfo, Locations::class.java)
        `in`.putExtra("pw", intent.getStringExtra("pw"))
        `in`.putExtra("username", intent.getStringExtra("username"))
        `in`.putExtra("Name", intent.getStringExtra("Name"))
        `in`.putExtra("Type", intent.getStringExtra("Type"))
        `in`.putExtra("Longitude", intent.getStringExtra("Longitude"))
        `in`.putExtra("Latitude", intent.getStringExtra("Latitude"))
        `in`.putExtra("Address", intent.getStringExtra("Address"))
        `in`.putExtra("Phone", intent.getStringExtra("Phone"))
        startActivity(`in`)
    }

    companion object {
        var location: String? = null
    }

}
