package com.wwave.wave;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class Welcome extends AppCompatActivity {

    TextView tv_nameOfPerson;
    Button btnAbout;
    Button btnGoToFeedback;
    Button btnMovement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv_nameOfPerson = findViewById(R.id.tv_nameOfPerson);
        btnAbout = findViewById(R.id.btnAbout);
        btnMovement = findViewById(R.id.btnMovement);

        Intent info = getIntent();
        String name = info.getStringExtra("name");
        tv_nameOfPerson.setText(name);

        final Intent goAbout = new Intent(this,About.class);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goAbout);
            }
        });


        btnGoToFeedback = findViewById(R.id.btnGoToFeedback);
        btnGoToFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://redcap.vanderbilt.edu/surveys/?s=ANNLFMHKEC";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        final Intent goAccel = new Intent(this,AccelActivity.class);
        btnMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goAccel);
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