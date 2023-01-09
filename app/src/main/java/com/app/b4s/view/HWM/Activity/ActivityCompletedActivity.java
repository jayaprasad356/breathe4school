package com.app.b4s.view.HWM.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityCompletedBinding;

public class ActivityCompletedActivity extends AppCompatActivity {
    ActivityCompletedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_completed);

        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());
    }
}