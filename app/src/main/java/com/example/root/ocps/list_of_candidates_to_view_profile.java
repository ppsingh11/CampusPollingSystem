package com.example.root.ocps;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Prashant Pratap on 30-03-2017.
 */

public class list_of_candidates_to_view_profile extends ListActivity {

    ListView list;
    String s;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_candidates_to_view_profile);

        s = getIntent().getStringExtra("POST");

        list = (ListView)findViewById(android.R.id.list);



        list.setAdapter(new custom_list_for_profile(list_of_candidates_to_view_profile.this,this,s));


    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        TextView n = (TextView)v.findViewById(R.id.candidate_roll);
        String roll = n.getText().toString();

      //  Toast.makeText(this,roll,Toast.LENGTH_SHORT).show();

        Intent i =new Intent(this,view_candidate_profile.class);
        i.putExtra("ROLL",roll);
        startActivity(i);


    }


}
