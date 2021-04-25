package com.example.picpainting_hw5;
//import android.support.v4.view.GestureDetectorCompat;
import android.media.MediaPlayer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.GestureDetectorCompat;
import static com.example.picpainting_hw5.DrawingActivity.paint_brush;
import static com.example.picpainting_hw5.DrawingActivity.path;

public class TouchListener implements View.OnTouchListener {

    // implements View.OnTouchListener

    DrawingActivity drawingActivity;
    GestureDetectorCompat gestureDetectorCompat;

    public TouchListener(DrawingActivity ma) {
        this.drawingActivity = ma;
        gestureDetectorCompat = new GestureDetectorCompat(this.drawingActivity, new MyGestureListener());
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetectorCompat.onTouchEvent(motionEvent);
        int maskedAction = motionEvent.getActionMasked();
        switch(maskedAction){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                drawingActivity.actionDown(motionEvent.getX(), motionEvent.getY());
                /*
                for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
                    int id = motionEvent.getPointerId(i);
                    drawingActivity.addPath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;

                 */
            case MotionEvent.ACTION_MOVE:
                drawingActivity.actionMove(motionEvent.getX(), motionEvent.getY());
                /*
                for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
                    int id = motionEvent.getPointerId(i);
                    drawingActivity.updatePath(id, motionEvent.getX(i), motionEvent.getY(i));
                }
                break;

                 */
            case MotionEvent.ACTION_UP:
                drawingActivity.touchUp();
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                //for(int i= 0, size = motionEvent.getPointerCount(); i< size; i++){
                    //int id = motionEvent.getPointerId(i);
                    //drawingActivity.removePath(id);
                //}
                //break;
        }
        return true;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            drawingActivity.onDoubleTap(e.getX(), e.getY());
            return super.onDoubleTap(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            drawingActivity.onLongPress(e.getX(), e.getY());
            super.onLongPress(e);
        }
    }

}
