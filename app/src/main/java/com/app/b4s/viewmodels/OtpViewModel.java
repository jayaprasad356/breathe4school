package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityOtpBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpViewModel extends ViewModel {
    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    ActivityOtpBinding binding;
    String otpType;
    Session session;
    Activity activity;
    public String url;

    public MutableLiveData<User> getUser(ActivityOtpBinding binding, String otpType, Activity activity) {
        this.activity=activity;
        this.binding=binding;
        this.otpType=otpType;
        session=new Session(activity);


        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {
        proceeed(otpType);
    }
    public void onBack() {
       activity.onBackPressed();
    }
    public void reSendOtp(){
        binding.tvResentotp.setTextColor(activity.getResources().getColorStateList(R.color.gray));
        binding.tvTimeout.setVisibility(View.GONE);
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

    private void proceeed(String otpType) {
        if (otpType.equals(Constant.EMAIL)) {
            if (binding.edOTPId.getText().length() >= 4) {
                url = Constant.VALIDATE_EMAIL_OTP;
                verifyOtp(Constant.EMAIL_OTP, binding.edOTPId.getText().toString().trim(), url);
            }
        } else if (otpType.equals(Constant.MOBILE)) {
            if (binding.edOTPId.getText().length() == 4) {
                url = Constant.VALIDATE_MOBILE_OTP;
                verifyOtp(Constant.MOBILE_OTP, binding.edOTPId.getText().toString().trim(), url);
            }
        } else {
            GradientDrawable drawable = (GradientDrawable) binding.rlOTPInp.getBackground();
//            drawable.mutate(); // only change this instance of the xml, not all components using this xml
            drawable.setStroke(2, Color.RED); // set stroke width and stroke color
            binding.tvInvalidotp.setVisibility(View.VISIBLE);
            binding.tvInvalidotp.setText(R.string.invalid_otp);
            binding.tvTimeout.setVisibility(View.GONE);


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
                        if (otpType.equals(Constant.EMAIL)) {
                            session.setBoolean(Constant.EMAIL_OTP_VERIFY,true);
                        } else if ((otpType.equals(Constant.MOBILE))) {
                            session.setBoolean(Constant.MOBILE_OTP_VERIFY, true);
                        }
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, RegistrationActivity.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, url, params, true, 1);

    }
    public void onUsernameTextChanged(CharSequence text) {
        // TODO do something with text
        if (text.length() >= 4) {
            binding.btnProceed.setEnabled(true);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
        } else {
            binding.btnProceed.setEnabled(false);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

        }
    }
}