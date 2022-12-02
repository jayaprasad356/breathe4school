package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityOtpBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.OtpViewModel;
import com.app.b4s.viewmodels.RegistrationViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {

    Activity activity;
    OtpViewModel otpViewModel;
    private ActivityOtpBinding binding;
    String otpType, description;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = OtpActivity.this;
        session = new Session(activity);

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        binding.setLifecycleOwner(this);
        binding.setViewModel(otpViewModel);
        Intent intent = getIntent();

        otpType = intent.getStringExtra(Constant.TYPE);
        description = intent.getStringExtra(Constant.DESCRIPTION);
        binding.tvdescription.setText(getString(R.string.otp_header) + " " + description);

        otpViewModel.getUser(binding,otpType,activity).observe(this, user -> {});
        otpViewModel.timerstart();

    }
}

