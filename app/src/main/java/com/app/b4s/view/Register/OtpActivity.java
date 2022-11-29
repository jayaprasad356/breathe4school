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
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.OtpViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {

    TextView tvTimer, tvTimeout, tvResentotp, tvInvalidotp;
    EditText edOTPId;
    Button btnProceed;
    Activity activity;
    LinearLayout rlOTPInp;
    ImageView ivOtpTick;
    OtpViewModel otpViewModel;
    private ActivityOtpBinding binding;
    String otpType;
    String url;


    ImageButton ibBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = OtpActivity.this;

        otpViewModel = ViewModelProviders.of(this).get(OtpViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        binding.setLifecycleOwner(this);
        binding.setViewModel(otpViewModel);
        Intent intent = getIntent();

        otpType = intent.getStringExtra(Constant.TYPE);


        tvTimer = binding.tvTimer;
        tvResentotp = binding.tvResentotp;
        tvTimeout = binding.tvTimeout;
        edOTPId = binding.edOTPId;
        btnProceed = binding.btnProceed;
        tvInvalidotp = binding.tvInvalidotp;
        rlOTPInp = binding.rlOTPInp;
        ibBackBtn = binding.ibBackBtn;
        ivOtpTick = binding.ivOtpTick;
        otpViewModel.getUser().observe(this, user -> {
            proceeed(otpType);
        });


        ibBackBtn.setOnClickListener(v -> onBackPressed());
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
                if (editable.length() == 4) {
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


    }

    private void proceeed(String otpType) {
        if (otpType.equals(Constant.EMAIL)) {
            if (edOTPId.getText().length() == 4) {
                url = Constant.VALIDATE_EMAIL_OTP;
                verifyOtp(Constant.EMAIL_OTP, edOTPId.getText().toString().trim(), url);
            }
        } else if (otpType.equals(Constant.MOBILE)) {
            if (edOTPId.getText().length() == 4) {
                url = Constant.VALIDATE_MOBILE_OTP;
                verifyOtp(Constant.MOBILE_OTP, edOTPId.getText().toString().trim(), url);
            }
        } else {
            GradientDrawable drawable = (GradientDrawable) rlOTPInp.getBackground();
//            drawable.mutate(); // only change this instance of the xml, not all components using this xml
            drawable.setStroke(2, Color.RED); // set stroke width and stroke color
            tvInvalidotp.setVisibility(View.VISIBLE);
            tvInvalidotp.setText(R.string.invalid_otp);
            tvTimeout.setVisibility(View.GONE);


        }
    }

    private void verifyOtp(String type, String otp, String url) {
        Map<String, String> params = new HashMap<>();
        params.put(type, otp);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, url, params, true, 1);

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

