package com.example.jamesluo.donationtracker;

import org.junit.Before;
import org.junit.Test;
import java.util.Map;

//import java.util.HashMap;

import static org.junit.Assert.*;

public class ModelTest {
    private static Map<String, String> auth = Model.getAuth();

    @Before
    public void setup() {

    }

    @Test
    public void verifyTest() {
        String u = "a";
        String p = "123456";

        auth.put(u, p);
        boolean result;

        result = Model.verify(u, p);
        assertTrue(result);

        result = Model.verify("b", "456789");
        assertFalse(result);

        result = Model.verify(u, "456789");
        assertFalse(result);

        result = Model.verify("b", p);
        assertFalse(result);
    }
}