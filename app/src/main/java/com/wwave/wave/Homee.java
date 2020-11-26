package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jjoe64.graphview.Viewport;

import java.io.IOException;

public class Homee extends AppCompatActivity implements View.OnClickListener{

    private CardView rednessCV, accelerationCV, aboutCV, feedbackCV;
    private TextView uvIndex;
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

        uvIndex = findViewById(R.id.uvIndex);

        this.getUV();

    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.rednessCV: i =  new Intent(this, CustomCamera.class);startActivity(i);break ;
            case R.id.accelerationCV: i =  new Intent(this, AccelActivity.class);startActivity(i);break ;
            case R.id.aboutCV: i =  new Intent(this, About.class);startActivity(i);break ;
            case R.id.feedbackCV: i =  new Intent(this, EmailActivity.class);startActivity(i);break ;
            default: break ;
        }

    }

    public void getUV() {


//        Document doc;
//
//        try {
//            doc = Jsoup.connect("https://www.vogella.com/").get();
//
//            Element element = doc.select("div[aria-live]").first();
//            uvIndex.setText(element.text());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}