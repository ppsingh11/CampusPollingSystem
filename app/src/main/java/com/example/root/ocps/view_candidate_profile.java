package com.example.root.ocps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Prashant Pratap on 25-03-2017.
 */

public class view_candidate_profile extends Activity {

    String s;
    TextView name,roll,dept,sem,post,party,reason;
    ProgressDialog pd;
    ImageView PIC, SYM;
    ListView prev_w_l;
    ScrollView scr;

    ArrayAdapter<String> my_adp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_candidate_profile);

        s = getIntent().getStringExtra("ROLL");

        Toast.makeText(this,"roll "+s,Toast.LENGTH_SHORT).show();
        ////////////////////////////////////////////////////////////
        name = (TextView)findViewById(R.id.name);
        roll = (TextView)findViewById(R.id.roll);
        dept = (TextView)findViewById(R.id.dept);
        sem = (TextView)findViewById(R.id.semester);
        post = (TextView)findViewById(R.id.post);
        party = (TextView)findViewById(R.id.party);
        reason = (TextView)findViewById(R.id.reason);
        PIC = (ImageView)findViewById(R.id.pic);
        SYM = (ImageView)findViewById(R.id.sym);
        prev_w_l = (ListView)findViewById(R.id.previous_work_list);
        scr = (ScrollView)findViewById(R.id.scr_view);




        //////////////////////////////////////////////////////////////

        new MyTask().execute(s);


    }

    ///////////////////////////////////






    /////////////////////////////////Async Task/////////////////////////////////
    class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/candidate_profile.php";
            String id= params[0];

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
                JSONObject jsonObj;

                 jsonObj = json.getJSONObject(0);

                String c_name = jsonObj.getString("name");
                String c_dept = jsonObj.getString("department");
                String c_semester = jsonObj.getString("semester");

                 jsonObj = json.getJSONObject(1);

                String c_post = jsonObj.getString("post");
                String c_party = jsonObj.getString("party");
                String c_img = jsonObj.getString("c_image");
                String p_sym = jsonObj.getString("p_sym");
                String c_promises = jsonObj.getString("promises");
                String c_reason = jsonObj.getString("reason");



                name.setText(c_name);
                roll.setText(s);
                dept.setText("Dept: " + c_dept);
                sem.setText("Sem: " + c_semester);
                post.setText("Nominating For: "+c_post);
                party.setText("Party: " +c_party);
                reason.setText("Election Reason: "+c_reason);

                byte[] decodedString = Base64.decode(c_img, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                PIC.setImageBitmap(decodedByte);

                byte[] decodedString1 = Base64.decode(p_sym, Base64.DEFAULT);
                Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString.length);
                SYM.setImageBitmap(decodedByte1);

        ///////////////////////////////////////////////
           //   ArrayList<String> l = new ArrayList<String>();
                List<String> l = new ArrayList<String>(Arrays.asList(c_promises.split("`")));





               ViewGroup.LayoutParams params = prev_w_l.getLayoutParams();

                my_adp = new ArrayAdapter<String>(view_candidate_profile.this,android.R.layout.simple_dropdown_item_1line,l);

                params.height = 99* (my_adp.getCount());
                prev_w_l.setAdapter(my_adp);

                scr.setVisibility(View.VISIBLE);



        ////////////////////////////////////////////




            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();


            }


        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(view_candidate_profile.this, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////





    //////////////////////////////////





    public void go_back(View view) {

        finish();
    }
}
