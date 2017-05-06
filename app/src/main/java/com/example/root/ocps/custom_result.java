package com.example.root.ocps;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by Prashant Pratap on 07-05-2017.
 */

public class custom_result extends Activity {


        ListView list;
        String s;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.custom_result);

            s = getIntent().getStringExtra("POST");

            list = (ListView)findViewById(R.id.resultList);



            list.setAdapter(new customized_list_for_result(custom_result.this,this,s));


        }
}
