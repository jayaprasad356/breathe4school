package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetFaceIdBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginFaceIDActivity;
import com.app.b4s.viewmodels.SetFaceIdViewModel;

public class SetFaceIdActivity extends AppCompatActivity {

    ImageButton ibBackBtn;
    TextView tvSkipFaceid;
    Boolean Skip_FaceID_tv = false;
    ActivitySetFaceIdBinding binding;
    Activity activity;
    String flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_face_id);
        activity = SetFaceIdActivity.this;
        ibBackBtn = binding.ibBackBtn;
        tvSkipFaceid = binding.tvSkipFaceid;
        flow = getIntent().getStringExtra(Constant.FLOW);
        Skip_FaceID_tv = getIntent().getExtras().getBoolean(Constant.SKIP_FACE_ID);
        showVisibility();
        if (flow.equals(Constant.FORGOT)) {
            binding.ivFaceLogo.setOnClickListener(view -> {
                Intent intent = new Intent(activity, SetMPinActivity.class);
                intent.putExtra(Constant.FLOW, Constant.NORMAL);
                activity.startActivity(intent);
            });
        }else {
            Intent intent = new Intent(activity, LoginFaceIDActivity.class);
            intent.putExtra(Constant.SKIP_FACE_ID, 0);
            activity.startActivity(intent);
        }
    }

    public void showVisibility() {
        if (Skip_FaceID_tv == true) {
            binding.tvSkipFaceid.setVisibility(View.VISIBLE);
        } else {
            binding.tvSkipFaceid.setVisibility(View.GONE);
        }
    }
}