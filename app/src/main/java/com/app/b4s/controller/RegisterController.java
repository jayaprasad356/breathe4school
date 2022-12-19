package com.app.b4s.controller;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginTempPasswordActivity;
import com.app.b4s.view.Register.RegisterSuccessfullActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterController implements IRegisterController {
    Session session;
    IRegisterView registerView;

    public RegisterController(IRegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void onRegister(String uniqueId, Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONObject data = jsonObject.getJSONObject(Constant.DATA);
                        session.setData(Constant.EMAIL, data.getString(Constant.EMAIL));
                        session.setData(Constant.MOBILE, data.getString(Constant.MOBILE_NUMBER));
                        session.setData(Constant.NAME,data.getString(Constant.NAME));
                        registerView.onRegisterSuccess(jsonObject.getString(Constant.MESSAGE));
                    } else {
                        registerView.onRegisterError(jsonObject.getString(Constant.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.VALIDATEUNIQUEID_URL, params, true, 1);
    }

    @Override
    public void onLogin(String uniqueId, Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        registerView.onLoginSuccess();
                    } else {
                        registerView.onLoginFailure(jsonObject.getString(Constant.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.VALIDATEUNIQUEID_URL, params, true, 1);
    }

    @Override
    public void setMPinPassword(String mPin, Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        params.put(Constant.MPIN, mPin);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        registerView.onResetSuccess(jsonObject.getString(Constant.MESSAGE));
                    } else {
                        registerView.onRegisterError(jsonObject.getString(Constant.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.RESET_MPIN, params, true, 2);
    }

    @Override
    public void onRegisterUser(Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        params.put(Constant.MPIN, session.getData(Constant.MPIN));
        params.put(Constant.PASSWORD, session.getData(Constant.PASSWORD));
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        registerView.onRegisterSuccess(jsonObject.getString(Constant.MESSAGE));
                    } else {
                        registerView.onRegisterError(jsonObject.getString(Constant.MESSAGE));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.REGISTER_USER, params, true, 1);
    }

    @Override
    public void generateTempMpin(Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONObject dataObject = jsonObject.getJSONObject(Constant.DATA);
                        int tempTicket = dataObject.getInt(Constant.TICKET_NUMBER);
                        String ticketNum = Integer.toString(tempTicket);
                        session.setData(Constant.TEMP_PASS, ticketNum);
                    } else {
                       // registerView.onRegisterSuccess(jsonObject.getString(Constant.MESSAGE));
                    }
                    registerView.onRegisterSuccess(jsonObject.getString(Constant.MESSAGE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.GENERATE_TEMP_MPIN, params, true, 1);
    }
}
