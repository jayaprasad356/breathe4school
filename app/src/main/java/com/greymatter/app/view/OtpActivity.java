package com.greymatter.app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.greymatter.app.R;

public class OtpActivity extends AppCompatActivity {

    TextView tvTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);



        tvTimer = findViewById(R.id.tvTimer);


        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(""+millisUntilFinished/1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {

            }

        }.start();


    }
}