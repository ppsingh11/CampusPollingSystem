package com.example.root.ocps;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;

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
 * Created by Prashant Pratap on 25-03-2017.
 */

public class customized_list_for_result extends BaseAdapter {

    custom_result VOTE;
    int flag=0;
    TextView c_name,c_roll,votes;

    ImageView c_img;

    ProgressDialog pd;
    String pst;


    Context mContext;

    ArrayList<String> Candidate_name = new ArrayList<String>();
    ArrayList<String> Candidate_roll = new ArrayList<String>();
    ArrayList<String> Party_symbol = new ArrayList<String>();
    ArrayList<String> total_votes = new ArrayList<String>();






    //////////////connecting to server to fetch info//////////////////
    /////////////////////////////////Async Task Submit/////////////////////////////////
    class result extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/result_list.php";

            String p = params[0];

            try {


                String data = URLEncoder.encode("POST", "UTF-8") + "=" +
                        URLEncoder.encode(p, "UTF-8");


                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);

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


            }
            catch (Exception e) {

                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return e.toString();
            }

        }
////////////////////////////////   post execute     /////////////////////////////


        @Override
        protected void onPostExecute(String sb) {
            pd.dismiss();
            flag=1;

            try {



                JSONArray json = new JSONArray(sb.toString());
                JSONObject jsonObj;
                String c_name,p_sym,c_id,t_votes;


                //  Toast.makeText(mContext, ""+json.length(), Toast.LENGTH_SHORT).show();

                for(int p=0;p<json.length();)
                {
                    jsonObj = json.getJSONObject(p);
                    p=p+1;
                    c_id = jsonObj.getString("ID");
                    Candidate_roll.add(c_id);

                    t_votes =jsonObj.getString("VOTES");
                    total_votes.add(t_votes);

                    p_sym = jsonObj.getString("C_IMG");
                    Party_symbol.add(p_sym);
                    jsonObj = json.getJSONObject(p);
                    p=p+1;
                    c_name = jsonObj.getString("Name");
                    Candidate_name.add(c_name);


                }





            }
            catch (Exception e)
            {
                Toast.makeText(mContext, ""+e.toString(), Toast.LENGTH_SHORT).show();
            }


        }
        //////////////////////////////////////post execution finished///////////////////////////////////////
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(mContext, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////


    //////////////connecting to server to cast vote//////////////////


    public int fact(int a)
    {
        int res=1;
        if(a==0 || a==1)
            return 1;
        else
            return a*fact(a-1);

    }


    customized_list_for_result(custom_result v,Context context,String str)
    {
        VOTE = v;
        pst = str;

        mContext = context;
        result t = new result();
        t.execute(pst);

        // Toast.makeText(mContext,"p "+post,Toast.LENGTH_SHORT).show();
        int data = 400;
        int result = fact(data);
        try {

            t.get();
        }
        catch (Exception e)
        {
            Toast.makeText(mContext,e.toString(),Toast.LENGTH_SHORT).show();
        }

    }



    /////////////////connection closed//////////////////////////////




    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = LayoutInflater.from(VOTE);
        View v = inf.inflate(R.layout.custom_result_list, null);





        c_name = (TextView)v.findViewById(R.id.candidate_name);
        c_roll = (TextView)v.findViewById(R.id.candidate_roll);
        votes = (TextView)v.findViewById(R.id.total_votes);

        c_img = (ImageView)v.findViewById(R.id.cand_picture);

        byte[] decodedString = Base64.decode(Party_symbol.get(position), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        c_img.setImageBitmap(decodedByte);


        c_name.setText(Candidate_name.get(position));
          c_roll.setText(Candidate_roll.get(position));
        votes.setText(total_votes.get(position));

        return v;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return Candidate_name.size();
    }





}



