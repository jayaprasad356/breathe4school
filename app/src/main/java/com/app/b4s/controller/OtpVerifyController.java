package com.app.b4s.controller;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.b4s.R;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

public class OtpVerifyController implements IOtpVerifyController {
    ResponseListener responseListener;
    String url, otpType;

    public OtpVerifyController(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public void OnOtpVerify(int otp, Activity activity, String type) {
        if (type.equals(Constant.EMAIL)) {
            url = Constant.VALIDATE_EMAIL_OTP;
            otpType = Constant.EMAIL_OTP;
        } else {
            url = Constant.VALIDATE_MOBILE_OTP;
            otpType = Constant.MOBILE_OTP;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(otpType, otp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (ApiConfig.isConnected(activity)) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TEST_RES", response.toString());
                            try {
                                if (response.getBoolean(Constant.STATUS)) {
                                    if (type.equals(Constant.EMAIL)) {
                                        Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                                        responseListener.onSuccess(type);
                                    } else if ((type.equals(Constant.MOBILE))) {
                                        Toast.makeText(activity, response.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                                        responseListener.onSuccess(type);
                                    }
                                } else {
                                    responseListener.onFailure(jsonObject.getString(Constant.MESSAGE));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.print(response);

                        }
                    }, new com.android.volley.Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            error.printStackTrace();
                            Toast.makeText(activity, activity.getString(R.string.invalid_otp), Toast.LENGTH_SHORT).show();
                        }


                    });
            ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
        }
    }
}
