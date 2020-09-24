package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView tv_nameOfPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv_nameOfPerson = findViewById(R.id.tv_nameOfPerson);

        Intent info = getIntent();
        String name = info.getStringExtra("name");
        tv_nameOfPerson.setText(name);
    }
}