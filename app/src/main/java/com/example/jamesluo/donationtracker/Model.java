package com.example.jamesluo.donationtracker;


import android.app.Application;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.*;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVParser;
import com.opencsv.*;
/**
 * Created by jamesluo on 9/20/18.
 */

public class Model {
    //id: email
    //name: username
    //type: account type
    private static HashMap<String, String> auth = new HashMap<>();
    private static HashMap<String, Info> info = new HashMap<>();
    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<Location> locations2 = new ArrayList<>();
    private static ArrayList<Item> items = new ArrayList<>();

    public static HashMap<String, Info> getInfo(){
        return info;
    }
    public static List<Location> getLocations(){
        ArrayList<Location> out = new ArrayList<>();
        out.addAll(locations);
        out.addAll(locations2);
        int k = 0;
        for (Location l : out) {
            l.setKey(k);
            k++;
        }
        return out;
    }
    public static List<Item> getItems() {return items; }
    public static void buildLocationCSV(InputStream ins) throws FileNotFoundException, IOException {
    //TODO init location array
        locations = new ArrayList<>();
        Reader in = new InputStreamReader(ins, "UTF-8");

        Iterable<CSVRecord> data = CSVFormat.DEFAULT.withHeader().parse(in);
        int key  = 0;
        for (CSVRecord csvRecord: data) {
            Map<String,String> locationData = csvRecord.toMap();
            Log.d("-----------------", locationData.keySet().toString());

            Location location = new Location(locationData, key);
            locations.add(key,location);
            key++;
        }
    }

    public static void addLocation(Map<String, String> hm) {
        locations2.add(new Location(hm, 0));

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
        auth.put(id,pwd);
        info.put(id, new Info(user, type ,id));
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


    private int key;
    public int getKey(){
        return key;
    }
    public void setKey(int key){
        this.key = key;
    }
    public Location(Map<String, String> location , int key) {this.location = location;this.key = key;}

    public Map<String, String> getLocation() {
        return location;
    }

    public void setLocation(Map<String, String> location) {
        this.location = location;
    }
}

class Item {
    private Map<String, String> item;

    public Item(Map<String, String> location) {this.item = location;}

    public Map<String, String> getItem() {
        return item;
    }

    public void setItem(Map<String, String> location) {
        this.item = location;
    }
}