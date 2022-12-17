package com.app.b4s.controller;

import androidx.fragment.app.FragmentActivity;

import com.app.b4s.commons.ResponseListener;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CalendarController implements ICalendarController{
    private CalendarResponse calendarResponse;
    private Session session;


    public CalendarController(CalendarResponse calendarResponse) {
        this.calendarResponse=calendarResponse;
    }

    @Override
    public void loadTimeTable(FragmentActivity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        JSONArray jsonArray1=jsonArray.getJSONObject(0).getJSONArray(Constant.SCHEDULES).getJSONObject(0).getJSONArray(Constant.LECTURES);
                        System.out.println(jsonArray1);
                        calendarResponse.onSuccess(jsonArray1);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.GET_ALL_TIME_TABLE, params, true, 0);
    }
}
