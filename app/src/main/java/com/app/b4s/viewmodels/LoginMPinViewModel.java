package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginMpinBinding;
import com.app.b4s.databinding.ActivityLoginTempPasswordBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.ForgotMPinActivity;
import com.app.b4s.view.Login.LoginPasswordActivity;
import com.app.b4s.view.Login.LoginSuccessfullActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginMPinViewModel extends ViewModel {
    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    ActivityLoginMpinBinding binding;
    Session session;
    Activity activity;
    public MutableLiveData<User> getUser(ActivityLoginMpinBinding binding, Activity activity) {
        this.binding = binding;
        this.activity = activity;
        session = new Session(activity);
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {

        User user = new User(UniqueId.getValue());

        userMutableLiveData.setValue(user);

    }
    public void onBackPress(){
        activity.onBackPressed();
    }
    public void mPinTextChanged(CharSequence text,int start){
        if (!text.toString().equals("")) {
            binding.btnProceed.setEnabled(true);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
        } else {
            binding.btnProceed.setEnabled(false);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

        }

    }
    public void doLogin(){
        loginWithMPin(session.getData(Constant.UNIQUE_ID), binding.edMPinId.getText().toString());
    }
    public void loginInsteadOfPassword(){
        Intent intent = new Intent(activity, LoginPasswordActivity.class);
        intent.putExtra(Constant.UNIQUE_ID,session.getData(Constant.UNIQUE_ID));
        activity.startActivity(intent);
    }
    public void forgotPin(){
        Intent intent = new Intent(activity, ForgotMPinActivity.class);
        intent.putExtra(Constant.UNIQUE_ID,session.getData(Constant.UNIQUE_ID));
        activity.startActivity(intent);
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
                        JSONObject dataObject1 = jsonObject.getJSONObject("data");
                        dataObject1.getString("name");
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
                        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
                        intent.putExtra(Constant.UNIQUE_ID,uniqueId);
//                intent.putExtra(   "Descripition","You have successfully completed the registration");
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
