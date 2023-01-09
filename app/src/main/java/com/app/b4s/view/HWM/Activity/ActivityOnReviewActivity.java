package com.app.b4s.view.HWM.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityOnReview2Binding;

public class ActivityOnReviewActivity extends AppCompatActivity {
    ActivityOnReview2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_on_review2);
        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());
    }
}