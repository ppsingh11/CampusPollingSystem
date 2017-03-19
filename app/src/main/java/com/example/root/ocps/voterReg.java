package com.example.root.ocps;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import static android.R.attr.data;

/**
 * Created by root on 13/2/17.
 */

public class voterReg extends Activity {


    EditText Roll ;
    TextView Name , Dept , Sem;
    Button Search;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voter_reg);
        Roll  = (EditText)findViewById(R.id.roll);
        Name = (TextView)findViewById(R.id.name);
        Dept = (TextView)findViewById(R.id.dept);
        Sem  = (TextView)findViewById(R.id.sem);
        Search = (Button)findViewById(R.id.search);

    }


    public void Search_voter(View view) {
      //  Search.setText("Please Wait...");


        String link = "https://onlinevotingnitp.000webhostapp.com/users_record.php";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String id = Roll.getText().toString();

        if(id.isEmpty())
        {
            Toast.makeText(this,"Enter Roll Number", Toast.LENGTH_SHORT).show();
        }
        else {
            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                String roll_num = URLEncoder.encode("ID", "UTF-8") + "=" +
                        URLEncoder.encode(id, "UTF-8");
                ////////////////////////////////////
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(roll_num);

                wr.flush();


                ///////////////////////////////


                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                reader.close();


                Roll.setFocusable(false);

                JSONArray json = new JSONArray(sb.toString());

                JSONObject jsonObj = json.getJSONObject(0);

                // Getting JSON Array node
                //JSONArray contacts = jsonObj.getJSONArray("contacts");
                String name = jsonObj.getString("name");
                String dept = jsonObj.getString("department");
                String semester = jsonObj.getString("semester");

               // Search.setText("SEARCH");


                LinearLayout l = (LinearLayout) findViewById(R.id.searchlayout);
                l.setVisibility(view.VISIBLE);

                Name.setText(name);
                Dept.setText(dept);
                Sem.setText(semester);

                //  Toast.makeText(this,""+name+" "+dept+" "+semester,Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

                Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show();

            }

        }

    }
}
