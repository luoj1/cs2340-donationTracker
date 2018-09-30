package com.example.jamesluo.donationtracker;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jamesluo on 9/20/18.
 */

public class Model {
    //id: email
    //name: username
    //type: account type
    private static HashMap<String, String> auth = new HashMap<>();
    private static HashMap<String, Info> info = new HashMap<>();

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
