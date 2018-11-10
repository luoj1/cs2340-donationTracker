package com.example.jamesluo.donationtracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.json.JSONArray
import java.util.*

class Locations : Activity() {
    private inner class SingleLocation {
        internal var name: String? = null
        internal var latitude: String? = null
        internal var longitude: String? = null
        internal var street_addr: String? = null
        internal var city: String? = null
        internal var state: String? = null
        internal var type: String? = null
        internal var phone: String? = null
        internal var website: String? = null
        internal var zip: String? = null
    }

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_location)
        if (intent.getStringExtra("locations") == null) {
            ServerModel.getLocation(this@Locations, Locations::class.java, LoginSuccess::class.java, intent.getStringExtra("username"), intent.getStringExtra("pw"))
        } else {
            val jsonArray: JSONArray
            val list = ArrayList<SingleLocation>()
            var values = arrayOfNulls<String>(1)
            try {
                jsonArray = JSONArray(intent.getStringExtra("locations"))
                values = arrayOfNulls(jsonArray.length())
                for (i in 0 until jsonArray.length()) {
                    val jsonobject = jsonArray.getJSONObject(i)
                    val sl = SingleLocation()
                    val name = jsonobject.getString("name")
                    sl.name = name
                    sl.latitude = jsonobject.getString("latitude")
                    sl.longitude = jsonobject.getString("longitude")
                    sl.street_addr = jsonobject.getString("street_addr")
                    sl.city = jsonobject.getString("city")
                    sl.state = jsonobject.getString("state")
                    sl.type = jsonobject.getString("type")
                    sl.phone = jsonobject.getString("phone")
                    sl.website = jsonobject.getString("website")
                    sl.zip = jsonobject.getString("zip")
                    values[i] = name
                    list.add(sl)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


            val listview = findViewById<View>(R.id.listview) as ListView

            for ((tracker, l) in Model.getLocations().withIndex()) {
                values[tracker] = Model.getLocations()[tracker].location!!["Name"]
            }
            val adapter = ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, values)
            listview.adapter = adapter

            listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                //Toast.makeText(getBaseContext() ,Integer.toString(position) + " selected", Toast.LENGTH_LONG).show();

                val `in` = Intent(this@Locations, LocationInfo::class.java)
                Log.d("position clicked", "" + position)
                `in`.putExtra("username", intent.getStringExtra("username"))
                `in`.putExtra("pw", intent.getStringExtra("pw"))
                `in`.putExtra("Name", list[position].name)
                `in`.putExtra("Type", list[position].type)
                `in`.putExtra("Longitude", list[position].longitude)
                `in`.putExtra("Latitude", list[position].latitude)
                `in`.putExtra("Address", list[position].street_addr)
                `in`.putExtra("Phone", list[position].phone)
                startActivity(`in`)
            }
        }


        val searchResult = findViewById<View>(R.id.searchResult) as ListView
        val nameOfItem = findViewById<View>(R.id.searchName) as EditText
        val searchByName = findViewById<View>(R.id.searchByName) as Button
        searchByName.setOnClickListener {
            //do search and create result listview

            ServerModel.searchItemsByName(this@Locations, ItemInfo_Location::class.java, searchResult, intent.getStringExtra("username"), intent.getStringExtra("pw"), nameOfItem.text.toString())
        }
        val categoryOfItem = findViewById<View>(R.id.searchCategory) as Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Category.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoryOfItem.adapter = adapter
        val searchByCategory = findViewById<View>(R.id.searchByCategory) as Button

        Log.d("locations", "item_list")


        Log.d("locations", "adapter")


        Log.d("locations", "onclick")
        searchResult.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //go to item info
        }
        searchByCategory.setOnClickListener {
            //do search and create result listview

            ServerModel.searchItemsByCategory(this@Locations, ItemInfo_Location::class.java, searchResult,
                    intent.getStringExtra("username"), intent.getStringExtra("pw"),
                    categoryOfItem.selectedItem.toString())
        }

    }

    /*private class Query {
        String from;
        String username;
        String pw;
        String q;
    }
    private class categorySearchUpdater extends AsyncTask<Query,Object,>{

    }*/
    override fun onBackPressed() {
        super.onBackPressed()
        val `in` = Intent(this@Locations, LoginSuccess::class.java)
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


}

internal class MySimpleArrayAdapter(context: Context, private val values: Array<String>) : ArrayAdapter<String>(context, -1, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.location_list_item, parent, false)
        val textView = rowView.findViewById<View>(R.id.firstLine) as TextView
        textView.text = values[position]


        return rowView
    }
}