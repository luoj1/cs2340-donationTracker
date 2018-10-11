package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Locations extends Activity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_location);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[Model.getLocations().size()];
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
                in.putExtra("Name", Model.getLocations().get(position).getLocation().get("Name"));
                in.putExtra("Type", Model.getLocations().get(position).getLocation().get("Type"));
                in.putExtra("Longitude", Model.getLocations().get(position).getLocation().get("Longitude"));
                in.putExtra("Latitude", Model.getLocations().get(position).getLocation().get("Latitude"));
                in.putExtra("Address", Model.getLocations().get(position).getLocation().get("Street Address"));
                in.putExtra("Phone", Model.getLocations().get(position).getLocation().get("Phone"));
                startActivity(in);
            }
        });
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Locations.this, MainActivity.class);
                startActivity(i);
            }
        });
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