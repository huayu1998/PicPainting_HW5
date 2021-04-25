package com.example.picpainting_hw5;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static com.example.picpainting_hw5.DrawingActivity.paint_brush;
import static com.example.picpainting_hw5.DrawingActivity.path;
import static com.example.picpainting_hw5.DrawingActivity.constraintLayout;
import static com.example.picpainting_hw5.DrawingActivity.iconList;

public class MyCanvas extends View {

    /*
    HashMap <Integer, Path> activePaths;
    Paint pathPaint;
    Path path;

     */
    public static ArrayList<Path> pathList = new ArrayList<>();
    public static ArrayList<Integer> colorList = new ArrayList<>();
    public ViewGroup.LayoutParams params;
    public static int current_brush = Color.BLACK;
    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;

    public MyCanvas(Context context) {
        super(context);
        init(context);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < pathList.size(); i++) {
            paint_brush.setColor(colorList.get(i));
            canvas.drawPath(pathList.get(i), paint_brush);
            invalidate();
        }
    }

    public void init(Context context) {
        paint_brush.setAntiAlias(true);
        paint_brush.setColor(Color.BLACK);
        paint_brush.setStyle(Paint.Style.STROKE);
        paint_brush.setStrokeWidth(70);
        params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void actionDown(float x, float y) {
        pathList.add(path);
        colorList.add(current_brush);
        //path.reset();
        path.moveTo(x, y);
        mX = x;
        mY = y;
        invalidate();
    }

    public void actionMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }

        invalidate();
    }

    public void touchUp() {
        path.lineTo(mX, mY);
        invalidate();
    }

    /*
    public void actionDown(float x, float y) {
        path.moveTo(x, y);
        invalidate();
    }

    public void actionMove(float x, float y) {
        path.lineTo(x, y);
        pathList.add(path);
        colorList.add(current_brush);
        invalidate();
    }

     */

    public void undoDraw () {
        if (pathList.size() != 0) {
            pathList.remove(pathList.size() - 1);
            invalidate(); // add
        } else {
            path.reset();
            //Toast.makeText(getContext(), "Nothing to undo", Toast.LENGTH_LONG).show();
        }

    }

    /*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                pathList.add(path);
                colorList.add(current_brush);
                invalidate();
                return true;
            default:
                return false;
        }
    }

     */

    /*
    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        activePaths = new HashMap<>();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLACK);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(70);

    }

     */

    /*
    @Override
    protected void onDraw(Canvas canvas) {

        for(Path path: activePaths.values()){
            canvas.drawPath(path, pathPaint);
        }

        super.onDraw(canvas);
    }


    public void addPath(int id, float x, float y) {
        path = new Path();
        path.moveTo(x, y);
        activePaths.put(id, path);
        invalidate();
    }

    public void updatePath(int id, float x, float y) {
        path = activePaths.get(id);
        if(path != null){
            path.lineTo(x, y);
        }
        invalidate();
    }

    public void removePath(int id) {
        if(activePaths.containsKey(id)){
            activePaths.remove(id);
        }
        invalidate();
    }

     */

}
