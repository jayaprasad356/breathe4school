package com.app.b4s.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.b4s.TestActivity;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

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
        ArrayList<String> dayData = new ArrayList<>();


        try {
            jsonObject.put(Constant.ACADEMIC_YEAR_ID, session.getData(Constant.ACADEMIC_YEAR_ID));
            jsonObject.put(Constant.SCHOOL_ID, session.getData(Constant.SCHOOL_ID));
            jsonObject.put(Constant.STANDARD_ID, session.getData(Constant.STANDARD_ID));
            jsonObject.put(Constant.SECTION_ID, session.getData(Constant.SECTION_ID));
            jsonObject.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            jsonObject.put(Constant.TIME_TABLE_SESSION_ID, session.getData(Constant.TIME_TABLE_SESSION_ID));
            lecture.put(Constant.NAME, session.getData(Constant.PLANER_NAME));
            lecture.put(Constant.DESCRIPTIO, session.getData(Constant.PLANER_DESC));
            lecture.put(Constant.START_TIME, session.getData(Constant.START_TIME));
            lecture.put(Constant.END_TIME, session.getData(Constant.END_TIME));
            lecture.put(Constant.SUBJECT_ID, session.getData(Constant.SELECTED_SUBJECT_ID));

            JSONArray days = new JSONArray();
            for (int i = 0; i < checkBoxData.size(); i++){
                days.put(checkBoxData.get(i));
            }
            lecture.put(Constant.DAY, days);


            jsonArray.put(lecture);
            schedule.put(Constant.LECTURES, jsonArray);
            jsonObject.put(Constant.SCHEDULES, schedule);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = Constant.SERVER+"/api/v1/studyPlanner/create";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject, response -> {
                    Log.d("STUDENT_RESPONSE", response.toString());
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();


                    try {
                        //TODO: Handle your response here
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.print(response);

                }, error -> {
                    // TODO: Handle error
                    String body = "";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if (error.networkResponse.data != null) {
                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                            Toast.makeText(activity, "" + body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }


                });

        ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
