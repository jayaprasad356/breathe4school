package com.app.b4s.view.Login;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginFaceIdactivityBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.LoginFaceidViewModel;

public class LoginFaceIDActivity extends AppCompatActivity {


    Activity activity;
    ActivityLoginFaceIdactivityBinding binding;
    String uniqueId;
    LoginFaceidViewModel loginFaceidViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_face_idactivity);
        activity = LoginFaceIDActivity.this;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        loginFaceidViewModel = ViewModelProviders.of(this).get(LoginFaceidViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginFaceidViewModel);
        loginFaceidViewModel.getUser(binding, activity).observe(this, user -> {
        });
    }

}