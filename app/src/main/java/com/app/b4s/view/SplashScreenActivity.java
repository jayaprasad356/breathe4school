package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.b4s.R;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Home.Activity.HomeActivity;
import com.app.b4s.view.Login.LoginFaceIDActivity;
import com.app.b4s.view.Login.LoginSuccessfullActivity;
import com.app.b4s.view.Register.RegisterSuccessfullActivity;
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
        //session.setData(Constant.MOBILE, "");
        session.setBoolean(Constant.EMAIL_OTP_VERIFY, false);
        // session.setData(Constant.EMAIL, "");

        handler = new Handler();
        if (session.getBoolean(Constant.IS_LOGEDIN)) {
            Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
            startActivity(intent);
            finishAffinity();
        } else {
            if (session.getBoolean(Constant.IS_REGISTER))
                goToLoginActivity();
            else
                goToRegisterActivity();
        }
    }

    private void goToLoginActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginFaceIDActivity.class);
                intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
                intent.putExtra(Constant.SKIP_FACE_ID, 0);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }


    private void goToRegisterActivity() {
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