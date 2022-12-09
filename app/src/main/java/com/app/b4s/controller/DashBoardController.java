package com.app.b4s.controller;

import android.app.Activity;

import com.app.b4s.commons.ResponseListener;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashBoardController implements IDashBoardController {
    ResponseListener responseListener;
    Session session;

    public DashBoardController(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void getThisDay(Activity context) {
        session = new Session(context);
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        session.setData(Constant.ON_THIS_DAY, jsonArray.getJSONObject(0).getString("text"));
                        responseListener.onSuccess(Constant.SUCCESS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, context, Constant.GET_ON_THIS_DAT, params, true, 0);

    }

    @Override
    public void getThought(Activity context) {
        session = new Session(context);
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        session.setData(Constant.QUOTE, jsonArray.getJSONObject(0).getString(Constant.QUOTE));
                        responseListener.onSuccess(Constant.SUCCESS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, context, Constant.GET_THOUGHT_OF_THE_DAY, params, true, 0);
    }
}
