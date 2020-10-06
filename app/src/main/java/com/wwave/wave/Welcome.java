package com.wwave.wave;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class Welcome extends AppCompatActivity {

    TextView tv_nameOfPerson;
    Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Load name to display
        final SharedPreferences userInformation = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name = userInformation.getString("nameOfUser","you shouldn't see this message");

        tv_nameOfPerson = findViewById(R.id.tv_nameOfPerson);
        btnAbout = findViewById(R.id.btnAbout);

        tv_nameOfPerson.setText(name);

        final Intent goAbout = new Intent(this,About.class);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goAbout);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
}