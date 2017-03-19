package com.example.root.ocps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Prashant Pratap on 26-02-2017.
 */

public class voterLogin extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voter_login);
    }

    public void cand_info(View view) {

        Intent cand_info = new Intent(this,candidateInfo.class);

        startActivity(cand_info);
    }
}
