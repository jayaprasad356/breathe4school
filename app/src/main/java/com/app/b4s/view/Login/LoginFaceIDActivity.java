package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginFaceIdactivityBinding;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFaceIDActivity extends AppCompatActivity {


    Button btnPin, btnPassword;
    TextView tvForgotPin_password, tvLoginWTempPassword;
    Activity activity;
    LinearLayout llFaceidNotMatch, llFaceId;
    ActivityLoginFaceIdactivityBinding binding;
    String uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_face_idactivity);
        activity = LoginFaceIDActivity.this;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);

        btnPin = binding.btnPin;
        btnPassword = binding.btnPassword;
        tvForgotPin_password = binding.tvForgotPinPassword;
        tvLoginWTempPassword = binding.tvLoginWTempPassword;
        llFaceidNotMatch = binding.llFaceidNotMatch;
        llFaceId = binding.llFaceId;


        llFaceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                llFaceId.setVisibility(View.GONE);
                llFaceidNotMatch.setVisibility(View.VISIBLE);

            }
        });
        tvLoginWTempPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTempMPin(uniqueId);

            }
        });
        tvForgotPin_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTempMPin(uniqueId);
            }
        });


        btnPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginMPinActivity.class);
                intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                startActivity(intent);
            }
        });
        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginPasswordActivity.class);
                intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                startActivity(intent);
            }
        });


    }

    private void generateTempMPin(String uniqueId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginTempPasswordActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginTempPasswordActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                        intent.putExtra(Constant.MPIN,"15445");
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.GENERATE_TEMP_MPIN, params, true, 1);
    }
}