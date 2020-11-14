package com.wwave.wave;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.FileProvider.*;

public class ImageProcessing extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final String TAG = "Camera Activity";
    private ImageView thumbnail, fullImage;
    private String currentPhotoPath;
    private TextView filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_processing);

        filename = (TextView) findViewById(R.id.tv_filename);
        thumbnail = (ImageView) findViewById(R.id.iv_thumbnail);
        fullImage = (ImageView) findViewById(R.id.iv_fullImage);

        ImageButton photoBtn = (ImageButton) findViewById(R.id.btn_takePiccture);
        Log.d(TAG, "activity created");
    }

   /*private void dispatchTakePictureIntent() {
        Log.d(TAG, "enter easy dispatch");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
        Log.d(TAG, "exit easy dispatch");
    }*/

    public void takePhoto(View v){
        Log.d(TAG, "start takePhoto");
        dispatchTakePictureIntent();
    }

    public void takeCustomPhoto (View v){
        Intent intent = new Intent(this, CustomCamera.class);
        startActivity(intent);
    }

    private void dispatchTakePictureIntent() {
        Log.d(TAG, "start dispatch");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "camera exists");
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                filename.setText(currentPhotoPath);
            } catch (IOException ex) {
                // Error occurred while creating the File
                //...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d(TAG, "file successfully created");
                Log.d(TAG, "check 1");
                Uri photoURI = getUriForFile(this,
                        "com.wwave.wave",
                        photoFile);
                // Uri photoURI = Uri.fromFile(photoFile);
                Log.d(TAG, "check 2");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d(TAG, "check 3");
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                Log.d(TAG,"starting camera");
            }
        }
        Log.d(TAG,"exit dispatch");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"entering onActivityResult");

        //handle case for thumbnail image only
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            thumbnail.setImageBitmap(imageBitmap);
        }

        //handle for full image case
        else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            fullImage.setImageBitmap(imageBitmap);
        }
        Log.d(TAG,"exit onActivityResult");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  ///* prefix */
                ".jpg",  //       /* suffix */
                storageDir     // /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}