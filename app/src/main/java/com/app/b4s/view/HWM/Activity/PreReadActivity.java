package com.app.b4s.view.HWM.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityPreReadPendingBinding;
import com.app.b4s.utilities.Constant;

public class PreReadActivity extends AppCompatActivity {
    ActivityPreReadPendingBinding binding;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pre_read_pending);
        Intent intent= getIntent();
        type= intent.getStringExtra(Constant.TYPE);
        binding.tvBack.setOnClickListener(view -> onBackPressed());
        if (type.equals(Constant.COMPLETED)){
            binding.btnCompleted.setEnabled(false);
            binding.btnCompleted.setText("Completed");
        }
    }
}