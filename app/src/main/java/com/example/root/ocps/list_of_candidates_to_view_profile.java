package com.example.root.ocps;

import android.app.Activity;
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

public class list_of_candidates_to_view_profile extends Activity {

    ListView list;
    String s;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_of_candidates_to_view_profile);

        s = getIntent().getStringExtra("POST");

        list = (ListView)findViewById(R.id.cand_list_to_view_profile);



        list.setAdapter(new custom_list_for_profile(list_of_candidates_to_view_profile.this,this,s));


    }





    public void view_profile(View view) {

        Toast.makeText(this,"Coming Soon",Toast.LENGTH_LONG).show();

    }
}
