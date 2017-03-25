package com.example.root.ocps;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Prashant Pratap on 25-03-2017.
 */

public class custom_cand_list extends BaseAdapter {

    vote_now VOTE;
    TextView c_name,c_roll;
    ImageView p_sym;
    Button cast_vote;
    ProgressDialog pd;

    Context mContext;

    ArrayList<String> Candidate_name = new ArrayList<String>();
    ArrayList<String> Candidate_roll = new ArrayList<String>();
    ArrayList<String> Party_symbol = new ArrayList<String>();


    //////////////connecting to server to fetch info//////////////////
    /////////////////////////////////Async Task Submit/////////////////////////////////
    class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/candidate_list_for_voting.php";


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

                reader.close();

                return sb.toString();


            }
            catch (Exception e) {

                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
                return e.toString();
            }

        }


        @Override
        protected void onPostExecute(String sb) {
            pd.dismiss();

            try {



                JSONArray json = new JSONArray(sb.toString());
                JSONObject jsonObj;
                String c_name,p_sym,c_id;


                Toast.makeText(mContext, ""+json.length(), Toast.LENGTH_SHORT).show();

              //  for(int p=0;p<json.length();p++)
                {
                  /*  jsonObj = json.getJSONObject(p);
                    p=p+1;
                    c_id = jsonObj.getString("ID");
                    Candidate_roll.add(c_id);
                    p_sym = jsonObj.getString("P_SYM");
                    Party_symbol.add(p_sym);
                    jsonObj = json.getJSONObject(p);
                    p=p+1;
                    c_name = jsonObj.getString("Name");
                    Candidate_name.add(c_name);
                    */
                    Candidate_name.add("PPS");
                    Candidate_roll.add("1406014");
                }



            }
            catch (Exception e)
            {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(mContext, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////



    /////////////////connection closed//////////////////////////////


    custom_cand_list(vote_now v,Context context)
    {
        VOTE = v;
        mContext = context;
        try {
            String str = new MyTask().execute("hi").get();
        }
        catch (Exception e)
        {

        }

    // Candidate_name.add("PPS");
     //   Candidate_roll.add("1406014");


    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = LayoutInflater.from(VOTE);
        View v = inf.inflate(R.layout.custom_cand_list, null);

        c_name = (TextView)v.findViewById(R.id.candidate_name);
        c_roll = (TextView)v.findViewById(R.id.candidate_roll);

        c_name.setText(Candidate_name.get(position));
        c_roll.setText(Candidate_roll.get(position));

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



