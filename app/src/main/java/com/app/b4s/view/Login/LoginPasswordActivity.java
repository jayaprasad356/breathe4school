package com.app.b4s.view.Login;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginPasswordBinding;
import com.app.b4s.viewmodels.LoginPasswordViewModel;

public class LoginPasswordActivity extends AppCompatActivity {
    Activity activity;
    ActivityLoginPasswordBinding binding;
    LoginPasswordViewModel loginPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_password);
        activity = LoginPasswordActivity.this;
        loginPasswordViewModel = ViewModelProviders.of(this).get(LoginPasswordViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginPasswordViewModel);
        loginPasswordViewModel.getUser(binding, activity).observe(this, user -> {
        });


    }

}