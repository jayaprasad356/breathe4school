package com.app.b4s.view.Login;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginMpinBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.LoginMPinViewModel;

public class LoginMPinActivity extends AppCompatActivity {

    Activity activity;

    ActivityLoginMpinBinding binding;
    String uniqueId;
    LoginMPinViewModel loginMPinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginMPinViewModel = ViewModelProviders.of(this).get(LoginMPinViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_mpin);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginMPinViewModel);
        activity = LoginMPinActivity.this;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        loginMPinViewModel.getUser(binding, activity).observe(this, user -> {
        });

    }
}