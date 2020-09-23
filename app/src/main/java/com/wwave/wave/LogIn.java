package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Allow users to store their own data
        SharedPreferences userInformation = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String password = userInformation.getString("agreedToTerms","this username does not exist1223323723465217513645346714");

    }
}