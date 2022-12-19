package com.app.b4s.view.Login;

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
import com.app.b4s.databinding.ActivityForgotMpinBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.SetMPinActivity;

import java.net.ServerSocket;

public class ForgotMPinActivity extends AppCompatActivity {
    Activity activity;
    private Session session;
    ActivityForgotMpinBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ForgotMPinActivity.this;
        session=new Session(activity);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_mpin);
        binding.btnProceed.setOnClickListener(view -> proceeed());
        binding.tvResentotp.setOnClickListener(view -> reSendOtp());
        binding.tvdescription.setText(session.getData(Constant.EMAIL));
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
                    binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

                } else {
                    binding.btnProceed.setEnabled(false);
                    binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

                }
            }
        });
        timerstart();
    }

    public void timerstart() {

        new CountDownTimer(45000, 1000) {

            public void onTick(long millisUntilFinished) {
                binding.tvResentotp.setEnabled(false);
                int seconds = (int) (millisUntilFinished / 1000);
                binding.tvTimer.setText(String.format(activity.getString(R.string.resend_time_format), seconds / 60, seconds % 60));

            }

            public void onFinish() {

                binding.tvResentotp.setEnabled(true);
                binding.tvTimeout.setVisibility(View.VISIBLE);
                binding.tvInvalidotp.setVisibility(View.GONE);
                binding.tvResentotp.setTextColor(activity.getResources().getColorStateList(R.color.black));

            }

        }.start();
    }
    public void reSendOtp(){
        binding.tvResentotp.setTextColor(activity.getResources().getColorStateList(R.color.gray));
        binding.tvTimeout.setVisibility(View.GONE);
        timerstart();
    }
    private void proceeed() {
        if (binding.edOTPId.getText().toString().equals(activity.getString(R.string.test_num))) {
            Intent intent = new Intent(activity, SetMPinActivity.class);
            intent.putExtra(Constant.FLOW,Constant.FORGOT);
            activity.startActivity(intent);

        } else {
            GradientDrawable drawable = (GradientDrawable) binding.rlOTPInp.getBackground();
            drawable.setStroke(2, Color.RED); // set stroke width and stroke color
            binding.tvInvalidotp.setVisibility(View.VISIBLE);
            binding.tvInvalidotp.setText(R.string.invalid_otp_enter);
            binding.tvTimeout.setVisibility(View.GONE);

        }
    }
}