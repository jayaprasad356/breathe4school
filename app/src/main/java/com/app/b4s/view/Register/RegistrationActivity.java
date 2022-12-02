package com.app.b4s.view.Register;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityRegistrationBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.viewmodels.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity {
    Activity activity;
    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;
    Boolean mobileVerify = false, emailverify = false;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = RegistrationActivity.this;
        session = new Session(activity);
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        binding.setLifecycleOwner(this);
        binding.setViewModel(registrationViewModel);
        binding.edUniqueId.getText();

        registrationViewModel.getUser(mobileVerify, emailverify, binding, activity).observe(this, user -> {
        });
        registrationViewModel.showActivity();

    }
}