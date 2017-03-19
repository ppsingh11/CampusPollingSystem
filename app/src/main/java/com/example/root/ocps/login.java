package com.example.root.ocps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by root on 13/2/17.
 */

public class login extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void voter_login(View view) {

        Intent voter_login = new Intent(this,voterLogin.class);

        startActivity(voter_login);
    }
}
