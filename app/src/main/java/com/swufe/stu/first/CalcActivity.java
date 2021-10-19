package com.swufe.stu.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class CalcActivity extends AppCompatActivity {

    private EditText input;
    private TextView show;
    private TextView title;
    private float rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        title=findViewById(R.id.calc_title);
        input=findViewById(R.id.calc_rmb);
        show=findViewById(R.id.calc_result);

        Intent intent=getIntent();
        String cname=intent.getStringExtra("cname_key");
        title.setText(cname);
        String cval=intent.getStringExtra("cval_key");
        show.setText(cval);
        rate=Float.parseFloat(cval);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str=s.toString();
                if (str.length()>0){
                    String result=String.valueOf(Float.parseFloat(str)*rate);
                    show.setText(result);
                }else {
                    show.setText(" ");
                }
            }
        });


    }
}