package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Locations extends Activity {
    private class SingleLocation{
        String name;
        String latitude;
        String longitude;
        String street_addr;
        String city;
        String state;
        String type;
        String phone;
        String website;
        String zip;
    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_location);
        if (getIntent().getStringExtra("locations") == null){
            ServerModel.getLocation(Locations.this, Locations.class, LoginSuccess.class,getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"));
        }else{
            JSONArray jsonArray;
            final ArrayList<SingleLocation> list = new ArrayList<>();
            String[] values = new String[1];
            try{
                jsonArray = new JSONArray(getIntent().getStringExtra("locations"));
                values  = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    SingleLocation sl = new SingleLocation();
                    String name = jsonobject.getString("name");
                    sl.name = name;
                    sl.latitude = jsonobject.getString("latitude");
                    sl.longitude = jsonobject.getString("longitude");
                    sl.street_addr = jsonobject.getString("street_addr");
                    sl.city = jsonobject.getString("city");
                    sl.state = jsonobject.getString("state");
                    sl.type = jsonobject.getString("type");
                    sl.phone = jsonobject.getString("phone");
                    sl.website = jsonobject.getString("website");
                    sl.zip = jsonobject.getString("zip");
                    values[i] = name;
                    list.add(sl);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }




            final ListView listview = (ListView) findViewById(R.id.listview);

            int tracker =0 ;
            for (Location l : Model.getLocations()) {
                values[tracker] = Model.getLocations().get(tracker).getLocation().get("Name");
                tracker ++ ;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, values);
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    //Toast.makeText(getBaseContext() ,Integer.toString(position) + " selected", Toast.LENGTH_LONG).show();

                    Intent in = new Intent(Locations.this, LocationInfo.class);
                    Log.d("position clicked",""+position);
                    in.putExtra("username", getIntent().getStringExtra("username"));
                    in.putExtra("pw", getIntent().getStringExtra("pw"));
                    in.putExtra("Name", list.get(position).name);
                    in.putExtra("Type", list.get(position).type);
                    in.putExtra("Longitude", list.get(position).longitude);
                    in.putExtra("Latitude", list.get(position).latitude);
                    in.putExtra("Address", list.get(position).street_addr);
                    in.putExtra("Phone", list.get(position).phone);
                    startActivity(in);


                }
            });
        }


        final ListView searchResult = (ListView) findViewById(R.id.searchResult);
        final EditText nameOfItem = (EditText) findViewById(R.id.searchName);
        String searchName = nameOfItem.toString();
        Button searchByName = (Button) findViewById(R.id.searchByName);
        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do search and create result listview

                ServerModel.searchItemsByName(Locations.this,ItemInfo_Location.class, searchResult, getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"), nameOfItem.getText().toString());


            }
        });
        final Spinner categoryOfItem = (Spinner) findViewById(R.id.searchCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Category.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryOfItem.setAdapter(adapter);
        Button searchByCategory = (Button) findViewById(R.id.searchByCategory);

        Log.d("locations","item_list");


        Log.d("locations","adapter");


        Log.d("locations","onclick");
        searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //go to item info


            }
        });
        searchByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do search and create result listview

                ServerModel.searchItemsByCategory(Locations.this,ItemInfo_Location.class,searchResult,
                        getIntent().getStringExtra("username"), getIntent().getStringExtra("pw"),
                        categoryOfItem.getSelectedItem().toString());


            }
        });

    }
    /*private class Query {
        String from;
        String username;
        String pw;
        String q;
    }
    private class categorySearchUpdater extends AsyncTask<Query,Object,>{

    }*/
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent in=new Intent(Locations.this, LoginSuccess.class);
        in.putExtra("pw", getIntent().getStringExtra("pw"));
        in.putExtra("username", getIntent().getStringExtra("username"));
        in.putExtra("Name", getIntent().getStringExtra("Name"));
        in.putExtra("Type", getIntent().getStringExtra("Type"));
        in.putExtra("Longitude", getIntent().getStringExtra("Longitude"));
        in.putExtra("Latitude", getIntent().getStringExtra("Latitude"));
        in.putExtra("Address", getIntent().getStringExtra("Address"));
        in.putExtra("Phone", getIntent().getStringExtra("Phone"));

        startActivity(in);
    }


}
class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MySimpleArrayAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.location_list_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        textView.setText(values[position]);


        return rowView;
    }
}