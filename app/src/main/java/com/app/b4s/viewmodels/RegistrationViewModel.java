package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityRegistrationBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.OtpActivity;
import com.app.b4s.view.Register.SetPasswordActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegistrationViewModel extends ViewModel {

    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    public boolean mobileVerify, emailverify;
    public ActivityRegistrationBinding binding;
    public Activity context;
    Session session;

    public MutableLiveData<User> getUser(Boolean mobileVerify, Boolean emailverify, ActivityRegistrationBinding binding, Activity activity) {
        this.context = activity;
        this.binding = binding;
        this.mobileVerify = mobileVerify;
        this.emailverify = emailverify;
        session = new Session(activity);

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {
        validateUniqueId();
    }

    public void showPasswordActivity() {
        if (mobileVerify) {
            String uniqueID = binding.edUniqueId.getText().toString();
            Intent intent = new Intent(context, SetPasswordActivity.class);
            intent.putExtra(Constant.FLOW, Constant.NORMAL);
            intent.putExtra(Constant.UNIQUE_ID, uniqueID);
            context.startActivity(intent);
        }
    }

    public void verifyMobile() {
        if (binding.edMobilenoId.getText().toString().equals("")) {
            binding.edMobilenoId.setError(context.getString(R.string.enter_mobile_no));
            binding.edMobilenoId.requestFocus();
        } else {
            session.setData(Constant.MOBILE, binding.edMobilenoId.getText().toString());
            showOtpActivity(binding.edMobilenoId.getText().toString(), Constant.MOBILE);
        }

    }

    public void verifyEmail() {
        if (binding.edEmailId.getText().toString().equals("")) {
            binding.edEmailId.setError(context.getString(R.string.enter_mail_id));
            binding.edEmailId.requestFocus();
        } else {
            session.setData(Constant.EMAIL, binding.edEmailId.getText().toString());
            showOtpActivity(binding.edEmailId.getText().toString(), Constant.EMAIL);
        }
    }

    private void showOtpActivity(String dataText, String dataType) {
        Intent intent = new Intent(context, OtpActivity.class);
        intent.putExtra(Constant.DESCRIPTION, dataText);
        intent.putExtra(Constant.TYPE, dataType);
        context.startActivity(intent);
        context.finish();

    }

    public void onUsernameTextChanged(CharSequence text) {
        // TODO do something with text
        if (!text.toString().equals("")) {
            binding.btnContinue.setEnabled(true);
            binding.btnContinue.setBackgroundTintList(context.getResources().getColorStateList(R.color.btncolor));
        } else {
            binding.btnContinue.setEnabled(false);
            binding.btnContinue.setBackgroundTintList(context.getResources().getColorStateList(R.color.btncolor));
        }
    }

    private void validateUniqueId() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, binding.edUniqueId.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        session.setData(Constant.UNIQUE_ID,binding.edUniqueId.getText().toString());
                        /// Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        binding.llInputFeild.setVisibility(View.VISIBLE);
                        binding.rlUniqueInp.setVisibility(View.GONE);
                        binding.btnContinue.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(context, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, context, Constant.VALIDATEUNIQUEID_URL, params, true, 1);

    }

    public void showActivity() {
        if (session.getBoolean(Constant.MOBILE_OTP_VERIFY)) {
            layoutVisibility();
            binding.edMobilenoId.setText(session.getData(Constant.MOBILE));
            mobileVerify = true;
            binding.tvVerifyMobile.setVisibility(View.GONE);
            binding.ivMobileTick.setVisibility(View.VISIBLE);
        }
        if (session.getBoolean(Constant.EMAIL_OTP_VERIFY)) {
            layoutVisibility();
            binding.edEmailId.setText(session.getData(Constant.EMAIL));
            emailverify = true;
            binding.tvVerifyEmail.setVisibility(View.GONE);
            binding.ivMailTick.setVisibility(View.VISIBLE);
        }
        if (emailverify || mobileVerify) {
            binding.btnContinuePass.setEnabled(true);
            binding.btnContinuePass.setVisibility(View.VISIBLE);
        }
    }

    private void layoutVisibility() {
        binding.llInputFeild.setVisibility(View.VISIBLE);
        binding.rlUniqueInp.setVisibility(View.GONE);
        binding.btnContinue.setVisibility(View.GONE);
    }

}