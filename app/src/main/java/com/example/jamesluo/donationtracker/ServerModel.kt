package com.example.jamesluo.donationtracker

/**
 * Created by jamesluo on 10/25/18.
 */

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.util.*

object ServerModel {
    private const val url = "http://162.243.172.39:8080/"
    private var client: OkHttpClient? = null

    class SingleItem {
        internal var ItemName: String? = null
        internal var FullDescription: String? = null
        internal var Category: String? = null
        internal var Value: String? = null
        internal var TimeStamp: String? = null
        internal var Location: String? = null
    }

    fun initClient() {
        client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()
    }

    fun createNewUserInDB(from: Context, success2: Class<*>,
                          fail2: Class<*>, email: String, name: String, type: String, uid: String) {
        Log.d("serverModel", name)

        val body = FormBody.Builder()
                .add("user", name)
                .add("pw", uid)
                .add("email", email)
                .add("user_type", type)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/register")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail", e.message)
                val intent = Intent(from, fail2)
                from.startActivity(intent)
                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                if (myResponse == "1") {
                    Log.d("success", myResponse)
                    val intent = Intent(from, success2)
                    from.startActivity(intent)
                } else {
                    call.cancel()
                    Log.d("success", myResponse)
                    val intent = Intent(from, fail2)
                    from.startActivity(intent)


                }
            }
        })
    }

    fun getLocation(from: Context, success: Class<*>, fail: Class<*>, username: String, pw: String) {
        Log.d("serverModel", username)

        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/getLocation")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get location", e.message)
                val intent = Intent(from, fail)
                intent.putExtra("username", username)
                intent.putExtra("pw", pw)
                from.startActivity(intent)
                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getlocation response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("success", myResponse)
                    val intent = Intent(from, fail)
                    intent.putExtra("username", username)
                    intent.putExtra("pw", pw)
                    from.startActivity(intent)

                } else {
                    try {

                        Log.d("success", myResponse)

                        val intent = Intent(from, success)
                        intent.putExtra("locations", myResponse)
                        intent.putExtra("username", username)
                        intent.putExtra("pw", pw)
                        from.startActivity(intent)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            }
        })
    }

    fun getItems(from: Context, ItemInfo: Class<*>, searchResult: ListView, username: String, pw: String, location: String,
                 type: String, longitude: String, latitude: String, phone: String, address: String) {
        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("location", location)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/getItems")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get location", e.message)

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getitems response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("0 in items", myResponse)
                    (from as Activity).runOnUiThread {
                        Toast.makeText(from, "empty", Toast.LENGTH_LONG).show()
                        searchResult.adapter = null
                    }

                } else {

                    Log.d("success in items", myResponse)

                    val items_list = itemBuilder(myResponse) ?: return
                    val items_displays = arrayOfNulls<String>(items_list.size)
                    for ((tracker, item) in items_list.withIndex()) {
                        items_displays[tracker] = item.ItemName
                    }
                    Log.d("item display len", "" + items_displays.size)
                    if (items_displays.isNotEmpty()) {
                        val adapter2 = ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays)
                        (from as Activity).runOnUiThread { searchResult.adapter = adapter2 }

                    } else {
                        //search all
                        (from as Activity).runOnUiThread {
                            Toast.makeText(from, "empty", Toast.LENGTH_LONG).show()
                            searchResult.adapter = null
                        }

                    }
                    searchResult.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        val `in` = Intent(from, ItemInfo)
                        `in`.putExtra("Location", items_list[position].Location)
                        `in`.putExtra("Timestamp", items_list[position].TimeStamp)
                        `in`.putExtra("ItemName", items_list[position].ItemName)
                        `in`.putExtra("FullDescription", items_list[position].FullDescription)
                        `in`.putExtra("Value", items_list[position].Value)
                        `in`.putExtra("Category", items_list[position].Category)
                        `in`.putExtra("username", username)
                        `in`.putExtra("pw", pw)
                        `in`.putExtra("Name", location)
                        `in`.putExtra("Type", type)
                        `in`.putExtra("Longitude", longitude)
                        `in`.putExtra("Latitude", latitude)
                        `in`.putExtra("Address", address)
                        `in`.putExtra("Phone", phone)
                        from.startActivity(`in`)
                    }


                }
            }
        })
    }

    fun searchItemsByCategory(from: Context, ItemInfo: Class<*>, searchResult: ListView, username: String, pw: String, category: String) {
        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("category", category)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/searchItemByCategory")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get location", e.message)

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getlocation response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("success", myResponse)
                    (from as Activity).runOnUiThread {
                        Log.d("s cat", "no cat")
                        Toast.makeText(from, "no category matched", Toast.LENGTH_LONG).show()
                        searchResult.adapter = null
                    }
                } else {

                    Log.d("success", myResponse)

                    val items_list = itemBuilder(myResponse) ?: return
                    val items_displays = arrayOfNulls<String>(items_list.size)
                    for ((tracker, item) in items_list.withIndex()) {
                        items_displays[tracker] = item.ItemName
                    }
                    if (items_displays.isNotEmpty()) {
                        val adapter2 = ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays)
                        (from as Activity).runOnUiThread { searchResult.adapter = adapter2 }

                    } else {
                        //search all
                        (from as Activity).runOnUiThread {
                            Log.d("s cat", "no cat")
                            Toast.makeText(from, "no category matched", Toast.LENGTH_LONG).show()
                            searchResult.adapter = null
                        }

                    }
                    searchResult.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        //go to item info
                        Log.d("clicked cat", items_list[position].ItemName)

                        val `in` = Intent(from, ItemInfo)
                        `in`.putExtra("Location", items_list[position].Location)
                        `in`.putExtra("Timestamp", items_list[position].TimeStamp)
                        `in`.putExtra("ItemName", items_list[position].ItemName)
                        `in`.putExtra("FullDescription", items_list[position].FullDescription)
                        `in`.putExtra("Value", items_list[position].Value)
                        `in`.putExtra("Category", items_list[position].Category)
                        `in`.putExtra("username", username)
                        `in`.putExtra("pw", pw)
                        from.startActivity(`in`)
                    }


                }
            }
        })
    }

    fun searchItemsByCategoryLoc(from: Context, ItemInfo: Class<*>, searchResult: ListView, username: String, pw: String, category: String, location: String,
                                 type: String, longitude: String, latitude: String, phone: String, address: String) {
        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("category", category)
                .add("location", location)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/searchItemByCategoryLoc")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get location", e.message)

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getlocation response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("success", myResponse)
                    (from as Activity).runOnUiThread {
                        Log.d("s cat loc", "no cat")
                        Toast.makeText(from, "no matched category", Toast.LENGTH_LONG).show()
                        getItems(from, ItemInfo, searchResult, username, pw, location,
                                type, longitude, latitude, phone, address)
                    }
                } else {

                    Log.d("success", myResponse)

                    val items_list = itemBuilder(myResponse) ?: return
                    val items_displays = arrayOfNulls<String>(items_list.size)
                    for ((tracker, item) in items_list.withIndex()) {
                        items_displays[tracker] = item.ItemName
                    }
                    if (items_displays.isNotEmpty()) {
                        val adapter2 = ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays)
                        (from as Activity).runOnUiThread { searchResult.adapter = adapter2 }

                    } else {
                        (from as Activity).runOnUiThread {
                            Log.d("s cat loc", "no cat")
                            Toast.makeText(from, "no matched category", Toast.LENGTH_LONG).show()
                            getItems(from, ItemInfo, searchResult, username, pw, location,
                                    type, longitude, latitude, phone, address)
                        }


                    }
                    searchResult.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        val `in` = Intent(from, ItemInfo)
                        `in`.putExtra("Location", items_list[position].Location)
                        `in`.putExtra("Timestamp", items_list[position].TimeStamp)
                        `in`.putExtra("ItemName", items_list[position].ItemName)
                        `in`.putExtra("FullDescription", items_list[position].FullDescription)
                        `in`.putExtra("Value", items_list[position].Value)
                        `in`.putExtra("Category", items_list[position].Category)
                        `in`.putExtra("username", username)
                        `in`.putExtra("pw", pw)
                        `in`.putExtra("Name", location)
                        `in`.putExtra("Type", type)
                        `in`.putExtra("Longitude", longitude)
                        `in`.putExtra("Latitude", latitude)
                        `in`.putExtra("Address", address)
                        `in`.putExtra("Phone", phone)
                        from.startActivity(`in`)
                    }

                }
            }
        })
    }


    fun searchItemsByName(from: Context, ItemInfo: Class<*>, searchResult: ListView, username: String, pw: String, name: String) {
        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("name", name)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/searchItemByName")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        if (name == "") {
            Toast.makeText(from, "null input", Toast.LENGTH_LONG).show()
            searchResult.adapter = null
            return
        }
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get location", e.message)

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getlocation response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("success", myResponse)

                    (from as Activity).runOnUiThread {
                        Log.d("s name", "no name")
                        Toast.makeText(from, "no name matched", Toast.LENGTH_LONG).show()
                        searchResult.adapter = null
                    }
                } else {

                    Log.d("success", myResponse)

                    val items_list = itemBuilder(myResponse) ?: return
                    val items_displays = arrayOfNulls<String>(items_list.size)
                    for ((tracker, item) in items_list.withIndex()) {
                        items_displays[tracker] = item.ItemName
                    }
                    if (items_displays.isNotEmpty()) {
                        val adapter2 = ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays)
                        (from as Activity).runOnUiThread { searchResult.adapter = adapter2 }

                    } else {
                        (from as Activity).runOnUiThread {
                            Log.d("s name", "no name")
                            Toast.makeText(from, "no name matched", Toast.LENGTH_LONG).show()
                            searchResult.adapter = null
                        }

                    }
                    searchResult.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        val `in` = Intent(from, ItemInfo)
                        `in`.putExtra("Location", items_list[position].Location)
                        `in`.putExtra("Timestamp", items_list[position].TimeStamp)
                        `in`.putExtra("ItemName", items_list[position].ItemName)
                        `in`.putExtra("FullDescription", items_list[position].FullDescription)
                        `in`.putExtra("Value", items_list[position].Value)
                        `in`.putExtra("Category", items_list[position].Category)
                        `in`.putExtra("username", username)
                        `in`.putExtra("pw", pw)
                        from.startActivity(`in`)
                    }

                }
            }
        })
    }

    fun searchItemsByNameLoc(from: Context, ItemInfo: Class<*>, searchResult: ListView, username: String, pw: String, name: String, location: String,
                             type: String, longitude: String, latitude: String, phone: String, address: String) {
        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("name", name)
                .add("location", location)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/searchItemByNameLoc")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        if (name == "") {
            Toast.makeText(from, "null input", Toast.LENGTH_LONG).show()
            searchResult.adapter = null
            getItems(from, ItemInfo, searchResult, username, pw, location,
                    type, longitude, latitude, phone, address)
            return
        }
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get location", e.message)

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getlocation response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("success", myResponse)
                    (from as Activity).runOnUiThread { Toast.makeText(from, "no matched name", Toast.LENGTH_LONG).show() }

                    getItems(from, ItemInfo, searchResult, username, pw, location,
                            type, longitude, latitude, phone, address)

                } else {

                    Log.d("success", myResponse)

                    val items_list = itemBuilder(myResponse) ?: return
                    val items_displays = arrayOfNulls<String>(items_list.size)
                    for ((tracker, item) in items_list.withIndex()) {
                        items_displays[tracker] = item.ItemName
                    }
                    if (items_displays.isNotEmpty()) {
                        val adapter2 = ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays)
                        (from as Activity).runOnUiThread { searchResult.adapter = adapter2 }

                    } else {
                        (from as Activity).runOnUiThread { Toast.makeText(from, "no matched category", Toast.LENGTH_LONG).show() }

                        getItems(from, ItemInfo, searchResult, username, pw, location,
                                type, longitude, latitude, phone, address)


                    }
                    searchResult.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        val `in` = Intent(from, ItemInfo)
                        `in`.putExtra("Location", items_list[position].Location)
                        `in`.putExtra("Timestamp", items_list[position].TimeStamp)
                        `in`.putExtra("ItemName", items_list[position].ItemName)
                        `in`.putExtra("FullDescription", items_list[position].FullDescription)
                        `in`.putExtra("Value", items_list[position].Value)
                        `in`.putExtra("Category", items_list[position].Category)
                        `in`.putExtra("username", username)
                        `in`.putExtra("pw", pw)
                        `in`.putExtra("Name", location)
                        `in`.putExtra("Type", type)
                        `in`.putExtra("Longitude", longitude)
                        `in`.putExtra("Latitude", latitude)
                        `in`.putExtra("Address", address)
                        `in`.putExtra("Phone", phone)
                        from.startActivity(`in`)
                    }
                }
            }
        })
    }

    fun addItems(from: Context, success: Class<*>, fail: Class<*>, category: String, name: String, full: String, timestamp: String, value: String, location: String, username: String, pw: String) {
        val body = FormBody.Builder()
                //.add("username", username)
                //.add("pw", pw)
                .add("location", location)
                .add("timestamp", timestamp)
                .add("name", name)
                .add("fulldescription", full)
                .add("value", value)
                .add("category", category)
                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/addItem")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail add item", e.message)
                //Toast.makeText(from,"fail",Toast.LENGTH_SHORT).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("additem response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("additem success", myResponse)
                    //Toast.makeText(from,"fail",Toast.LENGTH_SHORT).show();
                } else {

                    Log.d("additem success", myResponse)


                    val intent = Intent(from, success)

                    intent.putExtra("username", username)
                    intent.putExtra("pw", pw)

                    from.startActivity(intent)


                }
            }
        })
    }

    fun buttonVisibility(b: Button, username: String, pw: String) {
        val body = FormBody.Builder()
                .add("username", username)
                .add("pw", pw)

                .build()
        val client = OkHttpClient()
        val request = Request.Builder()
                .url("$url/userType")
                //.addHeader("Accept", "application/json")
                .header("Connection", "close")
                .post(body)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
                e.printStackTrace()
                Log.d("fail get user type", e.message)

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                //call.cancel();
                val myResponse = response.body().string()
                Log.d("getusertype response", myResponse)
                if (myResponse == "0") {
                    call.cancel()
                    Log.d("success", myResponse)

                } else {

                    Log.d("success", myResponse)

                    if (myResponse == "Location Employee") {
                        b.visibility = View.VISIBLE
                        Log.d("setbutton", myResponse)
                    }
                    Log.d("no setbutton", myResponse)
                    /*Intent intent = new Intent(from,success);
                    intent.putExtra("locations",myResponse);
                    intent.putExtra("username",username);
                    intent.putExtra("pw",pw);*/
                    //from.startActivity(intent);


                }
            }
        })

    }

    fun itemBuilder(raw: String): ArrayList<SingleItem>? {
        val jsonArray: JSONArray
        val list = ArrayList<SingleItem>()
        var values = arrayOfNulls<String>(1)
        if (raw == null || raw == "") return list
        try {
            jsonArray = JSONArray(raw)
            values = arrayOfNulls(jsonArray.length())
            for (i in 0 until jsonArray.length()) {
                val jsonobject = jsonArray.getJSONObject(i)
                val sl = SingleItem()
                sl.ItemName = jsonobject.getString("name")
                sl.Category = jsonobject.getString("category")
                sl.FullDescription = jsonobject.getString("fulldescription")
                sl.Location = jsonobject.getString("location")
                sl.TimeStamp = jsonobject.getString("timestamp")
                sl.Value = jsonobject.getString("value")
                list.add(sl)
            }
            return list
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
