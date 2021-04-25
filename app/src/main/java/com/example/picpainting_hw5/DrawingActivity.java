package com.example.picpainting_hw5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.VibrationEffect;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static com.example.picpainting_hw5.MyCanvas.colorList;
import static com.example.picpainting_hw5.MyCanvas.current_brush;
import static com.example.picpainting_hw5.MyCanvas.pathList;

public class DrawingActivity extends AppCompatActivity {


    MyCanvas myCanvas;
    TouchListener touchListener;
    Random rd = new Random();
    static final int REQUEST_IMAGE= 1;
    public static ConstraintLayout constraintLayout;
    public static ArrayList<ImageView> iconList = new ArrayList<>();
    MediaPlayer mp;

    Button doneButton;
    Button redButton;
    Button blueButton;
    Button greenButton;
    Button undoButton;

    public static Path path = new Path();
    public static Paint paint_brush = new Paint();
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        myCanvas = (MyCanvas) findViewById(R.id.myCanvas);
        touchListener = new TouchListener(this);
        constraintLayout = (ConstraintLayout) findViewById(R.id.layout);

        // Set on touch listner on the myCanvas
        myCanvas.setOnTouchListener(touchListener);

        // Get the bitmap/pic source from mainActivity and
        // set myCanvas's background with the image
        Bitmap bitmap = getIntent().getParcelableExtra("picture");
        myCanvas.setBackground(new BitmapDrawable(getResources(), bitmap));

        // When done drawing, change back to mainActivety
        doneButton = (Button) findViewById(R.id.done);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pathList.clear();
                colorList.clear();
                path.reset();
                paint_brush.setColor(Color.BLACK);
                currentColor(paint_brush.getColor());
                finish();
            }
        });

        // Handle the color of the painting
        redButton = (Button) findViewById(R.id.red);
        blueButton = (Button) findViewById(R.id.blue);
        greenButton = (Button) findViewById(R.id.green);
        undoButton = (Button) findViewById(R.id.undo);

        /*
        myCanvas.setOnTouchListener(new View.OnTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    myCanvas.setBackgroundColor(Color.rgb(rd.nextInt(255), rd.nextInt(255), rd.nextInt(255)));
                    return super.onDoubleTap(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Toast.makeText(getApplicationContext(), "Long press", Toast.LENGTH_LONG).show();
                    super.onLongPress(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

         */
    }

    public void currentColor(int c) {
        current_brush = c;
        path = new Path();
    }

    public void clear(View view) {
        pathList.clear();
        for (int i = 0; i < iconList.size(); i++) {
            constraintLayout.removeView(iconList.get(i));
        }
        colorList.clear();
        path.reset();
        paint_brush.setColor(Color.BLACK);
        currentColor(paint_brush.getColor());
        //Toast.makeText(this, "Black Painting Pen", Toast.LENGTH_LONG).show();
    }

    public void setRed(View view) {
        //myCanvas.pathPaint.setColor(Color.RED);
        paint_brush.setColor(Color.RED);
        currentColor(paint_brush.getColor());
        //Toast.makeText(this, "Red Painting Pen", Toast.LENGTH_LONG).show();
    }

    public void setBlue(View view) {
        //myCanvas.pathPaint.setColor(Color.BLUE);
        paint_brush.setColor(Color.BLUE);
        currentColor(paint_brush.getColor());
        //Toast.makeText(this, "Blue Painting Pen", Toast.LENGTH_LONG).show();
    }

    public void setGreen(View view) {
        //myCanvas.pathPaint.setColor(Color.GREEN);
        paint_brush.setColor(Color.GREEN);
        currentColor(paint_brush.getColor());
        //Toast.makeText(this, "Green Painting Pen", Toast.LENGTH_LONG).show();
    }

    public void undo(View view) {
        this.undoIcon();
        myCanvas.undoDraw();
    }

    public void undoIcon() {
        if (iconList.size() > 0) {
            constraintLayout.removeView(iconList.get(iconList.size() - 1));
            iconList.remove(iconList.size() - 1);
        }
    }

    public void actionDown(float x, float y) {
        mp = MediaPlayer.create(DrawingActivity.this, R.raw.clapping);
        myCanvas.actionDown(x, y);
    }

    public void actionMove(float x, float y) {
        mp.start();
        myCanvas.actionMove(x, y);
    }

    public void touchUp() {
        mp.pause();
        myCanvas.touchUp();
    }

    public void onDoubleTap(float x, float y) {
        //Toast.makeText(this, "double tap", Toast.LENGTH_SHORT).show();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_baseline_masks_50);
        imageView.setX(x);
        imageView.setY(y);
        constraintLayout.addView(imageView);
        iconList.add(imageView);

        RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        //anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1000);

        // Start animating the image
        imageView.startAnimation(anim);

        // Later.. stop the animation
        //imageView.setAnimation(null);
    }

    public void onLongPress(float x, float y) {
        //Toast.makeText(this, "long press", Toast.LENGTH_SHORT).show();
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.ic_baseline_ac_unit_60);
        imageView.setX(x);
        imageView.setY(y);
        constraintLayout.addView(imageView);
        iconList.add(imageView);

        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vib);

        imageView.startAnimation(shake); // starts animation

    }

    /*
    public void addPath(int id, float x, float y) {
        myCanvas.addPath(id, x, y);
    }

    public void updatePath(int id, float x, float y) {
        myCanvas.updatePath(id, x, y);
    }


    public void keepPath(int id, float x, float y) {
        myCanvas.keepPath(id);
    }

    //public void removePath(int id) {
        //myCanvas.removePath(id);
    //}

     */


}