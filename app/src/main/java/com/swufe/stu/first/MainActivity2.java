package com.swufe.stu.first;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    TextView output;
    TextView outputmoney;
    double money;
    double money1;
    String money_string;
    double dollarRate=0.1548;
    double poundRate=0.1130;
    double euroRate=0.1323;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exchange);
    }
    //exchange
        public void exClick(View btn) {
            outputmoney = findViewById(R.id.inputrmb);
            output=findViewById(R.id.exchange);
            money_string = outputmoney.getText().toString();
            if (money_string.length()==0) {
                Toast.makeText(this, "请输入人民币金额", Toast.LENGTH_SHORT).show();
            } else {
                money = Double.parseDouble(money_string);
                if (btn.getId() == R.id.button1) {
                    //美元
                    money1 = money * dollarRate;
                } else if (btn.getId() == R.id.button2) {
                    //英镑
                    money1 = money * poundRate;
                } else if (btn.getId() == R.id.button3){
                    //欧元
                    money1 = money * euroRate;
                }else{
                    money1=money;
                }
                output.setText(String.valueOf(money1));
            }
        }

    //页面跳转
        public void openOne(View btn){
            openConfig();
        }

    private void openConfig() {
        Intent config=new Intent(this,MainActivity3.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("pound_rate_key",poundRate);
        config.putExtra("euro_rate_key",euroRate);

        startActivityForResult(config,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1&&resultCode==1) {
            dollarRate = data.getDoubleExtra("dollar_key", 0.0);
            poundRate = data.getDoubleExtra("pound_key", 0.0);
            euroRate = data.getDoubleExtra("euro_key", 0.0);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_setting){
            openConfig();
        }
        return super.onOptionsItemSelected(item);
    }
}
