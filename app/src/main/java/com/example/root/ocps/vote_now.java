package com.example.root.ocps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Prashant Pratap on 25-03-2017.
 */

public class vote_now extends Activity {
    ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote_now);


        list = (ListView)findViewById(R.id.cand_list);

        list.setAdapter(new custom_cand_list(vote_now.this,this));
    }

    public void cast_vote(View view) {

        Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();
    }
}
