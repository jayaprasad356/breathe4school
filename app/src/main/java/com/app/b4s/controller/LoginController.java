package com.app.b4s.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.app.b4s.commons.ResponseListener;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginSuccessfullActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginController implements ILoginController{
    ResponseListener responseListener;
    Session session;
    public LoginController(ResponseListener responseListener) {
        this.responseListener=responseListener;
    }

    @Override
    public void onMPinLogin(String mpin, Activity activity) {
        session=new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID,session.getData(Constant.UNIQUE_ID) );
        params.put(Constant.MPIN, mpin);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        responseListener.onSuccess(jsonObject.getString(Constant.MESSAGE));
                    } else {
                       responseListener.onFailure(jsonObject.getString(Constant.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.LOGIN_WITH_MPIN, params, true, 1);
    }

    @Override
    public void loginWithPassword(String password, Activity activity) {
        session= new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        responseListener.onSuccess(jsonObject.getString(Constant.MESSAGE));
                    } else {
                        responseListener.onFailure(jsonObject.getString(Constant.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.LOGIN_WITH_PASSWORD, params, true, 1);
    }

}

