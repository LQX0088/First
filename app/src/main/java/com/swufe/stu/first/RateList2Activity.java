package com.swufe.stu.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RateList2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "List--";

    ListView list2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);
        list2 =findViewById(R.id.mylist2);
        list2.setOnItemClickListener(this);

        //准备数据
        ArrayList<HashMap<String,String>> listItems=new ArrayList<HashMap<String,String>>();
        for(int i=0;i<10;i++)
        {
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("cname","Rate:"+i);//标题文字
            map.put("cval","detail"+i);//详情描述
            listItems.add(map);
        }

        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter=new SimpleAdapter(this,
                listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
        list2.setAdapter(listItemAdapter);


        Handler handler=new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
                    ArrayList<HashMap<String, String>> rlist = (ArrayList<HashMap<String, String>>) msg.obj;
                    MyAdapter myAdapter = new MyAdapter(RateList2Activity.this,
                            R.layout.list_item,
                            rlist);

                    list2.setAdapter(myAdapter);
                }
            }
        };

        RateMapThread dt=new RateMapThread(handler);
        Thread t=new Thread(dt);
        t.start();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtposition=list2.getItemAtPosition(position);
        HashMap<String,String> map=(HashMap<String, String>) itemAtposition;
        String titleStr =map.get("cname");
        String detailStr=map.get("cval");
        Log.i(TAG, "onItemClick:titleStr="+titleStr);
        Log.i(TAG, "onItemClick: detai lStr="+detailStr);

        TextView title=(TextView) view.findViewById(R.id.itemTitle);
        TextView detail=(TextView) view.findViewById(R.id.itemDetail);

        String title2=String.valueOf(title.getText());
        String detail2=String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2="+title2);
        Log.i(TAG, "onItemClick: detail2="+detail2);

        Intent config=new Intent(this,RateHttpActivity.class);
        config.putExtra("rate_type",titleStr);
        config.putExtra("rate_get",detailStr);

        startActivity(config);

    }

}
