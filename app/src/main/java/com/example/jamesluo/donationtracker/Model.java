package com.example.jamesluo.donationtracker;


import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.csv.*;

import java.util.List;
import java.util.Map;

/**
 * Created by jamesluo on 9/20/18.
 */

public class Model {
    //id: email
    //name: username
    //type: account type
    private static Map<String, String> auth = new HashMap<>();
    private static Map<String, Info> info = new HashMap<>();
    private static Map<String, List<Item>> items = new HashMap<>();
    private static List<Location> locations = new ArrayList<>();
    //private static ArrayList<Item> items = new ArrayList<>();

    /**
     * getter method for info
     * @return map information
     */
    public static Map<String, String> getAuth(){
        return auth;
    }

    /**
     * getter method for info
     * @return map information
     */
    public static Map<String, List<Item>> getItems(){
        return items;
    }
    /**
     * getter method for info
     * @return map information
     */
    public static Map<String, Info> getInfo(){
        return info;
    }

    /**
     * getter method for locations
     * @return list of locations
     */
    public static List<Location> getLocations(){
        return locations;
    }
    /**
     * setter method for items
     * @param s target location
     * @param it target item
     */
    public static void setItems(String s, Item it) {
        if(items.containsKey(s)){
            items.get(s).add(it);
        }else{
            items.put(s, new ArrayList<Item>());
            items.get(s).add(it);
        }
    }

    /**
     * getter method for item
     * @param s target location
     * @return item list in the target location
     */
    public static List<Item> getItems(String s) {if (items.containsKey(s)) return items.get(s); else return null; }
    /**
     * build location csv
     * @param ins input array
     * @throws FileNotFoundException the file is not found
     * @throws IOException input list instead of array
     */
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

    /**
     * verify if our database has the input information
     * @param u input username
     * @param p input password
     * @return Yes/No if the input information exist and match
     */
    public static boolean verify(String u, String p){
        return auth.containsKey(u) && auth.get(u).equals(p);
    }

    /**
     * check if the database has the input username
     * @param u input username
     * @return Yes/No if the input information exist and match
     */
    public static boolean contains(String u) {
        return auth.containsKey(u);
    }

    /**
     * add user to database
     * @param user input username
     * @param pwd  input password
     * @param type input user type
     * @param id input ID number
     */
    public static void addUser(String user, String pwd,String type, String id) {
        //email as id
        auth.put(id,pwd);
        info.put(id, new Info(user, type ,id));
    }



}

class Info {

    private String name;
    private String type;
    private String id;
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