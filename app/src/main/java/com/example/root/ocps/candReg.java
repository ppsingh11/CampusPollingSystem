package com.example.root.ocps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by root on 13/2/17.
 */

public class candReg extends Activity {

    Spinner spn , spn_party;
    ImageView Image , Party_symbol;
    Bitmap pic,party_sym;
    String id;
    EditText Roll,Reason,Email;
    TextView Name,Dept,Sem;
    ArrayList<String> Promises_list = new ArrayList<String>();
    ProgressDialog pd;

    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_reg);
        Reason = (EditText)findViewById(R.id.election_reason);
        Email = (EditText)findViewById(R.id.mail);

        Roll = (EditText)findViewById(R.id.roll);
        Name = (TextView)findViewById(R.id.name);
        Dept = (TextView)findViewById(R.id.dept);
        Sem = (TextView)findViewById(R.id.sem);

        Image = (ImageView)findViewById(R.id.image) ;
        Party_symbol = (ImageView)findViewById(R.id.party_symbol);

        spn = (Spinner)findViewById(R.id.nominatingfor);
        ArrayList<String> Values = new ArrayList<String>();
        Values.add("Nominating For");
        Values.add("Hostel-President");
        Values.add("Hostel-Vice President");
        Values.add("College-President");
        Values.add("College-Vice President");
        ArrayAdapter<String> my_adp = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Values);
        spn.setAdapter(my_adp);


        spn_party = (Spinner)findViewById(R.id.party);
        ArrayList<String> Values_party = new ArrayList<String>();

        Values_party.add("Select Your Party");
        Values_party.add("AAP");
        Values_party.add("BJP");
        Values_party.add("Congress");
        Values_party.add("Individual");

        ArrayAdapter<String> my_adp_party = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Values_party);
        spn_party.setAdapter(my_adp_party);


    }

    ///////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                pic = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                Image.setImageBitmap(pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else
        {
            Uri uri = data.getData();

            try {
                party_sym = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));


                Party_symbol.setImageBitmap(party_sym);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////
class MyTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {

        String link = "https://onlinevotingnitp.000webhostapp.com/users_record.php";

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
        Roll.setFocusable(false);
        try {

            JSONArray json = new JSONArray(sb.toString());

            JSONObject jsonObj = json.getJSONObject(0);

            // Getting JSON Array node
            //JSONArray contacts = jsonObj.getJSONArray("contacts");
            String name = jsonObj.getString("name");
            String dept = jsonObj.getString("department");
            String semester = jsonObj.getString("semester");

            // Search.setText("SEARCH");


            LinearLayout l = (LinearLayout) findViewById(R.id.Search_layout);
            l.setVisibility(View.VISIBLE);

            Name.setText(name);
            Dept.setText("Dept :" +dept);
            Sem.setText("Sem :"+semester);


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();


        }


    }

    @Override
    protected void onPreExecute() {
        pd = ProgressDialog.show(candReg.this, "", "Please Wait", false);

    }



}

    ///////////////////////////Task End//////////////////////////////////


    /////////////////////////////////Async Task Submit/////////////////////////////////
    class SUBMIT extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {

            String link = "https://onlinevotingnitp.000webhostapp.com/candidates_reg.php";

            String c_data = params[0];

            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(c_data);

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

                AlertDialog.Builder builder = new AlertDialog.Builder(candReg.this);
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
            pd = ProgressDialog.show(candReg.this, "", "Please Wait", false);

        }



    }

    ///////////////////////////Task End//////////////////////////////////




    ///////////////////////////////

    public void Promises(final View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You can add upto 5 Promises");
        builder.setCancelable(false);
        builder.setTitle("Message");

        builder.setPositiveButton(
                "OK, Got it!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LinearLayout p_layout = (LinearLayout)findViewById(R.id.promise_layout);
                        p_layout.setVisibility(view.VISIBLE);
                        dialog.cancel();
                    }
                });



        AlertDialog alert11 = builder.create();
        alert11.show();


    }

    public void Add_promise(View view) {

        EditText promise = (EditText)findViewById(R.id.add_promise);
        String Promise = promise.getText().toString();

        if(i<=5)
        {
            Promises_list.add(Promise);
            promise.setText("");
            i++;
            if(i==6)
            {
                promise.setHint("All Promises Added");
                promise.setFocusable(false);
                Button btn = (Button)findViewById(R.id.promise_add);
                btn.setClickable(false);
            }
            else
            {
                promise.setHint("Promise "+i);
                Toast.makeText(this,"Promise Added",Toast.LENGTH_SHORT).show();
            }


        }



    }

    public void pic_img(View view) {


        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        startActivityForResult(chooserIntent,1);


    }

    public void pick_symbol(View view) {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        startActivityForResult(chooserIntent,2);
    }

    public void Search_roll(View view) {


        id = Roll.getText().toString();

        if(id.isEmpty())
        {
            Toast.makeText(this,"Enter Roll Number", Toast.LENGTH_SHORT).show();
        }
        else {

            new MyTask().execute(id);
        }
    }



    //////////////////////////////////////////////////////////////////////////////////////////////////
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////

    public void submit(View view) {

        String post = spn.getSelectedItem().toString();
        String party  = spn_party.getSelectedItem().toString();
       String image = getStringImage(pic);
        String p_symbol = getStringImage(party_sym);

       String reason = Reason.getText().toString();
        String email = Email.getText().toString();


        String p_list = "";

        for (String s : Promises_list )
        {
            p_list += s + "`";
        }


        try {



            String data = URLEncoder.encode("ID", "UTF-8") + "=" +
                    URLEncoder.encode(id, "UTF-8");

            data += "&" + URLEncoder.encode("POST", "UTF-8")
                    + "=" + URLEncoder.encode(post, "UTF-8");

           data += "&" + URLEncoder.encode("PARTY", "UTF-8")
                   + "=" + URLEncoder.encode(party, "UTF-8");

            data += "&" + URLEncoder.encode("C_IMAGE", "UTF-8")
                    + "=" + URLEncoder.encode(image, "UTF-8");

           data += "&" + URLEncoder.encode("P_SYM", "UTF-8")
                 + "=" + URLEncoder.encode(p_symbol, "UTF-8");

            data += "&" + URLEncoder.encode("REASON", "UTF-8")
                    + "=" + URLEncoder.encode(reason, "UTF-8");
            data += "&" + URLEncoder.encode("EMAIL", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");
         data += "&" + URLEncoder.encode("PROMISES", "UTF-8")
                 + "=" + URLEncoder.encode(p_list, "UTF-8");
            ////////////////////////////////////
            new SUBMIT().execute(data);



        } catch (Exception e) {

            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();

        }



    }
}
