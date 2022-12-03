package com.app.b4s.view.Login;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityForgotpasswordBinding;
import com.app.b4s.viewmodels.ForgotPasswordViewModel;

public class ForgotpasswordActivity extends AppCompatActivity {

    Activity activity;
    public ActivityForgotpasswordBinding binding;
    ForgotPasswordViewModel forgotPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword);
        activity = ForgotpasswordActivity.this;
        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(forgotPasswordViewModel);
        forgotPasswordViewModel.getUser(binding, activity).observe(this, user -> {
        });
        forgotPasswordViewModel.timerstart();

    }


}