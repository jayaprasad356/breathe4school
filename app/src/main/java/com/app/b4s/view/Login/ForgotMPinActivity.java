package com.app.b4s.view.Login;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityForgotMpinBinding;
import com.app.b4s.viewmodels.ForgotMPinViewModel;

public class ForgotMPinActivity extends AppCompatActivity {
    Activity activity;
    ActivityForgotMpinBinding binding;
    ForgotMPinViewModel forgotMPinViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ForgotMPinActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_mpin);
        forgotMPinViewModel = ViewModelProviders.of(this).get(ForgotMPinViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(forgotMPinViewModel);
        forgotMPinViewModel.getUser(binding, activity).observe(this, user -> {
        });
        forgotMPinViewModel.timerstart();
    }
}