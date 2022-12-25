package com.app.b4s.view.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySuccessfullBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Home.Activity.HomeActivity;

public class LoginSuccessfullActivity extends AppCompatActivity {

    TextView tvTitle, tvdescription;
    Activity activity;
    ActivitySuccessfullBinding binding;
    Handler handler;
    String uniqueId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_successfull);
        activity = LoginSuccessfullActivity.this;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        tvTitle = binding.tvTitle;
        tvdescription = binding.tvdescription;


        tvTitle.setText(getIntent().getStringExtra(Constant.TITLE));
        tvdescription.setText(getIntent().getStringExtra(Constant.DESCRIPTION));

        handler = new Handler();
        GotoActivity();


    }


    private void GotoActivity() {
        handler.postDelayed(() -> {
            Intent intent = new Intent(LoginSuccessfullActivity.this, HomeActivity.class);
            finishAffinity();
            intent.putExtra(Constant.SKIP_FACE_ID, true);
            intent.putExtra(Constant.FLOW, Constant.NORMAL);
            intent.putExtra(Constant.UNIQUE_ID, uniqueId);
            startActivity(intent);
            finish();
        }, 2000);
    }
}