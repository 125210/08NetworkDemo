package com.example.networkdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.networkdemo.Util.NetUtil;
import com.example.networkdemo.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class HttpURLActivity extends AppCompatActivity implements View.OnClickListener{



    private static final String IP_BASE_URL = "http://ip.taobao.com/service/getIpInfo.php";
    private static final String IP_URL = IP_BASE_URL+ "?ip=112.2.253.239";

    private Button btn_git,btn_post,btn_updata,btn_download;
    private ScrollView scrollView;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_urlconnection);



        initView();

    }






    private void initView() {
        btn_git = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        btn_updata = findViewById(R.id.btn_updata);
        btn_download = findViewById(R.id.btn_download);

        scrollView = findViewById(R.id.scr);
        textView = findViewById(R.id.tx);
        imageView = findViewById(R.id.img);



        btn_git.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_updata.setOnClickListener(this);
        btn_download.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
//                Glide.with(this).load("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2363799301,1741488617&fm=26&gp=0.jpg").into(imageView);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result = NetUtil.get(IP_URL);
                        if (result != null) {
                                Log.d("MainActivity", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(result);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    textView.setText("请求失败");
                                }
                            });
                        }
                    }
                }).start();

                break;
            case R.id.btn_post:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("ip","221.226.155.10"));
                        final String result = NetUtil.get(IP_URL);
                        if (result != null) {
                            Log.d("MainActivity", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(result);
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    textView.setText("请求失败");
                                }
                            });
                        }
                    }
                }).start();
                break;

            case R.id.btn_updata:



                break;
            case R.id.btn_download:



                break;

        }

    }
}
