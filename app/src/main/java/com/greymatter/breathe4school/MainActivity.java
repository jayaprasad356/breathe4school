package com.greymatter.breathe4school;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=MainActivity.this;
        getSupportActionBar().hide();
        Intent intent = new Intent(activity, RegistrationActivity.class);
        startActivity(intent);
    }
}