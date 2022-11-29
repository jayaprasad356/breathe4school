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

public class RegisterSuccessfullActivity extends AppCompatActivity {

    TextView tvTitle, tvdescription;
    Activity activity;

    Handler handler;
    ActivitySuccessfullBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_successfull);
        activity = RegisterSuccessfullActivity.this;

        tvTitle = binding.tvTitle;
        tvdescription = binding.tvdescription;

        tvTitle.setText(getIntent().getStringExtra(Constant.TITLE));
        tvdescription.setText(getIntent().getStringExtra(Constant.DESCRIPTION));

        handler = new Handler();
        GotoActivity();

    }


    private void GotoActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RegisterSuccessfullActivity.this, RegistrationActivity.class);
                startActivity(intent);
                finish();

            }
        }, 2000);
    }
}