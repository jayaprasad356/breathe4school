package com.app.b4s.view.Register;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetMpinBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.MpinViewModel;

public class SetMPinActivity extends AppCompatActivity {
    ActivitySetMpinBinding binding;
    MpinViewModel mpinViewModel;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_mpin);
        mpinViewModel = ViewModelProviders.of(this).get(MpinViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(mpinViewModel);
        activity = SetMPinActivity.this;
        String  flow = getIntent().getStringExtra(Constant.FLOW);
        mpinViewModel.getUser(binding, activity,flow).observe(this, user -> {
        });
    }


}