package com.swufe.stu.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "wwww";
    TextView output;
    TextView outputpoint;
    TextView outputpoint1;
    TextView outputpoint2;
    TextView outputmoney;
    int point;
    int point1;
    int point2;
//    TextView outputheight;
//    TextView outputweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

//        output=findViewById(R.id.textView);//获得控件
//        output.setText("BMI计算器");
//
//        Button btn=findViewById(R.id.btn);
        //btn.setOnClickListener(this);
        //this.onclick
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                output=findViewById(R.id.getinput);
//                String uname=output.getText().toString();
//
//                output.setText("华氏温度为"+uname);
//            }
//     };
    }

    //    public void myClick(View v){
//        //获得用户输入
//        outputheight=findViewById(R.id.input1);
//        String height=outputheight.getText().toString();
//        outputweight=findViewById(R.id.input2);
//        String weight=outputweight.getText().toString();
//
//        double h,w,bmi;
//        String advice;
//        h=Double.parseDouble(height);
//        w=Double.parseDouble(weight);
//        bmi=w/(h*h);
//        DecimalFormat df = new DecimalFormat("######0.00");
//
//        output=findViewById(R.id.getinput);
//        if(bmi<18.5)
//            advice="您的体重过轻，请注意饮食";
//         else if(bmi<23.9)
//            advice="您的体重正常，请继续保持";
//         else if(bmi<27.9)
//            advice="您的体重超重，请注意控制饮食";
//        else
//            advice="您的体重肥胖，请严格控制饮食，加强锻炼";
//
//        output.setText("BMI指数为:"+String.valueOf(df.format(bmi))+'\n'+advice);
//    }
//}
    //ball2
    public void myClick1(View btn) {
        outputpoint = findViewById(R.id.point1);
        point = Integer.parseInt(outputpoint.getText().toString());
        if (btn.getId() == R.id.button1) {
            point = point + 1;
        } else if (btn.getId() == R.id.button2) {
            point = point + 2;
        } else {
            point = point + 3;
        }
        outputpoint.setText(String.valueOf(point));

    }

    public void myClick2(View btn) {
        outputpoint = findViewById(R.id.point2);
        point = Integer.parseInt(outputpoint.getText().toString());
        if (btn.getId() == R.id.button4) {
            point = point + 1;
        } else if (btn.getId() == R.id.button5) {
            point = point + 2;
        } else {
            point = point + 3;
        }
        outputpoint.setText(String.valueOf(point));

    }

    public void myClick3(View btn) {
        outputpoint1 = findViewById(R.id.point1);
        outputpoint2 = findViewById(R.id.point2);
        outputpoint1.setText(String.valueOf(0));
        outputpoint2.setText(String.valueOf(0));

    }



}
