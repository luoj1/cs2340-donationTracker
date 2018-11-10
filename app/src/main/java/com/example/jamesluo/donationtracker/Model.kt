package com.example.jamesluo.donationtracker


import android.util.Log
import org.apache.commons.csv.CSVFormat
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * Created by jamesluo on 9/20/18.
 */

object Model {
    //id: email
    //name: username
    //type: account type
    private val auth = HashMap<String, String>()
    //private static ArrayList<Item> items = new ArrayList<>();

    val info = HashMap<String, Info>()
    private val items = HashMap<String, List<Item>>()
    private var locations = ArrayList<Location>()
    fun getLocations(): List<Location> {
        return locations
    }

    fun getItems(s: String): List<Item>? {
        return if (items.containsKey(s)) items[s] else null
    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun buildLocationCSV(ins: InputStream) {
        //TODO init location array
        locations = ArrayList()
        val `in` = InputStreamReader(ins, "UTF-8")

        val data = CSVFormat.DEFAULT.withHeader().parse(`in`)
        for ((key, csvRecord) in data.withIndex()) {
            val locationData = csvRecord.toMap()
            Log.d("-----------------", locationData.keys.toString())

            val location = Location(locationData, key)
            locations.add(key, location)
        }
    }


    fun verify(u: String, p: String): Boolean {
        return (auth.containsKey(u) && auth[u] == p)}

    operator fun contains(u: String): Boolean {
        return auth.containsKey(u)
    }

    fun addUser(user: String, pwd: String, type: String, id: String) {
        //email as id
        auth[id] = pwd
        info[id] = Info(user, type, id)
    }


}

class Info(var name: String, var type: String, var id: String)

class Location(var location: Map<String, String>?, var key: Int)

class Item(var item: Map<String, String>?)