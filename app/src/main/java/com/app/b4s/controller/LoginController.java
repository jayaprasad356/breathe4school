package com.app.b4s.controller;

import android.app.Activity;

import com.app.b4s.commons.ResponseListener;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginController implements ILoginController {
    ResponseListener responseListener;
    Session session;

    public LoginController(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void onMPinLogin(String mpin, Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        params.put(Constant.MPIN, mpin);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        setStudentData(data, activity);
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

    private void setStudentData(JSONObject data, Activity activity) {
        try {
            ArrayList<String> subjeccts = new ArrayList<>();
            ArrayList<String> subID = new ArrayList<>();
            JSONArray tI;

            tI = data.getJSONArray(Constant.TEACHER_INFORMATION);
            for (int i = 0; i < tI.length(); i++) {
                JSONObject jsonObject1 = tI.getJSONObject(i);
                if (jsonObject1 != null) {
                    System.out.println(jsonObject1);
                    for (int t = 0; t < jsonObject1.getJSONArray(Constant.SUBJECTS).length(); t++) {
                        JSONArray jsonSubject = jsonObject1.getJSONArray(Constant.SUBJECTS);
                        JSONObject subject = (JSONObject) jsonSubject.get(t);
                        String sub = subject.getString(Constant.NAME);
                        String id = subject.getString(Constant.ID);
                        subjeccts.add(sub);
                        subID.add(id);
                    }
                }
            }

            session.setArrayList(Constant.SUBJECTS_KEY, subjeccts);
            session.setArrayList(Constant.SUBJECTS_ID_KEY, subID);

            JSONObject academicData = data.getJSONObject(Constant.ACADEMIC_YEAR);
            JSONObject schoolData = data.getJSONObject(Constant.SCHOOL);
            JSONObject sectionData = data.getJSONObject(Constant.SECTION);
            JSONObject standardData = data.getJSONObject(Constant.STANDARD);
            JSONObject timeTableData = data.getJSONObject(Constant.TIMETABLESESSION);

            session.setData(Constant.NAME, data.getString(Constant.NAME));
            session.setData(Constant.ACADEMIC_YEAR_ID, academicData.getString(Constant.ID));
            session.setData(Constant.SCHOOL_ID, schoolData.getString(Constant.ID));
            session.setData(Constant.SECTION_ID, sectionData.getString(Constant.ID));
            session.setData(Constant.STANDARD_ID, standardData.getString(Constant.ID));
            session.setData(Constant.TIME_TABLE_SESSION_ID, timeTableData.getString(Constant.ID));

            session.setData(Constant.NAME, data.getString(Constant.NAME));
            session.setData(Constant.STUDENT_ID, data.getString(Constant.ID));
            session.setData(Constant.GENDER, data.getString(Constant.GENDER));
            session.setData(Constant.PARENT_EMAIL, data.getString(Constant.PARENT_EMAIL));
            session.setData(Constant.FATHER_NAME, data.getString(Constant.FATHER_NAME));
            session.setData(Constant.DOB, data.getString(Constant.DOB));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginWithPassword(String password, Activity activity) {
        session = new Session(activity);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONObject data = jsonObject.getJSONObject(Constant.DATA);
                        setStudentData(data, activity);
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

