package com.example.drawboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawBoard extends View {

    private Paint mpaint = new Paint();

    private Path path;
    private float mLastX;
    private float mLastY;
    private int paintSize = 20;
    private int paintColor = 0xFF000000;

    private List<Path> lastList = new ArrayList<>();
    private List<Path> pathList = new ArrayList<>();
    private List<Integer> paintList = new ArrayList<>();

    public DrawBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        pathList.clear();
        mpaint.setStrokeWidth(paintSize);
        mpaint.setColor(paintColor);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setAntiAlias(true);
        mpaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if(currentLocation>0&&currentLocation<pathList.size()){
//        if(!ischange)
//            currentLocation=paintList.size();
//            for (int i=0;i<currentLocation;i++) {
//                Path path1=pathList.get(i);
//                Paint paint1=new Paint();
//                paint1.setStrokeWidth(paintSize);
//                paint1.setColor(paintList.get(i));
//                paint1.setStyle(Paint.Style.STROKE);
//                paint1.setAntiAlias(true);
//                paint1.setStrokeCap(Paint.Cap.ROUND);
//                canvas.drawPath(path1, paint1);
//            }
//        }
//        else {
//            for (int i = 0; i < pathList.size(); i++) {
//                Path path1 = pathList.get(i);
//                canvas.drawPath(path1, mpaint);
//            }
//            currentLocation=pathList.size()-1;
//        }
        for (int i = 0; i < pathList.size(); i++) {
            Path path1 = pathList.get(i);
            Paint paint1 = new Paint();
            paint1.setStrokeWidth(paintSize);
            paint1.setColor(paintList.get(i));
            paint1.setStyle(Paint.Style.STROKE);
            paint1.setAntiAlias(true);
            paint1.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawPath(path1, paint1);

        }
        canvas.save();
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path = new Path();
            pathList.add(path);
            paintList.add(mpaint.getColor());
            path.moveTo(event.getX(), event.getY());

            mLastX = x;
            mLastY = y;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
            mLastX = x;
            mLastY = y;
        }
        invalidate();
        return true;
    }

    public void changePaintSize(int n) {
        this.paintSize = n;
        invalidate();
    }

    public void changePaintColor(int color) {
        this.paintColor = color;
        invalidate();
    }

    public void goback() {
        if (pathList.size() > 0) {
            lastList.add(pathList.get(pathList.size() - 1));
            pathList.remove(pathList.size() - 1);
        }
        invalidate();
    }

    public void gonext() {
        if (lastList.size() > 0) {
            pathList.add(lastList.get(0));
            lastList.remove(0);
            invalidate();
        }
    }

    public void seteraser() {
        mpaint.setColor(Color.WHITE);
        invalidate();
    }

    public void setpen() {
        mpaint.setStrokeWidth(paintSize);
        mpaint.setColor(paintColor);
        invalidate();
    }
}
