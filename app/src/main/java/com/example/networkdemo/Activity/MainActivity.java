package com.example.networkdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.networkdemo.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_httpurl,btn_okhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_httpurl = findViewById(R.id.btn_httpurl);
        btn_okhttp = findViewById(R.id.btn_okhttp);
        btn_okhttp.setOnClickListener(this);
        btn_httpurl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_httpurl:
                intent = new Intent(MainActivity.this, HttpURLActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_okhttp:
                intent = new Intent(MainActivity.this, OKHttpActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_getthree:
                intent = new Intent(MainActivity.this, GetThreeActivity.class);
                startActivity(intent);
                break;
        }

    }
}
