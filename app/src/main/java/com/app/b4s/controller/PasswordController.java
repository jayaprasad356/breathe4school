package com.app.b4s.controller;

import android.app.Activity;
import android.widget.Toast;

import com.app.b4s.commons.ResponseListener;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasswordController implements IPasswordController {
    ResponseListener responseListener;
    Session session;

    public PasswordController(ResponseListener responseListener) {
        this.responseListener=responseListener;
    }

    @Override
    public void setPassword(String password, Activity activity) {
        session=new Session(activity);
        String uniqueID = session.getData(Constant.UNIQUE_ID);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueID);
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        responseListener.setPasswordSuccess();
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.RESET_PASSWORD, params, true, 2);
    }
}
