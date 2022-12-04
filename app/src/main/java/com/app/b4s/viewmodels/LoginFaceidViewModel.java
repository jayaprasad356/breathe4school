package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.databinding.ActivityLoginFaceIdactivityBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginMPinActivity;
import com.app.b4s.view.Login.LoginPasswordActivity;
import com.app.b4s.view.Login.LoginTempPasswordActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFaceidViewModel extends ViewModel {
    private MutableLiveData<User> userMutableLiveData;
    ActivityLoginFaceIdactivityBinding binding;
    Session session;
    Activity activity;

    public MutableLiveData<User> getUser(ActivityLoginFaceIdactivityBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
        session = new Session(activity);

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onFaceIdClick() {
        binding.llFaceId.setVisibility(View.GONE);
        binding.llFaceidNotMatch.setVisibility(View.VISIBLE);
    }

    public void generateMpin() {
        generateTempMPin(session.getData(Constant.UNIQUE_ID));
    }

    public void clickPin() {
        Intent intent = new Intent(activity, LoginMPinActivity.class);
        intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        activity.startActivity(intent);
    }

    public void clickPassword() {
        Intent intent = new Intent(activity, LoginPasswordActivity.class);
        intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        activity.startActivity(intent);
    }

    private void generateTempMPin(String uniqueId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        JSONObject dataObject = jsonObject.getJSONObject(Constant.DATA);
                        String tempPin = dataObject.get(Constant.TICKET_NUMBER).toString();
                        session.setData(Constant.TEMP_PASS, tempPin);
                        Intent intent = new Intent(activity, LoginTempPasswordActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID, uniqueId);
                        activity.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.GENERATE_TEMP_MPIN, params, true, 1);
    }
}
