package com.example.picpainting_hw5;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import java.util.Random;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE = 1;
    Button takePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePic = (Button) findViewById(R.id.takePic);
        takePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePicIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePicIntent, REQUEST_IMAGE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnail = (Bitmap) extras.get("data");
            Intent intent = new Intent(this, DrawingActivity.class);
            intent.putExtra("picture", thumbnail);
            startActivity(intent);
        }
    }

}