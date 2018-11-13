package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by jamesluo on 10/25/18.
 */
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class ServerModel    {
        private static class SingleLocation{
            String name;
            String latitude;
            String longitude;
            String street_addr;
            String city;
            String state;
            String type;
            String phone;
            String website;
            String zip;
        }
    private static class SingleItem{
        String ItemName;
        String FullDescription;
        String Category;
        String Value;
        String TimeStamp;
        String Location;
    }
   private final static String url = "http://162.243.172.39:8080";
   private static OkHttpClient client;
   public static void initClient() {
       client = new OkHttpClient.Builder().retryOnConnectionFailure(true).build();
   }
    public static void createNewUserInDB(final Context from, final Class success2,
            final Class fail2, String email, String name, String type, String uid){
        Log.d("serverModel", name);

        RequestBody body = new FormBody.Builder()
                .add("user", name)
                .add("pw", uid)
                .add("email", email)
                .add("user_type", type)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/register")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail",e.getMessage());
                Intent intent = new Intent(from,fail2);
                from.startActivity(intent);
                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                if (myResponse .equals("1")){
                    Log.d("success",myResponse);
                    Intent intent = new Intent(from,success2);
                    from.startActivity(intent);
                }else{
                    call.cancel();
                    Log.d("success",myResponse);
                    Intent intent = new Intent(from,fail2);
                    from.startActivity(intent);


                }
            }
        });
    }

    public static void getLocation(final Context from, final Class success, final Class fail, final String username, final String pw){
        Log.d("serverModel", username);

        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/getLocation")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location",e.getMessage());
                Intent intent = new Intent(from,fail);
                intent.putExtra("username", username);
                intent.putExtra("pw", pw);
                from.startActivity(intent);
                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getlocation response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("success",myResponse);
                    Intent intent = new Intent(from,fail);
                    intent.putExtra("username", username);
                    intent.putExtra("pw", pw);
                    from.startActivity(intent);

                }else{
                    try{

                        Log.d("success",myResponse);

                        Intent intent = new Intent(from,success);
                        intent.putExtra("locations",myResponse);
                        intent.putExtra("username",username);
                        intent.putExtra("pw",pw);
                        from.startActivity(intent);

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            }
            });
    }

    public static void getLocationForMap(final Context from, final GoogleMap gm /*,final Class success, final Class fail,*/){
        Log.d("serverModelfor map","xx");

        RequestBody body = new FormBody.Builder()
                .add("username","x")
                .add("pw", "x")
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/getLocation")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location map",e.getMessage());
                /*Intent intent = new Intent(from,fail);
                intent.putExtra("username", username);
                intent.putExtra("pw", pw);
                from.startActivity(intent);*/
                Toast.makeText(from, "fail in creating map", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getlocation response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("success map",myResponse);
                    /*Intent intent = new Intent(from,fail);
                    intent.putExtra("username", username);
                    intent.putExtra("pw", pw);
                    from.startActivity(intent);*/
                    Toast.makeText(from, "success in creating map 0", Toast.LENGTH_LONG).show();

                }else{
                    try{

                        Log.d("success map with sth.",myResponse);

                        /*Intent intent = new Intent(from,success);
                        intent.putExtra("locations",myResponse);
                        intent.putExtra("username",username);
                        intent.putExtra("pw",pw);
                        from.startActivity(intent);*/
                        JSONArray jsonArray;
                        final ArrayList<SingleLocation> list = new ArrayList<>();
                        String[] values = new String[1];
                        try{
                            jsonArray = new JSONArray(myResponse);
                            values  = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonobject = jsonArray.getJSONObject(i);
                                SingleLocation sl = new SingleLocation();
                                String name = jsonobject.getString("name");
                                sl.name = name;
                                sl.latitude = jsonobject.getString("latitude");
                                sl.longitude = jsonobject.getString("longitude");
                                sl.street_addr = jsonobject.getString("street_addr");
                                sl.city = jsonobject.getString("city");
                                sl.state = jsonobject.getString("state");
                                sl.type = jsonobject.getString("type");
                                sl.phone = jsonobject.getString("phone");
                                sl.website = jsonobject.getString("website");
                                sl.zip = jsonobject.getString("zip");
                                values[i] = name;
                                list.add(sl);
                            }
                            ((Activity)from).runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    //Toast.makeText(from, "empty", Toast.LENGTH_LONG).show();
                                    //LatLng place = new LatLng(-84.38 , 33);
                                    //gm.addMarker(new MarkerOptions().position(place).title("Marker in Sydney"));
                                    for (SingleLocation s : list) {
                                        LatLng loc = new LatLng(Double.parseDouble(s.latitude) ,Double.parseDouble(s.longitude));

                                        gm.addMarker(new MarkerOptions().position(loc).title(s.name + "\ncall: "+s.phone));
                                        gm.moveCamera(CameraUpdateFactory.newLatLng(loc));


                                    }
                                    gm.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                        @Override
                                        public boolean onMarkerClick(Marker arg0) {

                                            Toast.makeText(from,arg0.getTitle(),Toast.LENGTH_LONG).show();

                                            return true;

                                        }
                                    });

                                }
                            });

                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }
            }
        });
    }

    public static void getItems(final Context from,final Class ItemInfo, final ListView searchResult, final String username, final String pw, final String location,
                                final String type, final String longitude, final String latitude, final String phone, final String address) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("location", location)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/getItems")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location",e.getMessage());

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getitems response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("0 in items",myResponse);
                    ((Activity)from).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(from, "empty", Toast.LENGTH_LONG).show();
                            searchResult.setAdapter(null);

                        }
                    });

                }else{

                    Log.d("success in items",myResponse);

                    final ArrayList<SingleItem> items_list = itemBuilder(myResponse);
                    if (items_list == null) return;
                    final String[] items_displays = new String[items_list.size()];
                    int tracker = 0;
                    for (SingleItem item : items_list) {
                        items_displays[tracker] = item.ItemName;
                        tracker++;
                    }
                    Log.d("item display len", ""+items_displays.length );
                    if (items_displays.length > 0 ) {
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays);
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                searchResult.setAdapter(adapter2);

                            }
                        });

                    }else{
                        //search all
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Toast.makeText(from, "empty", Toast.LENGTH_LONG).show();
                                searchResult.setAdapter(null);

                            }
                        });

                    }
                    searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            Intent in = new Intent(from,ItemInfo);
                            in.putExtra("Location", items_list.get(position).Location);
                            in.putExtra("Timestamp", items_list.get(position).TimeStamp);
                            in.putExtra("ItemName", items_list.get(position).ItemName);
                            in.putExtra("FullDescription", items_list.get(position).FullDescription);
                            in.putExtra("Value", items_list.get(position).Value);
                            in.putExtra("Category", items_list.get(position).Category);
                            in.putExtra("username",username);
                            in.putExtra("pw",pw);
                            in.putExtra("Name", location);
                            in.putExtra("Type",type);
                            in.putExtra("Longitude", longitude);
                            in.putExtra("Latitude", latitude);
                            in.putExtra("Address", address);
                            in.putExtra("Phone", phone);
                            from.startActivity(in);

                        }
                    });


                }
            }
        });
    }
    public static void searchItemsByCategory(final Context from, final Class ItemInfo,final ListView searchResult, final String username, final String pw, final  String category) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("category", category)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/searchItemByCategory")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location",e.getMessage());

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getlocation response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("success",myResponse);
                    ((Activity)from).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Log.d("s cat","no cat");
                            Toast.makeText(from, "no category matched", Toast.LENGTH_LONG).show();
                            searchResult.setAdapter(null);

                        }
                    });
                }else{

                    Log.d("success",myResponse);

                    final ArrayList<SingleItem> items_list = itemBuilder(myResponse);
                    if (items_list == null) return;
                    final String[] items_displays = new String[items_list.size()];
                    int tracker = 0;
                    for (SingleItem item : items_list) {
                        items_displays[tracker] = item.ItemName;
                        tracker++;
                    }
                    if (items_displays.length > 0 ) {
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays);
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                searchResult.setAdapter(adapter2);

                            }
                        });

                    }else{
                        //search all
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Log.d("s cat","no cat");
                                Toast.makeText(from, "no category matched", Toast.LENGTH_LONG).show();
                                searchResult.setAdapter(null);

                            }
                        });

                    }
                    searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            //go to item info
                            Log.d("clicked cat", items_list.get(position).ItemName);

                            Intent in = new Intent(from,ItemInfo);
                            in.putExtra("Location", items_list.get(position).Location);
                            in.putExtra("Timestamp", items_list.get(position).TimeStamp);
                            in.putExtra("ItemName", items_list.get(position).ItemName);
                            in.putExtra("FullDescription", items_list.get(position).FullDescription);
                            in.putExtra("Value", items_list.get(position).Value);
                            in.putExtra("Category", items_list.get(position).Category);
                            in.putExtra("username",username);
                            in.putExtra("pw",pw);
                            from.startActivity(in);
                        }
                    });





                }
            }
        });
    }

    public static void searchItemsByCategoryLoc(final Context from, final Class ItemInfo, final ListView searchResult, final String username, final String pw, final  String category, final String location,
                                                final String type, final String longitude, final String latitude, final String phone, final String address) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("category", category)
                .add("location", location)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/searchItemByCategoryLoc")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location",e.getMessage());

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getlocation response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("success",myResponse);
                    ((Activity)from).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Log.d("s cat loc","no cat");
                            Toast.makeText(from, "no matched category", Toast.LENGTH_LONG).show();
                            getItems(from,ItemInfo,searchResult, username, pw, location,
                            type, longitude, latitude, phone,  address);

                        }
                    });
                }else{

                    Log.d("success",myResponse);

                    final ArrayList<SingleItem> items_list = itemBuilder(myResponse);
                    if (items_list == null) return;
                    final String[] items_displays = new String[items_list.size()];
                    int tracker = 0;
                    for (SingleItem item : items_list) {
                        items_displays[tracker] = item.ItemName;
                        tracker++;
                    }
                    if (items_displays.length > 0 ) {
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays);
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                searchResult.setAdapter(adapter2);

                            }
                        });

                    }else{
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Log.d("s cat loc","no cat");
                                Toast.makeText(from, "no matched category", Toast.LENGTH_LONG).show();
                                getItems(from,ItemInfo,searchResult, username, pw, location,
                                        type, longitude, latitude, phone,  address);

                            }
                        });


                    }
                    searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            Intent in = new Intent(from,ItemInfo);
                            in.putExtra("Location", items_list.get(position).Location);
                            in.putExtra("Timestamp", items_list.get(position).TimeStamp);
                            in.putExtra("ItemName", items_list.get(position).ItemName);
                            in.putExtra("FullDescription", items_list.get(position).FullDescription);
                            in.putExtra("Value", items_list.get(position).Value);
                            in.putExtra("Category", items_list.get(position).Category);
                            in.putExtra("username",username);
                            in.putExtra("pw",pw);
                            in.putExtra("Name", location);
                            in.putExtra("Type",type);
                            in.putExtra("Longitude", longitude);
                            in.putExtra("Latitude", latitude);
                            in.putExtra("Address", address);
                            in.putExtra("Phone", phone);
                            from.startActivity(in);

                        }
                    });

                }
            }
        });
    }


    public static void searchItemsByName(final Context from, final Class ItemInfo, final ListView searchResult, final String username, final String pw, final String name) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("name", name)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/searchItemByName")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        if (name .equals("")){
            Toast.makeText(from, "null input", Toast.LENGTH_LONG).show();
            searchResult.setAdapter(null);
            return;
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location",e.getMessage());

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getlocation response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("success",myResponse);

                    ((Activity)from).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Log.d("s name","no name");
                            Toast.makeText(from, "no name matched", Toast.LENGTH_LONG).show();
                            searchResult.setAdapter(null);

                        }
                    });
                }else{

                    Log.d("success",myResponse);

                    final ArrayList<SingleItem> items_list = itemBuilder(myResponse);
                    if (items_list == null) return;
                    final String[] items_displays = new String[items_list.size()];
                    int tracker = 0;
                    for (SingleItem item : items_list) {
                        items_displays[tracker] = item.ItemName;
                        tracker++;
                    }
                    if (items_displays.length > 0 ) {
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays);
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                searchResult.setAdapter(adapter2);

                            }
                        });

                    }else{
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Log.d("s name","no name");
                                Toast.makeText(from, "no name matched", Toast.LENGTH_LONG).show();
                                searchResult.setAdapter(null);

                            }
                        });

                    }
                    searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            Intent in = new Intent(from,ItemInfo);
                            in.putExtra("Location", items_list.get(position).Location);
                            in.putExtra("Timestamp", items_list.get(position).TimeStamp);
                            in.putExtra("ItemName", items_list.get(position).ItemName);
                            in.putExtra("FullDescription", items_list.get(position).FullDescription);
                            in.putExtra("Value", items_list.get(position).Value);
                            in.putExtra("Category", items_list.get(position).Category);
                            in.putExtra("username",username);
                            in.putExtra("pw",pw);
                            from.startActivity(in);

                        }
                    });

                }
            }
        });
    }

    public static void searchItemsByNameLoc(final Context from, final Class ItemInfo, final ListView searchResult, final String username, final String pw, final String name, final String location,
                                            final String type, final String longitude, final String latitude, final String phone, final String address) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)
                .add("name", name)
                .add("location", location)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/searchItemByNameLoc")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        if (name .equals("")){
            Toast.makeText(from, "null input", Toast.LENGTH_LONG).show();
            searchResult.setAdapter(null);
            getItems(from,ItemInfo,searchResult, username, pw, location,
                    type, longitude, latitude, phone,  address);
            return;
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get location",e.getMessage());

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getlocation response", myResponse);
                if (myResponse .equals("0") ){
                    call.cancel();
                    Log.d("success",myResponse);
                    ((Activity)from).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            Toast.makeText(from, "no matched name", Toast.LENGTH_LONG).show();

                        }
                    });

                    getItems(from,ItemInfo,searchResult, username, pw, location,
                            type, longitude, latitude, phone,  address);

                }else{

                    Log.d("success",myResponse);

                    final ArrayList<SingleItem> items_list = itemBuilder(myResponse);
                    if (items_list == null) return;
                    final String[] items_displays = new String[items_list.size()];
                    int tracker = 0;
                    for (SingleItem item : items_list) {
                        items_displays[tracker] = item.ItemName;
                        tracker++;
                    }
                    if (items_displays.length > 0 ) {
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(from,
                                android.R.layout.simple_list_item_1, items_displays);
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                searchResult.setAdapter(adapter2);

                            }
                        });

                    }else{
                        ((Activity)from).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                Toast.makeText(from, "no matched category", Toast.LENGTH_LONG).show();

                            }
                        });

                        getItems(from,ItemInfo,searchResult, username, pw, location,
                                type, longitude, latitude, phone,  address);



                    }
                    searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            Intent in = new Intent(from,ItemInfo);
                            in.putExtra("Location", items_list.get(position).Location);
                            in.putExtra("Timestamp", items_list.get(position).TimeStamp);
                            in.putExtra("ItemName", items_list.get(position).ItemName);
                            in.putExtra("FullDescription", items_list.get(position).FullDescription);
                            in.putExtra("Value", items_list.get(position).Value);
                            in.putExtra("Category", items_list.get(position).Category);
                            in.putExtra("username",username);
                            in.putExtra("pw",pw);
                            in.putExtra("Name", location);
                            in.putExtra("Type",type);
                            in.putExtra("Longitude", longitude);
                            in.putExtra("Latitude", latitude);
                            in.putExtra("Address", address);
                            in.putExtra("Phone", phone);
                            from.startActivity(in);

                        }
                    });
                }
            }
        });
    }

    public static void addItems(final Context from, final Class success, final Class fail
                                , final String category
                                , final String name
                                , final String full
                                , final String timestamp
                                , final String value
                                ,final String location
                                ,final String username, final String pw) {
        RequestBody body = new FormBody.Builder()
                //.add("username", username)
                //.add("pw", pw)
                .add("location", location)
                .add("timestamp", timestamp)
                .add("name", name)
                .add("fulldescription", full)
                .add("value", value)
                .add("category", category)
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/addItem")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail add item",e.getMessage());
                //Toast.makeText(from,"fail",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("additem response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("additem success",myResponse);
                    //Toast.makeText(from,"fail",Toast.LENGTH_SHORT).show();
                }else{

                    Log.d("additem success",myResponse);


                    Intent intent = new Intent(from,success);

                    intent.putExtra("username",username);
                    intent.putExtra("pw",pw);

                    from.startActivity(intent);




                }
            }
        });
    }
    public static void buttonVisibility (final Button b, final String username, final String pw) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("pw", pw)

                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+"/userType")
                //.addHeader("Accept", "application/json")
                .header("Connection","close")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                e.printStackTrace();
                Log.d("fail get user type",e.getMessage());

                //Toast.makeText(from, "fail in creatring account", Toast.LENGTH_LONG).show();
                //Toast.makeText(from, "db issue", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //call.cancel();
                final String myResponse = response.body().string();
                Log.d("getusertype response", myResponse);
                if (myResponse .equals("0")){
                    call.cancel();
                    Log.d("success",myResponse);

                }else{

                    Log.d("success",myResponse);

                        if (myResponse.equals("Location Employee")){
                            b.setVisibility(View.VISIBLE);
                            Log.d("setbutton",myResponse);
                        }
                    Log.d("no setbutton",myResponse);
                    /*Intent intent = new Intent(from,success);
                    intent.putExtra("locations",myResponse);
                    intent.putExtra("username",username);
                    intent.putExtra("pw",pw);*/
                    //from.startActivity(intent);




                }
            }
        });

    }
    public static ArrayList<SingleItem> itemBuilder(String raw){
        JSONArray jsonArray;
        final ArrayList<SingleItem> list = new ArrayList<>();
        String[] values = new String[1];
        if (raw.equals(null) || raw.equals("")) return list;
        try{
            jsonArray = new JSONArray(raw);
            values  = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                SingleItem sl = new SingleItem();
                sl.ItemName = jsonobject.getString("name");
                sl.Category = jsonobject.getString("category");
                sl.FullDescription = jsonobject.getString("fulldescription");
                sl.Location = jsonobject.getString("location");
                sl.TimeStamp = jsonobject.getString("timestamp");
                sl.Value = jsonobject.getString("value");
                list.add(sl);
            }
            return list;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
