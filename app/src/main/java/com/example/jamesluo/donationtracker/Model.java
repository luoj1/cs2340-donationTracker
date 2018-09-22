package com.example.jamesluo.donationtracker;

/**
 * Created by jamesluo on 9/20/18.
 */

public class Model {
    private final static String user = "user";
    private final static String pwd = "pass";

    public static boolean verify(String u, String p){
        if (u.equals(user) && p.equals(pwd)){
            return true;
        }else {
            return false;
        }
    }



}
