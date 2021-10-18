package com.swufe.stu.first;
//自定义布局列表
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class RateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item);
        ListView listView=findViewById(R.id.mylist1);
        //ProgressBar progressBar=findViewById(R.id.progressBar2);

//        String[] list_data={"one","two","there","four"};
//        ListAdapter adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_data);
//
//        listView.setAdapter(adapter);

//自定义布局
        Handler handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    ArrayList<String> rlist=(ArrayList<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,rlist);
                    listView.setAdapter(adapter);
                    //调整控件显示状态
                    //progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }

                super.handleMessage(msg);
            }
        };

        MylistActivity first=new MylistActivity();
        first.setHandler(handler);

        Thread t=new Thread(first);
        t.start();

    }
}