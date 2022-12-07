package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySuccessfullBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginFaceIDActivity;

public class RegisterSuccessfullActivity extends AppCompatActivity {

    TextView tvTitle, tvdescription;
    Activity activity;

    Handler handler;
    ActivitySuccessfullBinding binding;
    public String uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_successfull);
        activity = RegisterSuccessfullActivity.this;

        tvTitle = binding.tvTitle;
        tvdescription = binding.tvdescription;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        tvTitle.setText(getIntent().getStringExtra(Constant.TITLE));
        tvdescription.setText(getIntent().getStringExtra(Constant.DESCRIPTION));

        handler = new Handler();
        GotoActivity();

    }


    private void GotoActivity() {
        handler.postDelayed(() -> {
            Intent intent = new Intent(RegisterSuccessfullActivity.this, LoginFaceIDActivity.class);
            intent.putExtra(Constant.UNIQUE_ID, uniqueId);
            intent.putExtra(Constant.SKIP_FACE_ID, 0);
            startActivity(intent);
            finish();
        }, 2000);
    }
}