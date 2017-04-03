package com.example.root.ocps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Random;

/**
 * Created by root on 13/2/17.
 */

public class login extends Activity{

    EditText Roll,Passwd;
    ProgressDialog pd;
    String id;
    String OTP;
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

            String[] part = sb.split("`");

            if(part[0].equals("OK")) {

                Intent voter_login = new Intent(login.this, voterLogin.class);
                voter_login.putExtra("ID",id);
                voter_login.putExtra("Status",part[1]);

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



    /////////////////////////////////Async Task/////////////////////////////////
    class ChangePass extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/forgetpassword.php";

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

            AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
            builder.setMessage("An OTP has been sent to your registered Email Address");
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

            Roll.setFocusable(false);
            Button btn  = (Button)findViewById(R.id.change_pass);
            btn.setVisibility(View.GONE);
            LinearLayout o_l = (LinearLayout)findViewById(R.id.o_layout);
            o_l.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(login.this, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////



    /////////////////////////////////Async Task/////////////////////////////////
    class Submit extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/changepassword.php";

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

            AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
            builder.setMessage(sb.toString());
            builder.setCancelable(false);
            builder.setTitle("Message");

            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {


                            dialog.cancel();
                            Intent i = new Intent(login.this,login.class);
                            startActivity(i);

                        }
                    });


            AlertDialog alert11 = builder.create();
            alert11.show();



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

               // Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show();
                LinearLayout l = (LinearLayout)findViewById(R.id.login_layout);
                l.setVisibility(View.GONE);
                Button btn  = (Button)findViewById(R.id.change_pass);
                btn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }


    }


    public void change_password(View view) {

        id = Roll.getText().toString();
        if(id.isEmpty())
        {
            Toast.makeText(this,"Enter Roll Number",Toast.LENGTH_SHORT).show();
        }
        else
        {

            Random ran = new Random();
            int otp = ran.nextInt(8888) + 1111;
            OTP = ""+otp;

            try {
                String data = URLEncoder.encode("ID", "UTF-8")
                        + "=" + URLEncoder.encode(id, "UTF-8");
                data += "&" + URLEncoder.encode("OTP", "UTF-8")
                        + "=" + URLEncoder.encode(OTP, "UTF-8");

                new ChangePass().execute(data);

            } catch (Exception e) {

                Toast.makeText(this,"An Error Occurred",Toast.LENGTH_SHORT).show();

            }

        }


    }

    public void verify_otp(View view) {

        EditText otp = (EditText)findViewById(R.id.otp);
        String e_otp = otp.getText().toString();
        if(e_otp.equals(OTP))
        {
            Toast.makeText(this,"OTP Verified",Toast.LENGTH_SHORT).show();
            LinearLayout l = (LinearLayout)findViewById(R.id.o_layout);
            l.setVisibility(View.GONE);

            LinearLayout ch_pass = (LinearLayout)findViewById(R.id.set_new_pass);
            ch_pass.setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(this,"Enter Correct OTP",Toast.LENGTH_SHORT).show();
        }
    }


    public void ChangePassword(View view) {

        EditText newpass = (EditText)findViewById(R.id.new_pass);
        EditText repass = (EditText)findViewById(R.id.re_pass);

        String pass = newpass.getText().toString();
        String re_pass = repass.getText().toString();

        if(!pass.equals(re_pass))
        {
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
        }

        else
        {
            try {
                String data = URLEncoder.encode("PASS", "UTF-8")
                        + "=" + URLEncoder.encode(pass, "UTF-8");
                data += "&" + URLEncoder.encode("V_ID", "UTF-8")
                        + "=" + URLEncoder.encode(id, "UTF-8");

                new Submit().execute(data);

            } catch (Exception e) {

                Toast.makeText(this,"An Error Occurred",Toast.LENGTH_SHORT).show();

            }
        }

    }
}
