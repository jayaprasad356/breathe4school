package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetFaceIdBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginFaceIDActivity;

public class SetFaceIdActivity extends AppCompatActivity {

    ImageButton ibBackBtn;
    TextView tvSkipFaceid;
    Boolean Skip_FaceID_tv = false;
    ActivitySetFaceIdBinding binding;
    Activity activity;
    String uniqueId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_face_id);
        activity = SetFaceIdActivity.this;
        ibBackBtn = binding.ibBackBtn;
        tvSkipFaceid = binding.tvSkipFaceid;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);


        Skip_FaceID_tv = getIntent().getExtras().getBoolean(Constant.SKIP_FACE_ID);

        ibBackBtn.setOnClickListener(v -> onBackPressed());
        binding.ivFaceLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LoginFaceIDActivity.class);
                intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                startActivity(intent);
            }
        });


        if (Skip_FaceID_tv == true) {

            tvSkipFaceid.setVisibility(View.VISIBLE);

        } else {
            tvSkipFaceid.setVisibility(View.GONE);

        }


    }
}