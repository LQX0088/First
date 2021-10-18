package com.swufe.stu.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RateHttpActivity extends AppCompatActivity {
    private static final String TAG = "Rate--";
    TextView country;
    TextView input;
    TextView exchange;
    String rateType;
    String rate;
    Float rate1;
    float ending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ratemap);
        country=findViewById(R.id.cval);
        input=findViewById(R.id.RMB);
        exchange=findViewById(R.id.money);
        //数据传输
        Intent intent=getIntent();
        rateType=intent.getStringExtra("rate_type");
        rate=intent.getStringExtra("rate_get");

        country.setText(String.valueOf(rateType));
        Log.i(TAG, "onCreate: country->>"+rateType);
        Log.i(TAG, "onCreate: rate->>"+rate);

    }
    public void exchangeClick(View btn) {
        String rmb = input.getText().toString();
        if (rmb.length()>0){
            float rmb1=Float.valueOf(rmb);
            rate1=Float.valueOf(rate);
            ending=(100/rate1)*rmb1;
            exchange.setText(String.valueOf(ending));
        }
        else{
            Toast.makeText(RateHttpActivity.this,"input RMB",Toast.LENGTH_SHORT).show();
        }
    }
}