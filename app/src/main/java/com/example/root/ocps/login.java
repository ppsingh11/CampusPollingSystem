package com.example.root.ocps;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by root on 13/2/17.
 */

public class login extends Activity{

    EditText Roll,Passwd;
    ProgressDialog pd;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Roll = (EditText)findViewById(R.id.roll);
        Passwd = (EditText)findViewById(R.id.password);
    }




    /////////////////////////////////Async Task/////////////////////////////////
    class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/login.php";

            String info = params[0];

            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(info);

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

                return sb.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String sb) {
            pd.dismiss();

            if(sb.equals("OK")) {

                Intent voter_login = new Intent(login.this, voterLogin.class);
                voter_login.putExtra("ID",id);

                startActivity(voter_login);
            }
            else
            {
                Toast.makeText(login.this,sb.toString(),Toast.LENGTH_SHORT).show();
            }



        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(login.this, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////







    public void voter_login(View view) {

         id = Roll.getText().toString();
        String pass = Passwd.getText().toString();

        if(id.isEmpty())
        {
            Toast.makeText(this,"Enter Roll Number",Toast.LENGTH_SHORT).show();
        }
        else if(pass.isEmpty())
        {
            Toast.makeText(this,"Enter Password",Toast.LENGTH_SHORT).show();
        }
        else {

            try {
                String data = URLEncoder.encode("ID", "UTF-8")
                        + "=" + URLEncoder.encode(id, "UTF-8");

                data += "&" + URLEncoder.encode("PASS", "UTF-8")
                        + "=" + URLEncoder.encode(pass, "UTF-8");

                new MyTask().execute(data);

            } catch (Exception e) {

                Toast.makeText(this,"An Error Occurred",Toast.LENGTH_SHORT).show();

            }

        }
    }

    public void forgot_password(View view) {

        switch(view.getId())
        {
            case R.id.f_password:

                Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }


    }
}
