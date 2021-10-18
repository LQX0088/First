package com.swufe.stu.first;
//解析中国银行汇率 继承list类产生列表
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ListActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RateListChinaActivity extends ListActivity implements Runnable {

    Handler handler;
    private static final String TAG = "List--";
    List<String> retlist=new ArrayList<String>();

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ListView listView=(ListView)findViewById(R.id.mylist1);
        List<String> list1=new ArrayList<String>();
        for (int i=1;i<100;i++){
            list1.add("item"+i);
        }

        handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    ArrayList<String> rlist=(ArrayList<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListChinaActivity.this, android.R.layout.simple_list_item_1,rlist);
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
                Document doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
                Log.i(TAG, "run: title:" + doc.title());

                Elements tables = doc.getElementsByTag("table");
                Element firsttable = tables.get(1);
                Bundle bundle = new Bundle();
                Elements trs = firsttable.getElementsByTag("tr");
                trs.remove(0);


                for (Element tr : trs) {
                    Elements tds = tr.getElementsByTag("td");
                    String cname = tds.get(0).text();
                    String cval = tds.get(5).text();
                    Log.i(TAG, "run: " + cname + "==>" + cval);
                    retlist.add(cname + "==>" + cval);

                }
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