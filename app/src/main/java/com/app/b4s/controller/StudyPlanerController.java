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
        ArrayList<String> dayData= new ArrayList<>();


                try {
            jsonObject.put(Constant.ACADEMIC_YEAR_ID, session.getData(Constant.ACADEMIC_YEAR_ID));
            jsonObject.put(Constant.SCHOOL_ID, session.getData(Constant.SCHOOL_ID));
            jsonObject.put(Constant.STANDARD_ID, session.getData(Constant.STANDARD_ID));
            jsonObject.put(Constant.SECTION_ID, session.getData(Constant.SECTION_ID));
            jsonObject.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
            jsonObject.put(Constant.TIME_TABLE_SESSION_ID, session.getData(Constant.TIME_TABLE_SESSION_ID));
            lecture.put(Constant.NAME, "StudyPlanerName");
            lecture.put(Constant.DESCRIPTIO, "exam");
            lecture.put(Constant.START_TIME, "1800");
            lecture.put(Constant.END_TIME, "2000");
            lecture.put(Constant.SUBJECT_ID, session.getData(Constant.SELECTED_SUBJECT_ID));
                    String input = String.valueOf(checkBoxData);
                    String[] elements = input.replace("[", "").replace("]", "").split(", "); // elements = ["Wednesday", "Friday"]

                    String[] inut = {"Wednesday", "Friday"};
                    System.out.println(inut);


                    String[] array = checkBoxData.toArray(new String[checkBoxData.size()]);
                    System.out.println(array);

//                    for (int i = 0; i < checkBoxData.size(); i++) {
//                        array[i] = checkBoxData.get(i);
//                    }

                    System.out.println(array);

                    JSONObject json = new JSONObject();

//                    for (String element : array) {
//                       // json.put("key", element);
//                        lecture.put(Constant.DAY, element);
//
//                    }

                    String[] days = {"Monday", "Tuesday"};

                    lecture.put(Constant.DAY,days);


            jsonArray.put(lecture);
            schedule.put(Constant.LECTURES, jsonArray);
            jsonObject.put(Constant.SCHEDULES, schedule);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://143.244.132.170:3001/api/v1/studyPlanner/create";











        //jsonObject2 is the payload to server here you can use JsonObjectRequest


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url, jsonObject, response -> {
                    Log.d("STUDENT_RESPONSE",response.toString());
                    Toast.makeText(activity, ""+response, Toast.LENGTH_SHORT).show();


                    try {
                        //TODO: Handle your response here
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.print(response);

                }, error -> {
                    // TODO: Handle error
                    String body= "";
                    //get status code here
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    //get response body and parse with appropriate encoding
                    if(error.networkResponse.data!=null) {
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Toast.makeText(activity, ""+body, Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }


                });

        ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
//
//        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, "http://143.244.132.170:3001/api/v1/studyPlanner/");
//        Call<ResponseBody> call = jsonPostService.postRawJSON(jsonObject);
//        call.enqueue(new Callback<ResponseBody>() {
//
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try{
//                    Log.e("response-success", response.body().toString());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.e("response-failure", call.toString());
//            }
//
//        });


//        if (ApiConfig.isConnected(activity)) {
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                    (Request.Method.POST, url, jsonObject, response -> {
//                        Log.d("TEST_RES", response.toString());
//                        try {
//                            if (response.getBoolean(Constant.STATUS)) {
//                                Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
//                            }
//                            Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        System.out.print(response);
//
//                    }, error -> {
//                        // TODO: Handle error
//                        error.printStackTrace();
//                        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
//                    });
//            //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
//        }
    }
}
