package com.example.root.ocps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Prashant Pratap on 06-05-2017.
 */

public class election_result extends Activity {


    Spinner spn;
    ListView result_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.election_result);
        spn = (Spinner)findViewById(R.id.c_spinner);



        ArrayList<String> Values = new ArrayList<String>();
        Values.add("Select Post");
        Values.add("Hostel-President");
        Values.add("Hostel-Vice President");
        Values.add("College-President");
        Values.add("College-Vice President");
        ArrayAdapter<String> my_adp = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Values);

        spn.setAdapter(my_adp);


    }

    public void view_result(View view) {

        String post = spn.getSelectedItem().toString();
        if(post.equals("Select Post"))
        {
            Toast.makeText(this,"Select a post from list",Toast.LENGTH_SHORT).show();
        }
        else
        {

            Intent i = new Intent(this, custom_result.class);
            i.putExtra("POST", post);
            startActivity(i);

        }


    }
}

