package com.wwave.wave;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    Button btnSendEmail;
    Button btnGoToWebsite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        btnSendEmail = findViewById(R.id.btnSendEmail);
        final Intent sendEmail = new Intent(this,EmailActivity.class);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(sendEmail);
            }
        });

        btnGoToWebsite = findViewById(R.id.btnGoToWebsite);
        btnGoToWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://docs.google.com/document/d/1VTDw-DsQqZ8eiyUjsgbyr1UpM_ZmJNQ0QWuHrSHsOno/edit";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }





}
