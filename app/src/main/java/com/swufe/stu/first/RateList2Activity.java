package com.swufe.stu.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class RateList2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private static final String TAG = "List--";

    ListView list2;
    MyAdapter myAdapter;
    ArrayList<Item> rlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_list2);
        list2 =findViewById(R.id.mylist2);
        list2.setOnItemClickListener(this);
        list2.setOnItemLongClickListener(this);

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

        list2.setEmptyView(findViewById(R.id.no_data));


        Handler handler=new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
//                    ArrayList<HashMap<String, String>> rlist = (ArrayList<HashMap<String, String>>) msg.obj;
                    Log.i(TAG, "handleMessage: ");
                    rlist=(ArrayList<Item>)msg.obj;
                    Log.i(TAG, "handleMessage: "+rlist);
                    myAdapter = new MyAdapter(RateList2Activity.this,
                            R.layout.list_item,
                            rlist);

                    list2.setAdapter(myAdapter);
                }
            }
        };

//        RateMapThread dt=new RateMapThread(handler);
        RateItemThread dt=new RateItemThread(handler);
        Thread t=new Thread(dt);
        t.start();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAtposition=list2.getItemAtPosition(position);
//        HashMap<String,String> map=(HashMap<String, String>) itemAtposition;
        Item item=(Item) itemAtposition;
//        String titleStr =map.get("cname");
//        String detailStr=map.get("cval");
        String titleStr =item.getCname();
        String detailStr=item.getCval();
        Log.i(TAG, "onItemClick:titleStr="+titleStr);
        Log.i(TAG, "onItemClick: detai lStr="+detailStr);

        //dakaichuangkou
        Intent open=new Intent(this,CalcActivity.class);
        open.putExtra("cname_key",titleStr);
        open.putExtra("cval_key",detailStr);
        startActivity(open);


//        TextView title=(TextView) view.findViewById(R.id.itemTitle);
//        TextView detail=(TextView) view.findViewById(R.id.itemDetail);
//
//        String title2=String.valueOf(title.getText());
//        String detail2=String.valueOf(detail.getText());
//        Log.i(TAG, "onItemClick: title2="+title2);
//        Log.i(TAG, "onItemClick: detail2="+detail2);

//        Intent config=new Intent(this,RateHttpActivity.class);
//        config.putExtra("rate_type",titleStr);
//        config.putExtra("rate_get",detailStr);
//
//        startActivity(config);

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemLongClick: 长按操作");
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "onClick: 对话框数据处理");
                        //删除数据项
                        myAdapter.remove(list2.getItemAtPosition(position));
                    }
                }).setNegativeButton("否",null);
        builder.create().show();
        return false;
    }
}
