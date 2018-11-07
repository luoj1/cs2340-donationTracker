package com.example.jamesluo.donationtracker;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ModelTest {
    private static HashMap<String, String> auth = Model.getAuth();
    @Test
    public void verifyTest() {
        String u = "a";
        String p = "123456";
        boolean expected1 = true;
        boolean expected2 = false;
        auth.put(u, p);
        boolean result;

        //Model model = new Model();
        result = Model.verify(u, p);
        assertEquals(expected1, result);
        result = Model.verify("b", "456789");
        assertEquals(expected2, result);
        result = Model.verify(u, "456789");
        assertEquals(expected2, result);
        result = Model.verify("b", p);
        assertEquals(expected2, result);
    }
}