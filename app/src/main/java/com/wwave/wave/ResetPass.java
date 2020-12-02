package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;

public class ResetPass extends AppCompatActivity {


    EditText et_pass1;
    EditText et_pass2;
    EditText et_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        //get from ui
        et_pass1 = findViewById(R.id.et_pass1);
        et_pass2 = findViewById(R.id.et_pass2);
        et_user = findViewById(R.id.et_user);



        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = pref.edit();

        edit.remove(et_user.toString());
        edit.putString(et_user,et_pass1);

        edit.putString(username+"NAME",name);
        edit.putString("nameOfUser",name);


        edit.apply();


    }
}