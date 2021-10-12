package com.swufe.stu.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RateListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list);
        ListView listView=findViewById(R.id.mylist1);

//        String[] list_data={"one","two","there","four"};
//        ListAdapter adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list_data);
//
//        listView.setAdapter(adapter);


        Handler handler=new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==7){
                    ArrayList<String> rlist=(ArrayList<String>) msg.obj;
                    ListAdapter adapter=new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1,rlist);
                    listView.setAdapter(adapter);
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