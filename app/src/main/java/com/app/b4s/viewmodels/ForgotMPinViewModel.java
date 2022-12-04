package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.CountDownTimer;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityForgotMpinBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.SetMPinActivity;

public class ForgotMPinViewModel extends ViewModel {
    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    ActivityForgotMpinBinding binding;
    Session session;
    Activity activity;
    String flow;

    public MutableLiveData<User> getUser(ActivityForgotMpinBinding binding, Activity activity, String flow) {
        this.binding = binding;
        this.activity = activity;
        this.flow=flow;

        session = new Session(activity);

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {
        proceeed();
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
            intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
            activity.startActivity(intent);

        } else {

            GradientDrawable drawable = (GradientDrawable) binding.rlOTPInp.getBackground();
//            drawable.mutate(); // only change this instance of the xml, not all components using this xml
            drawable.setStroke(2, Color.RED); // set stroke width and stroke color
            binding.tvInvalidotp.setVisibility(View.VISIBLE);
            binding.tvInvalidotp.setText(R.string.invalid_otp_enter);
            binding.tvTimeout.setVisibility(View.GONE);

        }
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
    public void otpTextChanged(CharSequence text,int start){
        if (text.length() >= 4) {
            binding.btnProceed.setEnabled(true);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

        } else {
            binding.btnProceed.setEnabled(false);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

        }

    }
}
