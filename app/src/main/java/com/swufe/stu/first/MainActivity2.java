package com.swufe.stu.first;

import static java.lang.System.out;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//汇率转换主界面
public class MainActivity2 extends AppCompatActivity implements Runnable {
    

    private static final String TAG = "wwww";
    TextView output;
    TextView outputmoney;
    double money;
    double money1;
    String money_string;
    double dollarRate = 0.1548;
    double poundRate = 0.1130;
    double euroRate = 0.1323;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange);
        //读取文件中保存的数据
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        dollarRate = (double) sharedPreferences.getFloat("new_dollar", 0.0f);
        poundRate = (double) sharedPreferences.getFloat("new_pound", 0.0f);
        euroRate = (double) sharedPreferences.getFloat("new_euro", 0.0f);

        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                //接受消息
                if (msg.what == 6) {
                    Log.i(TAG, "handleMessage: 消息收到");
                    String str = (String) msg.obj;
                    Log.i(TAG, "handleMessage: handleMessage:getMessange msg=" + str);
                }
                super.handleMessage(msg);
            }
        };

        //开启线程
        Thread t = new Thread(this);
        Log.i(TAG, "onCreate: ");
        t.start();
    }

    //exchange
    public void exClick(View btn) {
        outputmoney = findViewById(R.id.inputrmb);
        output = findViewById(R.id.exchange);
        money_string = outputmoney.getText().toString();
        if (money_string.length() == 0) {
            Toast.makeText(this, "请输入人民币金额", Toast.LENGTH_SHORT).show();
        } else {
            money = Double.parseDouble(money_string);
            if (btn.getId() == R.id.button1) {
                //美元
                money1 = money * dollarRate;
            } else if (btn.getId() == R.id.button2) {
                //英镑
                money1 = money * poundRate;
            } else if (btn.getId() == R.id.button3) {
                //欧元
                money1 = money * euroRate;
            } else {
                money1 = money;
            }
            output.setText(String.valueOf(money1));
        }
    }

    //页面跳转
    public void openOne(View btn) {
        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent(this, MainActivity3.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("pound_rate_key", poundRate);
        config.putExtra("euro_rate_key", euroRate);

        startActivityForResult(config, 1);
    }

    //从3界面直接传数据过来
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == 1) {//直接从3界面传值过来
            dollarRate = data.getDoubleExtra("dollar_key", 0.0);
            poundRate = data.getDoubleExtra("pound_key", 0.0);
            euroRate = data.getDoubleExtra("euro_key", 0.0);
        }
//        如果接受返回数据时保存到文件中
//        SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor= sp.edit();
//        editor.putFloat("new_dollar",(float) dollarRate);
//        editor.putFloat("new_pound",(float) poundRate);
//        editor.putFloat("new_euro",(float) euroRate);
//        editor.apply();

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    //创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    //调用菜单项打开页面
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "run: run()......");
        //获取网络数据
        URL url = null;
        try {
//            url = new URL("https://www.usd-cny.com/bankofchina.htm");
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            InputStream in = http.getInputStream();
//            String html = inputStream2String(in);
//            Log.i(TAG, "run: html=" + html);

            Document doc=Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: title:"+doc.title());
            Elements h4s=doc.getElementsByTag("h4");
            for(Element h4:h4s){
                Log.i(TAG, "run:h4:"+h4.text());
            }

            Elements tables=doc.getElementsByTag("table");
            Element table1=tables.first();
            Log.i(TAG, "run: table"+table1);
            Elements hrefs=table1.getElementsByTag("hrefs");
            for(Element tr:hrefs){
                Log.i(TAG, "run: hrefs="+hrefs.text());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //发送消息给主线程
        Message msg = handler.obtainMessage();
        msg.what = 6;
        msg.obj = "hello from run";
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息已发送");


    }

    //获取界面内容
    private String inputStream2String(InputStream inputStream)
            throws IOException {
        final int buffersize = 1024;
        final char[] buffer = new char[buffersize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while (true) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);

        }
        return out.toString();

    }
}
