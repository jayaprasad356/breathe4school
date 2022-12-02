package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginMpinBinding;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.LoginMPinViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginMPinActivity extends AppCompatActivity {

    ImageView ibBackBtn;
    Activity activity;
    TextView tvForgotPin, tvLogineithpassword;
    EditText edMPinId;
    Button btnProceed;
    ActivityLoginMpinBinding binding;
    String uniqueId;
    LoginMPinViewModel loginMPinViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginMPinViewModel = ViewModelProviders.of(this).get(LoginMPinViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_mpin);
        binding.setLifecycleOwner(this);
        binding.setViewModel(loginMPinViewModel);
        activity = LoginMPinActivity.this;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        ibBackBtn = binding.ibBackBtn;
        tvForgotPin = binding.tvForgotPin;
        tvLogineithpassword = binding.tvLogineithpassword;
        edMPinId = binding.edMPinId;
        btnProceed = binding.btnProceed;

        ibBackBtn.setOnClickListener(v -> onBackPressed());

        edMPinId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    btnProceed.setEnabled(true);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });


        btnProceed.setOnClickListener(v -> loginWithMPin(uniqueId, edMPinId.getText().toString()));


        tvForgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ForgotMPinActivity.class);
                intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                startActivity(intent);
            }
        });

        tvLogineithpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginPasswordActivity.class);
                intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                startActivity(intent);
            }
        });

    }

    private void loginWithMPin(String uniqueId, String mPin) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        params.put(Constant.MPIN, mPin);

        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
                        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
                        intent.putExtra(Constant.UNIQUE_ID,uniqueId);
//                intent.putExtra(   "Descripition","You have successfully completed the registration");
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.LOGIN_WITH_MPIN, params, true, 1);
    }
}