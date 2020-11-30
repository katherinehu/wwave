package com.wwave.wave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.wwave.wave.ImageProcessing.REQUEST_IMAGE_CAPTURE;
import static com.wwave.wave.ImageProcessing.REQUEST_TAKE_PHOTO;

public class UVSkin extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    Button btnTakePhoto, displayColor;
    ImageView ivSkinPreview;
    TextView tvEstimatedSkin;
    //

    final int TAKE_PICTURE = 1235;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_v_skin);

        //Declare ui elements
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        displayColor = findViewById(R.id.displayColor);
        ivSkinPreview = findViewById(R.id.ivSkinPreview);
        tvEstimatedSkin = findViewById(R.id.tvEstimatedSkin);

        //Ask for permission from the user to use the camera if the permission wasn't granted
        if(!allPermissionsGranted()){
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        //Button takes a photo, sends the thumbnail to use, we don't need the whole thing, technically all we need
        //is just 1 pixel in order to figure out the color, therefore out of laziness its easiest to just grab thumbnail
        //rather than deal with storage and etc
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, TAKE_PICTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        //Here's where the fun begins
                        ivSkinPreview.setImageBitmap(bitmap);
                        //The color types of each skin type
                        int[] I   = {241,209,177};
                        int[] II  = {228,181,144};
                        int[] III = {207,159,125};
                        int[] IV  = {182,120,81};
                        int[] V   = {161,94,45};
                        int[] VI  = {81,57,56};

                        ArrayList<int[]> skinTypes = new ArrayList<>();
                        skinTypes.add(I);
                        skinTypes.add(II);
                        skinTypes.add(III);
                        skinTypes.add(IV);
                        skinTypes.add(V);
                        skinTypes.add(VI);

                        //Calculate the average color of the thumbnail
                        int height = bitmap.getHeight();
                        int width = bitmap.getWidth();
                        int[] pixels = new int[height*width];
                        bitmap.getPixels(pixels,0,width,0,0,width,height);
                        long totalRed = 0;
                        long totalBlue = 0;
                        long totalGreen = 0;
                        for (int counter = 0; counter < height*width; ++counter) {
                            int red, blue, green;
                            red = Color.red(pixels[counter]);
                            blue = Color.blue(pixels[counter]);
                            green = Color.green(pixels[counter]);
                            totalRed += red;
                            totalGreen += green;
                            totalBlue += blue;
                        }

                        int red = (int)(totalRed/(height*width));
                        int green = (int)(totalGreen/(height*width));
                        int blue = (int)(totalBlue/(height*width));

                        //Make the button this color so the user can see
                        displayColor.setBackgroundColor(Color.rgb(red,green,blue));

                        ArrayList<Integer> differences = new ArrayList<>();
                        for (int[] types: skinTypes){
                            //Square differences, so that values further away are punished more
                            int redDifference = (types[0] - red) * (types[0] - red);
                            int greenDifference = (types[1] - green) * (types[1] - green);
                            int blueDifference = (types[2] - blue) * (types[2] - blue);
                            differences.add(redDifference + greenDifference + blueDifference);
                        }

                        //find the smallest of the differences
                        int currentSmallest = -1;
                        int smallestValueSoFar = 2000000000;
                        for(int i = 0; i < differences.size(); ++i) {
                            if (differences.get(i) < smallestValueSoFar) {
                                smallestValueSoFar = differences.get(i);
                                currentSmallest = i;
                            }
                        }

                        switch (currentSmallest) {
                            case 0:
                                tvEstimatedSkin.setText("Your skin type: I");
                                break;
                            case 1:
                                tvEstimatedSkin.setText("Your skin type: II");
                                break;
                            case 2:
                                tvEstimatedSkin.setText("Your skin type: III");
                                break;
                            case 3:
                                tvEstimatedSkin.setText("Your skin type: IV");
                                break;
                            case 4:
                                tvEstimatedSkin.setText("Your skin type: V");
                                break;
                            case 5:
                                tvEstimatedSkin.setText("Your skin type: VI");
                                break;
                        }
                     }

                }
                break;
        }
    }

    //Check to see if user has allowed camera use
    private boolean allPermissionsGranted(){
        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}