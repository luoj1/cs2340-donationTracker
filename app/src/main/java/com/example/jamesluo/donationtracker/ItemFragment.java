package com.example.jamesluo.donationtracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jamesluo on 10/31/18.
 */

public class ItemFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("con createview","1");
        //mParam1 = getArguments().getString("params");
        TextView tx = (TextView) getActivity().findViewById(R.id.item_name);
        Button b = (Button) getActivity().findViewById(R.id.deflate);
        Log.d("con createview","2");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("con createview","3");
                FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
                ft.remove(((Activity)view.getContext()).getFragmentManager().findFragmentById(R.id.viewer));
                //https://mobikul.com/pass-data-activity-fragment-android/
                ft.commit();

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater,container,savedInstanceState);

        Log.d("con createview","xx");
        return inflater.inflate(R.layout.fragment_item, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        //String s = savedInstanceState.getString("item_name");
        super.onViewCreated(view,savedInstanceState);


    }
}
