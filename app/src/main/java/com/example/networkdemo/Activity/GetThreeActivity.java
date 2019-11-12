package com.example.networkdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.networkdemo.R;

import java.util.HashMap;
import java.util.Map;

public class GetThreeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_weather,btn_oil,btn_news;
    private ImageView imageView;
    private ScrollView scrollView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_three);


        initView();
        initDate();
    }

    private void initDate() {

    }

    private void initView() {

        btn_weather = findViewById(R.id.btn_weather);
        btn_oil = findViewById(R.id.btn_oil);
        btn_news = findViewById(R.id.btn_news);

        scrollView = findViewById(R.id.scr_3);
        textView = findViewById(R.id.tx_3);
        imageView = findViewById(R.id.img_3);


        btn_weather.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_weather:



                break;
            case R.id.btn_oil:
                break;
            case R.id.btn_news:
                break;
        }

    }


}
