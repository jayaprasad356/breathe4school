package com.app.b4s.view.Register;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetPasswordBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.PasswordViewModel;

public class SetPasswordActivity extends AppCompatActivity {
    Activity activity;
    String uniqueID, flow;
    ActivitySetPasswordBinding binding;
    PasswordViewModel passwordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_password);
        binding.setLifecycleOwner(this);
        binding.setViewModel(passwordViewModel);
        activity = SetPasswordActivity.this;
        uniqueID = getIntent().getStringExtra(Constant.UNIQUE_ID);
        flow = getIntent().getStringExtra(Constant.FLOW);
        passwordViewModel.getUser(binding, activity, flow).observe(this, user -> {
        });
        binding.edSetPasswordInp.setEndIconOnClickListener(view -> passwordViewModel.setPasswordInfo());
    }

}