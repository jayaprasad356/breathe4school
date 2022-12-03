package com.app.b4s.view.Register;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetFaceIdBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.SetFaceIdViewModel;

public class SetFaceIdActivity extends AppCompatActivity {

    ImageButton ibBackBtn;
    TextView tvSkipFaceid;
    Boolean Skip_FaceID_tv = false;
    ActivitySetFaceIdBinding binding;
    Activity activity;
    String uniqueId;
    SetFaceIdViewModel setFaceIdViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_face_id);
        activity = SetFaceIdActivity.this;
        ibBackBtn = binding.ibBackBtn;
        tvSkipFaceid = binding.tvSkipFaceid;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        setFaceIdViewModel = ViewModelProviders.of(this).get(SetFaceIdViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(setFaceIdViewModel);
        Skip_FaceID_tv = getIntent().getExtras().getBoolean(Constant.SKIP_FACE_ID);
        setFaceIdViewModel.getUser(binding, activity, Skip_FaceID_tv).observe(this, user -> {
        });

        setFaceIdViewModel.showVisibility();
    }
}