package com.example.drawboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.security.PrivateKey;

public class MainActivity extends AppCompatActivity {

    private RadioGroup menu;
    private RadioButton pen,eraser,goback,gonext,save;
    private View board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private  void initView(){
        board=findViewById(R.id.board);
        menu=findViewById(R.id.menu);
        pen=findViewById(R.id.pen);
        eraser=findViewById(R.id.eraser);
        goback=findViewById(R.id.goback);
        gonext=findViewById(R.id.gonext);
        save=findViewById(R.id.save);
    }




}
