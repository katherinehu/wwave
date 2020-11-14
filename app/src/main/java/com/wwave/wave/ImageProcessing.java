package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageProcessing extends AppCompatActivity {

    //Camera stuff
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final String TAG = "Camera Activity";
    private String currentPhotoPath;
    private TextView filename;
    Button btnTakePicture;
    Bitmap imageBitmap;
    ImageView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_processing);

        //Camera stuff
        filename = findViewById(R.id.tv_filename);
        btnTakePicture = findViewById(R.id.btnTakePhoto);
        fullImage = findViewById(R.id.ivFullImage);

        final Intent takePhoto = new Intent(this,CustomCamera.class);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(takePhoto);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "entering onActivityResult");

        //generate bitmap
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);
            fullImage.setImageBitmap(imageBitmap);
        }
        Log.d(TAG, "exit onActivityResult");
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