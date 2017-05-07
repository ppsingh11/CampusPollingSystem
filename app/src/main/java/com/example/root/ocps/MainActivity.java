package com.example.root.ocps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences spf;
    SharedPreferences.Editor edt;

    Button v_reg,c_reg,result;
    String V_reg,C_reg,Voting,Result;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spf = getSharedPreferences("myshared", Context.MODE_PRIVATE);
        edt = spf.edit();

//////////////////////////////////////////////////////////////////////////
        MyTask t  = new MyTask();
        t.execute();


        ////////////////////////////////////////////////////////////////////

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Coming Soon", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    ///////////////////////////////Background/////////////////////////////////////

    /////////////////////////////////Async Task/////////////////////////////////
    class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/admin_act.php";


            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();


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

            try {


                JSONArray json = new JSONArray(sb.toString());

                JSONObject jsonObj = json.getJSONObject(0);

               V_reg = jsonObj.getString("v_reg");
               C_reg = jsonObj.getString("c_reg");
               Voting  = jsonObj.getString("voting");
                Result  = jsonObj.getString("publish_res");

                edt.putString("VOTE",Voting);

                v_reg = (Button)findViewById(R.id.v_reg);
                c_reg = (Button)findViewById(R.id.nomination);
                result = (Button)findViewById(R.id.result);

                if(V_reg.equals("NO"))
                {
                    v_reg.setClickable(false);
                }

                if(C_reg.equals("NO"))
                {
                    c_reg.setClickable(false);
                }

                if(Result.equals("NO"))
                {
                    result.setClickable(false);
                }

            } catch (Exception e) {


                Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();


            }


        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(MainActivity.this, "", "Connecting to server..Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////



    ///////////////////////////////////////////////////////////////////

    public void voter_register(View view) {


        Intent voter_reg = new Intent(this,voterReg.class);

        startActivity(voter_reg);
    }

    public void login(View view) {

        Intent login = new Intent(this,login.class);

        startActivity(login);
    }

    public void CandReg(View view) {

        Intent CandReg = new Intent(this,candReg.class);

        startActivity(CandReg);
    }

    public void grievances(View view) {

        Toast.makeText(this, "Please Wait.. Fetching Data..", Toast.LENGTH_SHORT).show();

        Intent gr = new Intent(this,grievances.class);
        startActivity(gr);
    }

    public void view_result(View view) {

        Intent er = new Intent(this,election_result.class);
        startActivity(er);

    }
}
