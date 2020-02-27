package com.example.drawboard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;

public class MainActivity extends Activity implements View.OnClickListener {

    private RadioGroup menu;
    private RadioButton pen, eraser, goback, gonext, save;
    private DrawBoard board;
    private TextView textView;
    private SeekBar seekBar;

    private int colors[] = new int[]{0xff00ffff, 0xffff0000, 0xff00ff00, 0xff00ffff};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        board = findViewById(R.id.board);
        menu = findViewById(R.id.menu);
        pen = findViewById(R.id.pen);
        eraser = findViewById(R.id.eraser);
        goback = findViewById(R.id.goback);
        gonext = findViewById(R.id.gonext);
        save = findViewById(R.id.save);

        pen.setOnClickListener(this);
        eraser.setOnClickListener(this);
        goback.setOnClickListener(this);
        gonext.setOnClickListener(this);
    }

    private Bitmap getViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void saveBitmap(View v) {
        Bitmap bitmap = getViewBitmap(v);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eraser:
                board.seteraser();
                Log.i("i","eraser");
                break;
            case R.id.pen:
                board.setpen();
                Log.i("i","pen");

                break;
            case R.id.goback:
                board.goback();
                Log.i("i","goback");

                break;
            case R.id.gonext:
                board.gonext();
                break;
            case R.id.colors:
                board.changePaintColor(colors[2]);
                break;
        }
    }
}
