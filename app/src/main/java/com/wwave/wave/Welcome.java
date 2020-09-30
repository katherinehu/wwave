package com.wwave.wave;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toolbar;

public class Welcome extends AppCompatActivity {

    TextView tv_nameOfPerson;
    private Toolbar tb_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv_nameOfPerson = findViewById(R.id.tv_nameOfPerson);

        Intent info = getIntent();
        String name = info.getStringExtra("name");
        tv_nameOfPerson.setText(name);

        tb_toolbar = findViewById(R.id.tb_toolbar);
        setSupportActionBar(tb_toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}