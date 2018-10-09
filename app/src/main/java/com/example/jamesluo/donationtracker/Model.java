package com.example.jamesluo.donationtracker;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.*;
import java.io.File;
import java.util.Map;

import static org.apache.commons.csv.CSVParser.parse;

/**
 * Created by jamesluo on 9/20/18.
 */

public class Model {
    //id: email
    //name: username
    //type: account type
    private static HashMap<String, String> auth = new HashMap<>();
    private static HashMap<String, Info> info = new HashMap<>();

    public static ArrayList<Location> buildLocation(String filePath) {
    //TODO init location array
        ArrayList<Location> locations = new ArrayList<>();
        CSVParser data = null;
        try{
            data = CSVParser.parse(filePath, CSVFormat.RFC4180);
        }catch ( java.io.IOException e){

            e.printStackTrace();
        }
        for (CSVRecord csvRecord: data) {
            Map<String,String> locationData = csvRecord.toMap();
            Log.d("-----------------",  csvRecord.get("Key"));
            int key = Integer.parseInt(locationData.get("key"));
            Location location = new Location(locationData);
            locations.add(key - 1,location);
        }
        return locations;
    }
    public static boolean verify(String u, String p){
        if (auth.containsKey(u) && auth.get(u).equals(p)){
            return true;
        }else {
            return false;
        }
    }
    public static boolean contains(String u) {
        return auth.containsKey(u);
    }
    public static void addUser(String user, String pwd,String type, String id) {
        auth.put(user,pwd);
        info.put(user, new Info(user, type ,id));
    }



}
class Info {
    public String name;
    public String type;
    public String id;
    public Info (String name, String type, String id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }
}

class Location {
    private Map<String, String> location;

    public Location(Map<String, String> location) {this.location = location;}

    public Map<String, String> getLocation() {
        return location;
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }
}