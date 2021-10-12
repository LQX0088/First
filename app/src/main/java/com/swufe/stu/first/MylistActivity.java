package com.swufe.stu.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ListActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MylistActivity extends ListActivity implements Runnable {

    Handler handler;
    private static final String TAG = "List--";
    List<String> retlist=new ArrayList<String>();

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        List<String> list1=new ArrayList<String>();
        for (int i=1;i<100;i++){
            list1.add("item"+i);
        }

        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    ArrayList<String> rlist=(ArrayList<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(MylistActivity.this, android.R.layout.simple_list_item_1,rlist);
                    setListAdapter(adapter);
                }

                super.handleMessage(msg);
            }
        };


//        String[] list_data={"one","two","there","four"};
//        ListAdapter adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_data);
//
//        setListAdapter(adapter);


//        RateThread rt =new RateThread(handler);
        Thread t=new Thread(this);
        t.start();


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

            Document doc= Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: title:"+doc.title());

//            Elements h4s=doc.getElementsByTag("h4");
//            for(Element h4:h4s){
//                Log.i(TAG, "run:h4:"+h4.text());
//            }

            Elements tables=doc.getElementsByTag("table");
            Element firsttable=tables.first();
            Bundle bundle=new Bundle();
            Elements trs=firsttable.getElementsByTag("tr");
            trs.remove(0);


            for(Element tr:trs) {
                Elements tds = tr.getElementsByTag("td");
                String cname = tds.get(0).text();
                String cval = tds.get(5).text();
                Log.i(TAG, "run: " + cname + "==>" + cval);
                retlist.add(cname+"==>"+cval);

            }


//            Log.i(TAG, "run: table"+table1);
//            Elements hrefs=table1.getElementsByTag("hrefs");
//            for(Element tr:hrefs){
//                Log.i(TAG, "run: hrefs="+hrefs.text());
//            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //发送消息给主线程
        Message msg=handler.obtainMessage();
        msg.what = 7;
        msg.obj = retlist;
        handler.sendMessage(msg);
        Log.i(TAG, "run: 消息已发送");


    }
}