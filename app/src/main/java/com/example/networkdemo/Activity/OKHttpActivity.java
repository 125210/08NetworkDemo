package com.example.networkdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.networkdemo.Bean.Ip;
import com.example.networkdemo.Bean.IpData;
import com.example.networkdemo.R;
import com.example.networkdemo.Util.HttpsUtil;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OKHttpActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String IP_BASE_URL = "http://ip.taobao.com/service/getIpInfo.php";
    private static final String IP_URL = IP_BASE_URL + "?ip=112.2.253.239";
    private static final String UPLOAD_FILE_URL ="https://api.github.com/markdown/raw";
    private static final String DOWNLOAD_URL = "https://github.com/zhayh/AndroidExample/blob/master/README.md";
    private static final String TAG = "aaaaaaaaa";



    private Button btn_get, btn_post, btn_updata, btn_download;
    private ScrollView scrollView;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        initView();

    }

    private void initView() {
        btn_get = findViewById(R.id.btn_get);
        btn_post = findViewById(R.id.btn_post);
        btn_updata = findViewById(R.id.btn_updata);
        btn_download = findViewById(R.id.btn_download);

        scrollView = findViewById(R.id.scr);
        textView = findViewById(R.id.tx_2);
        imageView = findViewById(R.id.img_2);


        btn_get.setOnClickListener(this);
        btn_post.setOnClickListener(this);
        btn_updata.setOnClickListener(this);
        btn_download.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                get(IP_URL);


                break;
            case R.id.btn_post:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                Map<String,String>parms = new HashMap<>();
                parms.put("ip","112.2.253.239");
                post(IP_URL,parms);


                break;
            case R.id.btn_updata:
                scrollView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                String path = getFilesDir().getAbsolutePath();
                final String fileName = path + File.separator+"readme.md";
                uploadFile(UPLOAD_FILE_URL,fileName);



                break;
            case R.id.btn_download:

                String path1 = getFilesDir().getAbsolutePath();
                downFile(DOWNLOAD_URL, path1);

                break;

        }

    }

    public static void writeFile(InputStream is,String path,String fileName)
        throws IOException{
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdirs();
        }

        File file = new File(path,fileName);
        if (file.exists()){
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer))!= -1){
            fos.write(buffer,0,len);
        }
        fos.flush();
        fos.close();
        bis.close();
    }

    private void downFile(final String url, final String path) {
        final Request request = new Request.Builder().url(url).build();
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("OkHttpAcitivity",e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("文件下载失败");
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()){
                    String ext = url.substring(url.lastIndexOf(".")+1);
                    final String fileName = System.currentTimeMillis() + "."+ext;
                    InputStream is = response.body().byteStream();
                    writeFile(is,path,fileName);
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(fileName+"下载成功，存放在"+path);
                        }
                    });
                }
            }
        });

    }





    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private void uploadFile(String url,String fileName){
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN,new File(fileName)))
                .build();

        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                Log.e(TAG,e.getMessage());
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("上传失败"+ e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String str = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("上传成功"+str);
                        }
                    });
                }else {
                    Log.d(TAG,response.body().string());
                }

            }
        });
    }

    private void post(String ipUrl, Map<String, String> parms) {
        RequestBody body = setRequestBody(parms);

        Request request = new Request.Builder().url(ipUrl).post(body)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:70.0) Gecko/20100101 Firefox/70.0")
                .addHeader("Accept", "application/json")
                .build();

        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                final Ip ip = JSON.parseObject(json, Ip.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ip.getCode() != 0) {
                            textView.setText("请求失败无数据");
                        } else {
                            IpData data = ip.getData();
                            textView.setText(data.getRegion()+","+data.getCounty()+"，，，，，，"+data.getArea_id());
                        }

                    }
                });

            }
        });


    }

    private RequestBody setRequestBody(Map<String, String> parms) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : parms.keySet()){
            builder.add(key,parms.get(key));
        }
        return builder.build();
    }


    private void get(String url) {
        //1.构造Request
        Request request = new Request.Builder().url(url)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:70.0) Gecko/20100101 Firefox/70.0")
                .addHeader("Accept", "application/json")
                .get()
                .method("GET", null)
                .build();


        //2.发送请求，并处理回调
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //获得响应主体的json字符串
                String json = response.body().string();
                //使用fastjson库解析json字符串
                final Ip ip = JSON.parseObject(json, Ip.class);
                //回到UI线程显示获取的数据
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //根据返回的code判断是否成功
                        if (ip.getCode() != 0) {
                            textView.setText("请求失败无数据");
                        } else {
                            //解析数据
                            IpData data = ip.getData();
                            textView.setText(data.getRegion()+","+data.getCity()+"，，，，，，"+data.getArea());
                        }

                    }
                });

            }
        });
    }




}
