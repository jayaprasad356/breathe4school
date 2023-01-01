package com.app.b4s;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class TestActivity extends AppCompatActivity {
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        session = new Session(TestActivity.this);
        
        sendAnswer();
    }

    private void sendAnswer() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonObject.put("student_id", "638dee5fc4d6787791bfa3a7");
            jsonObject.put("submitted_on", "23/12/2022");

            jsonObject2.put("question_id", "63a45306f3d7db2a2dc3beb3");
            jsonObject2.put("answer", "A");
            jsonArray.put(jsonObject2);
            jsonObject.put("answers", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //jsonObject2 is the payload to server here you can use JsonObjectRequest

        String url="http://143.244.132.170:3001/api/v1/homework/"+session.getData(Constant.HOMEWORD_ID)+"/studentResponse/create";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST,url, jsonObject, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("STUDENT_RESPONSE",response.toString());
                        Toast.makeText(TestActivity.this, ""+response, Toast.LENGTH_SHORT).show();


                        try {
                            //TODO: Handle your response here
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        System.out.print(response);

                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        String body= "";
                        //get status code here
                        String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //get response body and parse with appropriate encoding
                        if(error.networkResponse.data!=null) {
                            try {
                                body = new String(error.networkResponse.data,"UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(TestActivity.this, ""+body, Toast.LENGTH_SHORT).show();

                    }


                });

        ApiConfig.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}