package com.example.jamesluo.donationtracker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        AuthModel.initAuth()
        val login = findViewById<View>(R.id.login) as Button
        login.setOnClickListener {
            val i = Intent(this@MainActivity, Login::class.java)
            startActivity(i)
        }
        val register = findViewById<View>(R.id.register) as Button
        register.setOnClickListener {
            val i = Intent(this@MainActivity, Registration::class.java)
            startActivity(i)
        }


    }
}