package com.wwave.wave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Homee extends AppCompatActivity implements View.OnClickListener {

    private CardView rednessCV, accelerationCV, aboutCV, feedbackCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //define the cards
        rednessCV = (CardView) findViewById(R.id.rednessCV);
        accelerationCV = (CardView) findViewById(R.id.accelerationCV);
        aboutCV = (CardView) findViewById(R.id.aboutCV);
        feedbackCV = (CardView) findViewById(R.id.feedbackCV);


        //set on click to the cards
        rednessCV.setOnClickListener(this);
        accelerationCV.setOnClickListener(this);
        aboutCV.setOnClickListener(this);
        feedbackCV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.rednessCV:
                i = new Intent(this, UVSkin.class);
                startActivity(i);
                break;
            case R.id.accelerationCV:
                i = new Intent(this, AccelActivity.class);
                startActivity(i);
                break;
            case R.id.aboutCV:
                i = new Intent(this, About.class);
                startActivity(i);
                break;
            case R.id.feedbackCV:
                String url = "https://redcap.vanderbilt.edu/surveys/?s=ANNLFMHKEC";
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
            default:
                break;
        }

    }
}