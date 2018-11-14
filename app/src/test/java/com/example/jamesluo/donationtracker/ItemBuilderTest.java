package com.example.jamesluo.donationtracker;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ItemBuilderTest {
    String test1;
    String test2;
    @Before
    public void setup() {
        test1 = generateTest1();
        test2 = generateTest2();
    }

    @Test(expected = IllegalArgumentException.class)
    public void exceptionForNull() throws Exception {
        ServerModel.itemBuilder(null);
    }
    @Test
    public void emptyListFor0Response() throws Exception {
        ArrayList<ServerModel.SingleItem> list= ServerModel.itemBuilder("0");
        assertTrue(null!=list);
        assertTrue(0 == list.size());
    }
    @Test
    public void emptyListForBadResponse() throws Exception {
        ArrayList<ServerModel.SingleItem> list= ServerModel.itemBuilder("badbad");
        assertTrue(null!=list);
        assertTrue(0 == list.size());
    }
    @Test
    public void buildSingleItem() throws Exception {
        //String test = "[{\"name\":\"stadium\",\"category\":\"sport\",\"fulldescription\":\"for fun\",\"location\":\"Atlanta\",\"timestamp\":\"11/11/2018\",\"value\":\"something\"}]";


        ArrayList<ServerModel.SingleItem> list= ServerModel.itemBuilder(test1);
        assertTrue(1 == list.size());
        assertTrue(list.get(0).ItemName.equals("stadium"));
        assertTrue(list.get(0).FullDescription.equals("for fun"));
        assertTrue(list.get(0).Location.equals("Atlanta"));
        assertTrue(list.get(0).Category.equals("sport"));
        assertTrue(list.get(0).TimeStamp.equals("11/11/2018"));
        assertTrue(list.get(0).Value.equals("something"));


    }
    @Test
    public void buildSingleItem2() throws Exception {
        //String test = "[{\"name\":\"stadium\",\"category\":\"sport\",\"fulldescription\":\"for fun\",\"location\":\"Atlanta\",\"timestamp\":\"11/11/2018\",\"value\":\"something\"}]";


        ArrayList<ServerModel.SingleItem> list= ServerModel.itemBuilder(test2);
        assertTrue(1 == list.size());
        assertTrue(list.get(0).ItemName.equals("park"));
        assertTrue(list.get(0).FullDescription.equals("not so many fun"));
        assertTrue(list.get(0).Location.equals("NYC"));
        assertTrue(list.get(0).Category.equals("play"));
        assertTrue(list.get(0).TimeStamp.equals("11/11/2018"));
        assertTrue(list.get(0).Value.equals(""));


    }
    private String generateTest1 () {
        try{
            JSONArray ja = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("name", "stadium");
            obj.put("fulldescription", "for fun");
            obj.put("location", "Atlanta");
            obj.put("timestamp", "11/11/2018");
            obj.put("category", "sport");
            obj.put("value", "something");
            ja.put(obj);
            return ja.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }


    }
    private String generateTest2 () {
        try{
            JSONArray ja = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("name", "park");
            obj.put("fulldescription", "not so many fun");
            obj.put("location", "NYC");
            obj.put("timestamp", "11/11/2018");
            obj.put("category", "play");
            obj.put("value", "");
            ja.put(obj);
            return ja.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }


    }
}