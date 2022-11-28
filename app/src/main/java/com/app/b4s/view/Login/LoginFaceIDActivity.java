package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.b4s.R;

public class LoginFaceIDActivity extends AppCompatActivity {


    Button btnPin,btnPassword;
    TextView tvForgotPin_password,tvLoginWTempPassword;
    Activity activity;
    LinearLayout llFaceidNotMatch,llFaceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_face_idactivity);
        activity = LoginFaceIDActivity.this;

        btnPin = findViewById(R.id.btnPin);
        btnPassword = findViewById(R.id.btnPassword);
        tvForgotPin_password = findViewById(R.id.tvForgotPin_password);
        tvLoginWTempPassword = findViewById(R.id.tvLoginWTempPassword);
        llFaceidNotMatch = findViewById(R.id.llFaceidNotMatch);
        llFaceId = findViewById(R.id.llFaceId);



        llFaceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               llFaceId.setVisibility(View.GONE);
               llFaceidNotMatch.setVisibility(View.VISIBLE);

            }
        });
        tvLoginWTempPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(activity,LoginTempPasswordActivity.class);
               startActivity(intent);

            }
        });
        tvForgotPin_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(activity,LoginTempPasswordActivity.class);
               startActivity(intent);

            }
        });







        btnPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,LoginMPinActivity.class);
                startActivity(intent);
            }
        });
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,LoginPasswordActivity.class);
                startActivity(intent);
            }
        });


    }
}