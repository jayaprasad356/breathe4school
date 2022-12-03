package com.app.b4s.view.Login;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginTempPasswordBinding;
import com.app.b4s.viewmodels.TempPasswordViewModel;

public class LoginTempPasswordActivity extends AppCompatActivity {

    Activity activity;
    ActivityLoginTempPasswordBinding binding;
    TempPasswordViewModel tempPasswordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_temp_password);
        activity = LoginTempPasswordActivity.this;
        tempPasswordViewModel = ViewModelProviders.of(this).get(TempPasswordViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(tempPasswordViewModel);
        tempPasswordViewModel.getUser(binding, activity).observe(this, user -> {
        });
    }
}