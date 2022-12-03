package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityOtpBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.OtpViewModel;

public class OtpActivity extends AppCompatActivity {

    Activity activity;
    OtpViewModel otpViewModel;
    private ActivityOtpBinding binding;
    String otpType, description;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = OtpActivity.this;
        session = new Session(activity);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        binding.setLifecycleOwner(this);
        binding.setViewModel(otpViewModel);
        Intent intent = getIntent();

        otpType = intent.getStringExtra(Constant.TYPE);
        description = intent.getStringExtra(Constant.DESCRIPTION);
        binding.tvdescription.setText(getString(R.string.otp_header) + " " + description);

        otpViewModel.getUser(binding,otpType,activity).observe(this, user -> {});
        otpViewModel.timerstart();

    }
}

