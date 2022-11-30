package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityLoginPasswordBinding;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPasswordActivity extends AppCompatActivity {

    EditText edSetPasswordId;
    Button btnProceed;
    TextView tvForgotPasword;
    Activity activity;
    ActivityLoginPasswordBinding binding;
    String uniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_password);
        activity = LoginPasswordActivity.this;
        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);

        edSetPasswordId = binding.edSetPasswordId;
        btnProceed = binding.btnProceed;
        tvForgotPasword = binding.tvForgotPasword;


        edSetPasswordId.addTextChangedListener(new TextWatcher() {
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


        btnProceed.setOnClickListener(v -> loginWithPassword(uniqueId, edSetPasswordId.getText().toString()));

        tvForgotPasword.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ForgotpasswordActivity.class);
            intent.putExtra(Constant.UNIQUE_ID,uniqueId);
            startActivity(intent);
        });

    }

    private void loginWithPassword(String uniqueId, String password) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueId);
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID,uniqueId);
                        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
//                intent.putExtra(   "Descripition","You have successfully completed the registration");
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.LOGIN_WITH_PASSWORD, params, true, 1);

    }
}