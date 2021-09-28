package com.swufe.stu.first;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
//汇率转换修改汇率页面

public class MainActivity3 extends AppCompatActivity {

    TextView dollarText;
    TextView poundText;
    TextView euroText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange1);
        Intent intent=getIntent();
        double dollar2=intent.getDoubleExtra("dollar_rate_key",0.0);
        double pound2=intent.getDoubleExtra("pound_rate_key",0.0);
        double euro2=intent.getDoubleExtra("euro_rate_key",0.0);

        dollarText=findViewById(R.id.text_dollar);
        poundText=findViewById(R.id.text_pound);
        euroText=findViewById(R.id.text_euro);

        dollarText.setText(String.valueOf(dollar2));
        poundText.setText(String.valueOf(pound2));
        euroText.setText(String.valueOf(euro2));

    }
    public void save(View btn){
        //重新获取新的汇率数据
        dollarText=findViewById(R.id.text_dollar);
        poundText=findViewById(R.id.text_pound);
        euroText=findViewById(R.id.text_euro);
        double dollar=Double.parseDouble(dollarText.getText().toString());
        double pound=Double.parseDouble(poundText.getText().toString());
        double euro=Double.parseDouble(euroText.getText().toString());

//        Intent first=getIntent();
//        first.putExtra("dollar_key",dollar);
//        first.putExtra("pound_key",pound);
//        first.putExtra("euro_key",euro);

        //在save时保存数据到文件中
        SharedPreferences sp=getSharedPreferences("myrate", Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor= sp.edit();
        editor.putFloat("new_dollar",(float) dollar);
        editor.putFloat("new_pound",(float) pound);
        editor.putFloat("new_euro",(float) euro);
        editor.apply();


        //setResult(1,first);
        finish();//退回原来界面
    }
}