package com.example.jamesluo.donationtracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast

class LoginSuccess : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)
        val logout = findViewById<View>(R.id.logout) as Button
        Toast.makeText(applicationContext, "welcome " + intent.getStringExtra("username"), Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext, "uid in ls: " + intent.getStringExtra("pw"), Toast.LENGTH_SHORT).show()
        logout.setOnClickListener {
            val i = Intent(this@LoginSuccess, MainActivity::class.java)
            startActivity(i)
        }
        try {
            val aMgr = assets
            val ipStream = aMgr.open("LocationData.csv")
            Model.buildLocationCSV(ipStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        for (l in Model.getLocations())
            Log.d("-----------------", l.location!!["Longitude"])
        //ArrayList<Location> location = Model.buildLocationXLSX("./LocationData.xlsx");

        val locationData = findViewById<View>(R.id.location) as Button
        locationData.setOnClickListener {
            val i = Intent(this@LoginSuccess, Locations::class.java)
            i.putExtra("username", intent.getStringExtra("username"))
            i.putExtra("pw", intent.getStringExtra("pw"))
            startActivity(i)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@LoginSuccess, Login::class.java)
        startActivity(intent)
    }
}
