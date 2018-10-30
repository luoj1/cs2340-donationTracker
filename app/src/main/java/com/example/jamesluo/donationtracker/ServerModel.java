package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jamesluo on 10/25/18.
 */
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
   private final static String url = "http://10.0.2.2:8080";
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

}
