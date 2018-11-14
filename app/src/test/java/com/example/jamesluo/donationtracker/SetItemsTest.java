package com.example.jamesluo.donationtracker;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SetItemsTest {
    private static HashMap<String, List<Item>> testItems = Model.getItems();
    private String location1 = "Atlanta";
    private String location2 = "Golden State";
    private Map<String, String> l1 = new HashMap<>();
    private Map<String, String> l2 = new HashMap<>();
    private Map<String, String> l3 = new HashMap<>();
    @Test
    public void setItemsTest() {
        l1.put("a","a");
        l2.put("b","b");
        l3.put("c","c");
        Item item1 = new Item(l1);
        Item item2 = new Item(l2);
        Item item3 = new Item(l3);
        List<Item> items1 = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();
        items1.add(item1);
        items1.add(item3);
        items2.add(item2);
        items2.add(item1);
        testItems.put(location2, items2);
        Model.setItems(location1, item2,testItems);
        Model.setItems(location2, item3,testItems);
        assertTrue(testItems.containsKey("Atlanta"));
        assertTrue(testItems.get("Atlanta").contains(item2));
        assertTrue(testItems.get("Golden State").contains(item3));
    }
}
