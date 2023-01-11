package com.app.b4s.controller;

import androidx.fragment.app.FragmentActivity;

import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.DCM.FilterListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FilterController implements IfilterController {
    FilterListener filterListener;
    Session session;

    public FilterController(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    @Override
    public void getFilterHomeWork(String type, FragmentActivity activity) {
        session = new Session(activity);
        String url;
       // url = Constant.FILTER_BY_STUDENT_ID + session.getData(Constant.STUDENT_ID)+"/"+"completed";
        url = Constant.FILTER_BY_STUDENT_ID +session.getData(Constant.STUDENT_ID)+"/"+type;
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        filterListener.onSuccess(jsonArray);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, url, params, true, 0);

    }

    @Override
    public void getFilterPreRead(String type, FragmentActivity activity) {
        session = new Session(activity);
        String url;
        // url = Constant.FILTER_BY_STUDENT_ID + session.getData(Constant.STUDENT_ID)+"/"+"completed";
        url = Constant.FILTER_BY_STUDENT_ID +session.getData(Constant.STUDENT_ID)+"/"+type;
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        filterListener.onSuccess(jsonArray);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, url, params, true, 0);
    }
}
