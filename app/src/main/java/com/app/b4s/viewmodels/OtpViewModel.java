package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.b4s.R;
import com.app.b4s.databinding.ActivityOtpBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.RegistrationActivity;
import com.app.b4s.view.ToastMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpViewModel extends ViewModel {
    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    ActivityOtpBinding binding;
    String otpType;
    Session session;
    Activity activity;
    public String url;
    public ToastMessage toastMessage = new ToastMessage();


    public MutableLiveData<User> getUser(ActivityOtpBinding binding, String otpType, Activity activity) {
        this.activity = activity;
        this.binding = binding;
        this.otpType = otpType;
        session = new Session(activity);


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

    public void reSendOtp() {
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
                verifyOYP(Constant.EMAIL_OTP, url, Integer.parseInt(binding.edOTPId.getText().toString().trim()));
                //verifyOtp(Constant.EMAIL_OTP, binding.edOTPId.getText().toString().trim(), url);
            }
        } else if (otpType.equals(Constant.MOBILE)) {
            if (binding.edOTPId.getText().length() == 4) {
                url = Constant.VALIDATE_MOBILE_OTP;
                verifyOYP(Constant.MOBILE_OTP, url, Integer.parseInt(binding.edOTPId.getText().toString().trim()));
                // verifyOtp(Constant.MOBILE_OTP, binding.edOTPId.getText().toString().trim(), url);
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

    private void verifyOYP(String type, String url, int otpText) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(type, otpText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ApiConfig.isConnected(activity)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TEST_RES", response.toString());

                            try {
                                if (response.getBoolean(Constant.STATUS)) {
                                    Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                                    if (otpType.equals(Constant.EMAIL)) {
                                        session.setBoolean(Constant.EMAIL_OTP_VERIFY, true);
                                    } else if ((otpType.equals(Constant.MOBILE))) {
                                        session.setBoolean(Constant.MOBILE_OTP_VERIFY, true);
                                    }
                                    Intent intent = new Intent(activity, RegistrationActivity.class);
                                    activity.startActivity(intent);
                                    activity.finish();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.print(response);

                        }
                    }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            error.printStackTrace();
                            Toast.makeText(activity, activity.getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                        }


                    });
            ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
        }

    }

    public void onUserTextChanged(CharSequence text) {
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