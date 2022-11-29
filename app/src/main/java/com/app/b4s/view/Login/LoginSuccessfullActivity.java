package com.app.b4s.view.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.b4s.R;
import com.app.b4s.view.Register.SetFaceIdActivity;

public class LoginSuccessfullActivity extends AppCompatActivity {

    TextView tvTitle,tvdescription;
    Activity activity;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfull);
        activity = LoginSuccessfullActivity.this;

        tvTitle = findViewById(R.id.tvTitle);
        tvdescription = findViewById(R.id.tvdescription);



        tvTitle.setText(getIntent().getStringExtra("Title"));
        tvdescription.setText(getIntent().getStringExtra("Descripition"));



        handler = new Handler();
        GotoActivity();


    }


    private void GotoActivity()
    {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent intent = new Intent(LoginSuccessfullActivity.this, SetFaceIdActivity.class);
                    intent.putExtra("SkipFaceId", true);
                    startActivity(intent);
                    finish();

            }
        },2000);
    }
}