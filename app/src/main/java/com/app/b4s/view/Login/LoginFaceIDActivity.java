package com.app.b4s.view.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.controller.IRegisterController;
import com.app.b4s.controller.IRegisterView;
import com.app.b4s.controller.RegisterController;
import com.app.b4s.databinding.ActivityLoginFaceIdactivityBinding;
import com.app.b4s.utilities.Constant;

public class LoginFaceIDActivity extends AppCompatActivity implements IRegisterView {


    Activity activity;
    ActivityLoginFaceIdactivityBinding binding;
    String uniqueId;
    IRegisterController registerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_face_idactivity);
        activity = LoginFaceIDActivity.this;
        registerController = new RegisterController(this);
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        binding.llFaceId.setOnClickListener(view -> {
            binding.llFaceId.setVisibility(View.GONE);
            binding.llFaceidNotMatch.setVisibility(View.VISIBLE);
        });
        binding.tvLoginWTempPassword.setOnClickListener(view -> registerController.generateTempMpin(activity));
        binding.btnPassword.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LoginPasswordActivity.class);
            activity.startActivity(intent);
        });
        binding.btnPin.setOnClickListener(view -> {
            Intent intent = new Intent(activity, LoginMPinActivity.class);
            activity.startActivity(intent);
        });
    }

    //Temp Mpin generated success
    @Override
    public void onRegisterSuccess(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, LoginTempPasswordActivity.class);
        intent.putExtra(Constant.UNIQUE_ID, uniqueId);
        activity.startActivity(intent);
    }

    @Override
    public void onRegisterError(String message) {

    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure(String message) {

    }

    @Override
    public void onResetSuccess(String message) {

    }
}