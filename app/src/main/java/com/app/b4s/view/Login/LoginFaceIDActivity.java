package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginFaceIdactivityBinding;

public class LoginFaceIDActivity extends AppCompatActivity {


    Button btnPin, btnPassword;
    TextView tvForgotPin_password, tvLoginWTempPassword;
    Activity activity;
    LinearLayout llFaceidNotMatch, llFaceId;
    ActivityLoginFaceIdactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_face_idactivity);
        activity = LoginFaceIDActivity.this;

        btnPin = binding.btnPin;
        btnPassword = binding.btnPassword;
        tvForgotPin_password = binding.tvForgotPinPassword;
        tvLoginWTempPassword = binding.tvLoginWTempPassword;
        llFaceidNotMatch = binding.llFaceidNotMatch;
        llFaceId = binding.llFaceId;


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

                Intent intent = new Intent(activity, LoginTempPasswordActivity.class);
                startActivity(intent);

            }
        });
        tvForgotPin_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, LoginTempPasswordActivity.class);
                startActivity(intent);

            }
        });


        btnPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginMPinActivity.class);
                startActivity(intent);
            }
        });
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginPasswordActivity.class);
                startActivity(intent);
            }
        });


    }
}