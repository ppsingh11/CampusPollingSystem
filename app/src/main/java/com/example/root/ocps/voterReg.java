package com.example.root.ocps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;


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


    EditText Roll, Contact, Password, V_OTP;
    TextView Name, Dept, Sem;
    Button Search;
    String id;
    ProgressDialog pd;
    String OTP;

    CheckBox Water, Wifi, Laundry, Sweeping, Food, Hosteler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voter_reg);
        Roll = (EditText) findViewById(R.id.roll);
        V_OTP = (EditText)findViewById(R.id.otp);
        Name = (TextView) findViewById(R.id.name);
        Dept = (TextView) findViewById(R.id.dept);
        Sem = (TextView) findViewById(R.id.sem);
        Search = (Button) findViewById(R.id.search);

        Contact = (EditText) findViewById(R.id.contact);

        Password = (EditText) findViewById(R.id.password);

        Water = (CheckBox) findViewById(R.id.water);
        Food = (CheckBox) findViewById(R.id.food);
        Wifi = (CheckBox) findViewById(R.id.wifi);
        Laundry = (CheckBox) findViewById(R.id.laundry);
        Sweeping = (CheckBox) findViewById(R.id.sweeping);
        Hosteler = (CheckBox) findViewById(R.id.hosteler);

    }



    /////////////////////////////////Async Task/////////////////////////////////
    class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/users_record.php";
            String otp= params[0];

            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                String roll_num = URLEncoder.encode("ID", "UTF-8") + "=" +
                        URLEncoder.encode(id, "UTF-8");
                roll_num += "&" + URLEncoder.encode("OTP", "UTF-8")
                        + "=" + URLEncoder.encode(otp, "UTF-8");
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

                return sb.toString();
            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String sb) {
            pd.dismiss();
            Roll.setFocusable(false);
            try {


                JSONArray json = new JSONArray(sb.toString());

                JSONObject jsonObj = json.getJSONObject(0);

                String name = jsonObj.getString("name");
                String dept = jsonObj.getString("department");
                String semester = jsonObj.getString("semester");

                Name.setText(name);
                Dept.setText("Dept :" + dept);
                Sem.setText("Sem :" + semester);


                //////////////////////////////////////////////
                AlertDialog.Builder builder = new AlertDialog.Builder(voterReg.this);
                builder.setMessage("An OTP has been sent to your registered Email address");
                builder.setCancelable(false);
                builder.setTitle("Message");

                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });


                AlertDialog alert11 = builder.create();
                alert11.show();

                //////////////////////////////////////////////

                LinearLayout l = (LinearLayout) findViewById(R.id.otp_layout);
                l.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();


            }


        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(voterReg.this, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////


    /////////////////////////////////Async Task Submit/////////////////////////////////
    class SUBMIT extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/voter_reg.php";

            String v_data = params[0];

            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(v_data);

                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                reader.close();
                return sb.toString();

            } catch (Exception e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String sb) {
            pd.dismiss();

            try {

                AlertDialog.Builder builder = new AlertDialog.Builder(voterReg.this);
                builder.setMessage(sb.toString());
                builder.setCancelable(false);
                builder.setTitle("Message");

                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                dialog.cancel();
                            }
                        });


                AlertDialog alert11 = builder.create();
                alert11.show();


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();


            }


        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(voterReg.this, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////


    public void Search_voter(View view) {

        id = Roll.getText().toString();
        if (id.isEmpty()) {
            Toast.makeText(this, "Enter Roll Number", Toast.LENGTH_SHORT).show();
        } else {

            Random ran = new Random();
            int otp = ran.nextInt(8888) + 1111;
            OTP = ""+otp;

            new MyTask().execute(OTP);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    public void Register(View view) {

        String v_mail = Contact.getText().toString();
        String v_hosteler = "No", v_wifi = "No", v_water = "No", v_food = "No", v_laundry = "No", v_sweeping = "No";
        String v_pass = Password.getText().toString();


        if (Hosteler.isChecked()) {
            v_hosteler = "Yes";
        }

        if (Wifi.isChecked()) {
            v_wifi = "Yes";
        }
        if (Water.isChecked()) {
            v_water = "Yes";
        }
        if (Sweeping.isChecked()) {
            v_sweeping = "Yes";
        }
        if (Laundry.isChecked()) {
            v_laundry = "Yes";
        }
        if (Food.isChecked()) {
            v_food = "Yes";
        }

        if (v_mail.isEmpty()) {
            Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
        } else if (v_pass.isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

           try {
               String v_data = URLEncoder.encode("ID", "UTF-8")
                       + "=" + URLEncoder.encode(id, "UTF-8");

               v_data += "&" + URLEncoder.encode("Email", "UTF-8")
                       + "=" + URLEncoder.encode(v_mail, "UTF-8");
               v_data += "&" + URLEncoder.encode("Pass", "UTF-8")
                       + "=" + URLEncoder.encode(v_pass, "UTF-8");
               v_data += "&" + URLEncoder.encode("Hosteler", "UTF-8")
                       + "=" + URLEncoder.encode(v_hosteler, "UTF-8");
               v_data += "&" + URLEncoder.encode("Wifi", "UTF-8")
                       + "=" + URLEncoder.encode(v_wifi, "UTF-8");
               v_data += "&" + URLEncoder.encode("Water", "UTF-8")
                       + "=" + URLEncoder.encode(v_water, "UTF-8");
               v_data += "&" + URLEncoder.encode("Food", "UTF-8")
                       + "=" + URLEncoder.encode(v_food, "UTF-8");
               v_data += "&" + URLEncoder.encode("Laundry", "UTF-8")
                       + "=" + URLEncoder.encode(v_laundry, "UTF-8");
               v_data += "&" + URLEncoder.encode("Sweeping", "UTF-8")
                       + "=" + URLEncoder.encode(v_sweeping, "UTF-8");

               new SUBMIT().execute(v_data);
           }
           catch (Exception e)
           {
             Toast.makeText(this,"Some Error Occured",Toast.LENGTH_SHORT).show();
           }
            ////////////////////////////////////

        }
    }

    ////////////////////////verify otp///////
    public void verify_otp(View view) {

        String E_otp = V_OTP.getText().toString();
        if(E_otp.equals(OTP))
        {
            Toast.makeText(this,"OTP Verified",Toast.LENGTH_SHORT).show();

            LinearLayout otp_l = (LinearLayout) findViewById(R.id.otp_layout);
            otp_l.setVisibility(View.GONE);

            LinearLayout l = (LinearLayout) findViewById(R.id.searchlayout);
            l.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(this,"Enter Correct OTP",Toast.LENGTH_SHORT).show();
        }

    }
}