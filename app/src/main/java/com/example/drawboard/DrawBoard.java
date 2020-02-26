package com.example.drawboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawBoard extends View {

    private Paint mpaint=new Paint();

    private Canvas mcanvas;
    private Path path;
    private float mLastX;
    private float mLastY;
    private List<Path> pathList = new ArrayList<>();


    public DrawBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mcanvas = new Canvas();
        mpaint.setColor(Color.BLACK);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(20);
        mpaint.setAntiAlias(true);
        mpaint.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Path path1 : pathList) {
            canvas.drawPath(path1, mpaint);
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
            path.moveTo(event.getX(), event.getY());
            mLastX = x;
            mLastY = y;
            invalidate();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            path.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
            mLastX = x;
            mLastY = y;
            invalidate();
        }
//        if (event.getAction()==MotionEvent.ACTION_UP){
//            path.quadTo(mLastX, mLastY, (mLastX + x) / 2, (mLastY + y) / 2);
//            mcanvas.drawPath(path, mpaint);
//            mLastX = x;
//            mLastY = y;
//            invalidate();
//        }
        return true;
    }
}
