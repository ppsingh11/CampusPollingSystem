package com.example.root.ocps;



        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.LinearLayout;
        import android.widget.Toast;

        import com.github.mikephil.charting.charts.BarChart;
        import com.github.mikephil.charting.data.BarData;
        import com.github.mikephil.charting.data.BarDataSet;
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

public class grievances extends AppCompatActivity {
    BarChart barChart;
    String wifi , laundry,food,sweeping,water,total_voters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grievances);


        barChart=(BarChart)findViewById(R.id.bargraph);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        BarDataSet barDataSet =new BarDataSet(barEntries,"graves");
        ArrayList<String> graves = new ArrayList<>();
        graves.add("Wifi");
        graves.add("Food");
        graves.add("Water");
        graves.add("Laundry");
        graves.add("Sweeping");


        //////////////////////////////////////////////////////////////////////
        String link = "https://onlinevotingnitp.000webhostapp.com/grievances.php";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);




            try {

                URL url = new URL(link);
                URLConnection conn = url.openConnection();


                ////////////////////////////////////



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




                JSONArray json = new JSONArray(sb.toString());

                JSONObject jsonObj;
                jsonObj = json.getJSONObject(0);
                 wifi = jsonObj.getString("WIFI");


                jsonObj = json.getJSONObject(1);
                 laundry = jsonObj.getString("LAUNDRY");

                jsonObj = json.getJSONObject(2);
                food = jsonObj.getString("FOOD");

                jsonObj = json.getJSONObject(3);
                 sweeping = jsonObj.getString("SWEEPING");

                jsonObj = json.getJSONObject(4);
                 water = jsonObj.getString("WATER");

                jsonObj = json.getJSONObject(5);
                total_voters = jsonObj.getString("total");











            } catch (Exception e) {

                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }






        ///////////////////////////////////////////////////////////////////////

        int w;
        w = (Integer.parseInt(wifi)/Integer.parseInt(total_voters))*100;


        barEntries.add(new BarEntry(w,0));
        w = (Integer.parseInt(food)/Integer.parseInt(total_voters))*100;
        barEntries.add(new BarEntry(w,1));

        w = (Integer.parseInt(water)/Integer.parseInt(total_voters))*100;
        barEntries.add(new BarEntry(w,2));

        w = (Integer.parseInt(laundry)/Integer.parseInt(total_voters))*100;
        barEntries.add(new BarEntry(w,3));

        w = (Integer.parseInt(sweeping)/Integer.parseInt(total_voters))*100;
        barEntries.add(new BarEntry(w,4));



        BarData theData;
        theData = new BarData(graves,barDataSet);
        barChart.setData(theData);

    }

}
