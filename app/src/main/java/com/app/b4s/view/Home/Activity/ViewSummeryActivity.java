package com.app.b4s.view.Home.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityViewSummeryBinding;

public class ViewSummeryActivity extends AppCompatActivity {
ActivityViewSummeryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_summery);
        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());

    }
}