package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.b4s.R;
import com.app.b4s.view.Register.RegistrationActivity;

public class SplashScreenActivity extends AppCompatActivity {
    final int SPLASH_TIME_OUT = 500;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        activity = SplashScreenActivity.this;
        new Handler().postDelayed(() -> startActivity(new Intent(activity, RegistrationActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)), SPLASH_TIME_OUT);

        finish();
    }
}