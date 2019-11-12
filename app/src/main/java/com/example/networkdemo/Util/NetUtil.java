package com.example.networkdemo.Util;

//http url 的请求工具类

import android.text.TextUtils;

import com.example.networkdemo.Util.HttpsUtil;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class NetUtil {

    public static String get(String urlPath){
        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            //1.将url字符串转为url对象
            URL url = new URL(urlPath);

            //2获得httpconnection对象
            connection = (HttpURLConnection) url.openConnection();

            //3.设置连接相关参数
            connection.setRequestMethod("GET");//默认为get
            connection.setUseCaches(false);//不使用缓存
            connection.setConnectTimeout(15000);//设置连接超时时间
            connection.setReadTimeout(15000);//设置读取超时时间
            connection.setRequestProperty("Connection","Keep-Alive");//设置请求头参数
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:70.0) Gecko/20100101 Firefox/70.0");

            //4.配置证书
            if ("https".equalsIgnoreCase(url.getProtocol())){
                ((HttpsURLConnection)connection).setSSLSocketFactory(HttpsUtil.getSSLSocketFactory());
            }

            //5.进行数据的读取，首先判断响应码是否为200
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                //获得输入流
                is = connection.getInputStream();
                //包装字节流为字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //读取数据
                StringBuffer response = new StringBuffer();
                String line;

                while ((line = reader.readLine())!= null){
                    response.append(line);
                }
                //关闭资源
                is.close();
                connection.disconnect();
                //返回
                return response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }



    //组装请求参数
    public static String getParamString( List<NameValuePair> pairs) throws UnsupportedEncodingException {

        StringBuilder builder = new StringBuilder();
        for(NameValuePair pair:pairs){
            if(!TextUtils.isEmpty(builder)){
                builder.append("&");
            }

            builder.append(URLEncoder.encode(pair.getName(),"UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
        }
        return builder.toString();
    }





    public static String post(String urlPath,List<NameValuePair> params){

//        HttpURLConnection connection = null;
//        InputStream is = null;
        if(params == null || params.size() == 0){
            return get(urlPath);
        }





        try {
            String body = getParamString(params);
            byte[]data = body.getBytes();

            //1.将url字符串转为url对象
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:70.0) Gecko/20100101 Firefox/70.0");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //4.配置证书
            if ("https".equalsIgnoreCase(url.getProtocol())){
                ((HttpsURLConnection)connection).setSSLSocketFactory(HttpsUtil.getSSLSocketFactory());
            }

            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(data.length));
            OutputStream os = connection.getOutputStream();
            os.write(data);
            os.flush();




            //5.进行数据的读取，首先判断响应码是否为200
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                //获得输入流
                InputStream is = connection.getInputStream();
                //包装字节流为字符流
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                //读取数据
                StringBuffer response = new StringBuffer();
                String line;

                while ((line = reader.readLine())!= null){
                    response.append(line);
                }
                //6.关闭资源
                is.close();
                connection.disconnect();

                //7.返回结果
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
