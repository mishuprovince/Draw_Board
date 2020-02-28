package com.example.drawboard;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {

    private RadioGroup menu;
    private RadioButton pen, eraser, goback, gonext, save, clean,width,color;
    private DrawBoard board;
    private int colorlocation=0;
    private int widthlocation=-1;

    private int colors[] = new int[]{0xff000000, 0xffff0000, 0xff00ff00, 0xff00ffff};
    private int paintWidths[]=new int[]{10,20,30,40,50};

    @RequiresApi(api = Build.VERSION_CODES.M)
    void Request() {
        //获取相机拍摄读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //版本判断
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 1);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Request();
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
        color=findViewById(R.id.colors);
        clean = findViewById(R.id.cleanboard);
        width=findViewById(R.id.widths);
        pen.setOnClickListener(this);
        eraser.setOnClickListener(this);
        goback.setOnClickListener(this);
        gonext.setOnClickListener(this);
        clean.setOnClickListener(this);
        save.setOnClickListener(this);
        width.setOnClickListener(this);
        color.setOnClickListener(this);

    }

    private Bitmap getViewBitmap(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void saveBitmap(View v) {
        Bitmap bmp = getViewBitmap(v);

        String root = getApplicationContext().getFilesDir().getAbsolutePath();
        Log.v("path",root);

        String dirName = "drawboard";
        File appDir = new File(root, dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".jpg";

        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        Log.i("path",file.getPath());
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

            MediaStore.Images.Media.insertImage(this.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + appDir.getAbsolutePath())));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eraser:
                board.seteraser();
                break;
            case R.id.pen:
                board.setpen();
                break;
            case R.id.goback:
                board.goback();
                break;
            case R.id.gonext:
                board.gonext();
                break;
            case R.id.colors:
                colorlocation++;
                color.setBackgroundColor(colors[colorlocation%4]);
                board.changePaintColor(colors[colorlocation%4]);
                break;
            case R.id.cleanboard:
                board.clean();
                break;
            case R.id.save:
                saveBitmap(board);
                break;
            case R.id.widths:
                widthlocation++;
                width.setText(paintWidths[widthlocation%5]+"");
                board.changePaintSize(paintWidths[widthlocation%5]);
                break;
        }
    }
}
