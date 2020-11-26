package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Homee extends AppCompatActivity implements View.OnClickListener {

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

        try {
            String result = new getUV().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.rednessCV:
                i = new Intent(this, CustomCamera.class);
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
                i = new Intent(this, EmailActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }

    }

    public class getUV extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.openuv.io/api/v1/uv?lat=-33.34&lng=115.342&dt=2018-01-24T10%3A50%3A52.283Z")
                    .get()
                    .addHeader("x-access-token", "0b1d3edc053bf726a35cddde61beb687")
                    .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Headers headers = response.headers();
//            String stringResponse = response.body().toString();
            JSONObject responseObj = null;
            try {
                responseObj = new JSONObject(response.body().string());
                System.out.println("here");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            responseObj.getJSONObject("uv").getDouble();
            int e = 6;
            return null;
        }
    }
}

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