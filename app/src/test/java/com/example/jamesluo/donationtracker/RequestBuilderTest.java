package com.example.jamesluo.donationtracker;

//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.junit.Before;
import org.junit.Test;

//import java.util.ArrayList;

import org.junit.Rule;
//import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

import okhttp3.FormBody;
//import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RequestBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void requestBuilder() {
        String username = null;
        String password = null;
        String category = null;
        String location = null;
        String username1 = "Joey";
        String password1 = "fail(test";
        String password2 = "fail)test";
        String password3 = "fail test";
        String password4 = "fail;test";
        String password5 = "successTest";
        String category1 = "test";
        String location1 = "georgiaTech";

        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username,password,category,location);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username,password1,category1,location1);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password,category1,location1);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password1,category,location1);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password1,category1,location);


        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password1,category1,location1);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password2,category1,location1);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password3,category1,location1);
        thrown.expect(IllegalArgumentException.class);
        ServerModel.requestBuilder(username1,password4,category1,location1);

        RequestBody body = new FormBody.Builder()
                .add("username", username1)
                .add("pw", password5)
                .add("category", category1)
                .add("location", location1)
                .build();

        assertTrue(body.toString().equals(ServerModel.requestBuilder("Joey", "successTest", "test", "georgiaTech")));
        assertFalse(body.toString().equals(ServerModel.requestBuilder("Joey1", "successTest", "test", "georgiaTech")));
        assertFalse(body.toString().equals(ServerModel.requestBuilder("Joey", "successTest1", "test", "georgiaTech")));
        assertFalse(body.toString().equals(ServerModel.requestBuilder("Joey", "successTest", "test1", "georgiaTech")));
        assertFalse(body.toString().equals(ServerModel.requestBuilder("Joey", "successTest", "test", "georgiaTech1")));


    }
}