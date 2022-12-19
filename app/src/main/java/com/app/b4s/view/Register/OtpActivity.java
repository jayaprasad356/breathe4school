package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.IOtpVerifyController;
import com.app.b4s.controller.OtpVerifyController;
import com.app.b4s.databinding.ActivityOtpBinding;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;

import java.util.ArrayList;

public class OtpActivity extends AppCompatActivity implements ResponseListener {

    Activity activity;
    private ActivityOtpBinding binding;
    String otpType, description;
    Session session;
    private String url;
    IOtpVerifyController verifyController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = OtpActivity.this;
        session = new Session(activity);
        verifyController = new OtpVerifyController(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        Intent intent = getIntent();
        timerstart();
        binding.tvdescription.setText(session.getData(Constant.EMAIL));
        otpType = intent.getStringExtra(Constant.TYPE);
        description = intent.getStringExtra(Constant.DESCRIPTION);
        binding.tvdescription.setText(getString(R.string.otp_header) + " " + description);

        binding.btnProceed.setOnClickListener(view -> {
            if (otpType.equals(Constant.EMAIL)) {
                if (binding.edOTPId.getText().length() >= 4) {
                    verifyController.OnOtpVerify(Integer.parseInt(binding.edOTPId.getText().toString()), activity, Constant.EMAIL);
                }
            } else if (otpType.equals(Constant.MOBILE)) {
                if (binding.edOTPId.getText().length() >= 4) {
                    verifyController.OnOtpVerify(Integer.parseInt(binding.edOTPId.getText().toString()), activity, Constant.MOBILE);
                }
            } else {
                GradientDrawable drawable = (GradientDrawable) binding.rlOTPInp.getBackground();
                drawable.setStroke(2, Color.RED); // set stroke width and stroke color
                binding.tvInvalidotp.setVisibility(View.VISIBLE);
                binding.tvInvalidotp.setText(R.string.invalid_otp);
                binding.tvTimeout.setVisibility(View.GONE);
            }
        });
        binding.edOTPId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 4) {
                    binding.btnProceed.setEnabled(true);
                    binding.btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {
                    binding.btnProceed.setEnabled(false);
                    binding.btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });
        binding.ibBackBtn.setOnClickListener(View -> onBackPressed());

        binding.tvResentotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tvResentotp.setTextColor(getResources().getColorStateList(R.color.gray));
                binding.tvTimeout.setVisibility(View.GONE);
                timerstart();
            }
        });

    }

    @Override
    public void onSuccess(String type) {
        if (type.equals(Constant.EMAIL))
            session.setBoolean(Constant.EMAIL_OTP_VERIFY, true);
        else
            session.setBoolean(Constant.MOBILE_OTP_VERIFY, true);
        activity.finish();
        Intent intent = new Intent(activity, RegistrationActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void setPasswordSuccess() {

    }

    @Override
    public void OnSuccess(ArrayList<DayOfLine> arrayList) {

    }

    private void timerstart() {

        new CountDownTimer(45000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.tvResentotp.setEnabled(false);
                int seconds = (int) (millisUntilFinished / 1000);
                binding.tvTimer.setText(String.format(getString(R.string.resend_time_format), seconds / 60, seconds % 60));
            }

            public void onFinish() {
                binding.tvResentotp.setEnabled(true);
                binding.tvTimeout.setVisibility(View.VISIBLE);
                binding.tvInvalidotp.setVisibility(View.GONE);
                binding.tvResentotp.setTextColor(getResources().getColorStateList(R.color.black));
            }

        }.start();
    }


}

