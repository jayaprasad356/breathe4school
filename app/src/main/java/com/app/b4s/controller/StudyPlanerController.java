package com.app.b4s.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StudyPlanerController implements IStudyPlanerController {
    ResponseListener responseListener;
    private Session session;

    public StudyPlanerController(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void createStudyPlaner(Activity activity, ArrayList<String> checkBoxData) {
        session = new Session(activity);
        JSONObject schedule = new JSONObject();
        JSONObject lecture = new JSONObject();

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {

            jsonObject.put(Constant.ACADEMIC_YEAR_ID, session.getData(Constant.ACADEMIC_YEAR_ID));
            jsonObject.put(Constant.SCHOOL_ID, session.getData(Constant.SCHOOL_ID));
            jsonObject.put(Constant.STANDARD_ID, session.getData(Constant.STANDARD_ID));
            jsonObject.put(Constant.SECTION_ID, session.getData(Constant.SECTION_ID));
            jsonObject.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            jsonObject.put(Constant.TIME_TABLE_SESSION_ID, session.getData(Constant.TIME_TABLE_SESSION_ID));
            lecture.put(Constant.NAME, "Hindi");
            lecture.put(Constant.DESCRIPTIO, "exam");
            lecture.put(Constant.START_TIME, "1800");
            lecture.put(Constant.END_TIME, "2000");
            lecture.put(Constant.SUBJECT_ID, "638d71469c3aacfcfd7b064e");
            lecture.put("day", checkBoxData);
            jsonObject.put("schedules", schedule);
            schedule.put("lectures", jsonArray);
            jsonArray.put(lecture);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://143.244.132.170:3001/api/v1/studyPlanner/create";

        if (ApiConfig.isConnected(activity)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, response -> {
                        Log.d("TEST_RES", response.toString());
                        try {
                            if (response.getBoolean(Constant.STATUS)) {
                                Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }, error -> {
                        // TODO: Handle error
                        error.printStackTrace();
                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
                    });
            ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
}
