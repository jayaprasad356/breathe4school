package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetFaceIdBinding;

import org.w3c.dom.Text;

public class SetFaceIdActivity extends AppCompatActivity {

    ImageButton ibBackBtn;
    TextView tvSkipFaceid;
    Boolean Skip_FaceID_tv = false;
    ActivitySetFaceIdBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_face_id);

        ibBackBtn = binding.ibBackBtn;
        tvSkipFaceid = binding.tvSkipFaceid;


        Skip_FaceID_tv =true; //getIntent().getExtras().getBoolean("SkipFaceId");

        ibBackBtn.setOnClickListener(v -> onBackPressed());


        if (Skip_FaceID_tv == true){

            tvSkipFaceid.setVisibility(View.VISIBLE);

        }

        else
        {
            tvSkipFaceid.setVisibility(View.GONE);

        }


    }
}