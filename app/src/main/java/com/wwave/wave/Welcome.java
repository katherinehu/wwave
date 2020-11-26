package com.wwave.wave;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;

public class Welcome extends AppCompatActivity {

    TextView tv_nameOfPerson;
    Button btnAbout;
    Button btnGoToFeedback;
    Button btnMovement;
    Button btnCustom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        tv_nameOfPerson = findViewById(R.id.tv_nameOfPerson);
        btnAbout = findViewById(R.id.btnAbout);
        btnMovement = findViewById(R.id.btnMovement);
        btnCustom = findViewById(R.id.btn_custom);

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

        final Intent goAccel = new Intent(this,Homee.class);
        btnMovement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goAccel);
            }
        });

        final Intent goCam = new Intent(this, ImageProcessing.class);
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(goCam);
            }
        });

    }



//
//    public void takeCustomPhoto (View v){
//        Intent intent = new Intent(this, CustomCamera.class);
//        startActivity(intent);
//    }

//    private void dispatchTakePictureIntent() {
//        Log.d(TAG, "start dispatch");
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            Log.d(TAG, "camera exists");
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//                filename.setText(currentPhotoPath);
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                //...
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Log.d(TAG, "file successfully created");
//                Log.d(TAG, "check 1");
//                Uri photoURI = getUriForFile(this,
//                        "edu.bme3890.fileprovider",  //note that this is my package
//                        photoFile);
//                // Uri photoURI = Uri.fromFile(photoFile);
//                Log.d(TAG, "check 2");
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                Log.d(TAG, "check 3");
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//                Log.d(TAG,"starting camera");
//            }
//        }
//        Log.d(TAG,"exit dispatch");
//    }



}