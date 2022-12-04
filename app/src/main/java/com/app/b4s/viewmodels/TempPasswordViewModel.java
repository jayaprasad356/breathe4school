package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginTempPasswordBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginSuccessfullActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TempPasswordViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData;
    ActivityLoginTempPasswordBinding binding;
    Session session;
    Activity activity;

    public MutableLiveData<User> getUser(ActivityLoginTempPasswordBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
        session = new Session(activity);

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void generatePass() {
        binding.rlGenrateTempCode.setVisibility(View.VISIBLE);
        binding.rlEnterTempCode.setVisibility(View.GONE);
    }

    public void onClick() {
        loginWithMPin(session.getData(Constant.UNIQUE_ID), binding.edMPinId.getText().toString());
    }

    public void forgotPin() {
        binding.rlGenrateTempCode.setVisibility(View.VISIBLE);
        binding.rlEnterTempCode.setVisibility(View.GONE);
    }

    public void login() {
        binding.rlGenrateTempCode.setVisibility(View.GONE);
        binding.rlEnterTempCode.setVisibility(View.VISIBLE);
    }

    public void ivBack() {
        activity.onBackPressed();
    }

    public void tempPinTextChanged(CharSequence text, int start) {
        if (!text.toString().equals("")) {
            binding.btnProceed.setEnabled(true);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
        } else {
            binding.btnProceed.setEnabled(false);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
        }
    }

    public void showTempPassword() {
        binding.tvCode.setText(session.getData(Constant.TEMP_PASS));
    }

    private void loginWithMPin(String uniqueId, String mPin) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        params.put(Constant.MPIN, mPin);

        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
                        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.LOGIN_WITH_MPIN, params, true, 1);
    }
}
