package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.b4s.R;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.RegistrationActivity;

public class SplashScreenActivity extends AppCompatActivity {
    final int SPLASH_TIME_OUT = 500;
    Activity activity;
    Handler handler;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        activity = SplashScreenActivity.this;
        session = new Session(activity);
        session.setBoolean(Constant.MOBILE_OTP_VERIFY, false);
        session.setData(Constant.MOBILE, "");
        session.setBoolean(Constant.EMAIL_OTP_VERIFY, false);
        session.setData(Constant.EMAIL, "");


        handler = new Handler();
        GotoActivity();
    }


    private void GotoActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(activity, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}