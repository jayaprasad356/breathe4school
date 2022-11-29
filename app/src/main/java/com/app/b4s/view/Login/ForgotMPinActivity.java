package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityForgotMpinBinding;
import com.app.b4s.databinding.ActivitySetPasswordBinding;
import com.app.b4s.view.Register.SetMPinActivity;

public class ForgotMPinActivity extends AppCompatActivity {
    TextView tvTimer, tvTimeout, tvResentotp, tvInvalidotp;
    EditText edOTPId;
    Button btnProceed;
    Activity activity;
    LinearLayout rlOTPInp;
    ImageView ivOtpTick;
    private ActivityForgotMpinBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ForgotMPinActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_mpin);

        tvTimer = binding.tvTimer;
        tvResentotp = binding.tvResentotp;
        tvTimeout = binding.tvTimeout;
        edOTPId = binding.edOTPId;
        btnProceed = binding.btnProceed;
        tvInvalidotp = binding.tvInvalidotp;
        rlOTPInp = binding.rlOTPInp;
        ivOtpTick = binding.ivOtpTick;

        timerstart();

        edOTPId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 6) {
                    btnProceed.setEnabled(true);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });

        tvResentotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvResentotp.setTextColor(getResources().getColorStateList(R.color.gray));
                tvTimeout.setVisibility(View.GONE);
                timerstart();
            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceeed();
            }
        });

    }

    private void proceeed() {

        if (edOTPId.getText().toString().equals(getString(R.string.test_num))) {
            Intent intent = new Intent(activity, SetMPinActivity.class);
            startActivity(intent);

        } else {

            GradientDrawable drawable = (GradientDrawable) rlOTPInp.getBackground();
//            drawable.mutate(); // only change this instance of the xml, not all components using this xml
            drawable.setStroke(2, Color.RED); // set stroke width and stroke color
            tvInvalidotp.setVisibility(View.VISIBLE);
            tvInvalidotp.setText(R.string.invalid_otp_enter);
            tvTimeout.setVisibility(View.GONE);

        }
    }

    private void timerstart() {

        new CountDownTimer(45000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvResentotp.setEnabled(false);
                int seconds = (int) (millisUntilFinished / 1000);
                tvTimer.setText(String.format(getString(R.string.resend_time_format), seconds / 60, seconds % 60));

            }

            public void onFinish() {

                tvResentotp.setEnabled(true);
                tvTimeout.setVisibility(View.VISIBLE);
                tvInvalidotp.setVisibility(View.GONE);
                tvResentotp.setTextColor(getResources().getColorStateList(R.color.black));

            }

        }.start();
    }


}