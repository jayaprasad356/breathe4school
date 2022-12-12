package com.app.b4s.view.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.ILoginController;
import com.app.b4s.controller.LoginController;
import com.app.b4s.databinding.ActivityLoginTempPasswordBinding;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;

import java.util.ArrayList;

public class LoginTempPasswordActivity extends AppCompatActivity implements ResponseListener {

    Activity activity;
    ActivityLoginTempPasswordBinding binding;
    Session session;
    ILoginController loginController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_temp_password);
        activity = LoginTempPasswordActivity.this;
        session = new Session(activity);
        loginController = new LoginController(this);
        binding.tvCode.setText(session.getData(Constant.TEMP_PASS));
        binding.tvLoginMPin.setOnClickListener(view -> {
            binding.rlGenrateTempCode.setVisibility(View.GONE);
            binding.rlEnterTempCode.setVisibility(View.VISIBLE);
        });
        binding.ibBackGenratecode.setOnClickListener(view -> {
            binding.rlGenrateTempCode.setVisibility(View.VISIBLE);
            binding.rlEnterTempCode.setVisibility(View.GONE);
        });
        binding.tvForgotPin.setOnClickListener(view -> {
            binding.rlGenrateTempCode.setVisibility(View.VISIBLE);
            binding.rlEnterTempCode.setVisibility(View.GONE);
        });
        binding.ibBackBtn.setOnClickListener(view -> backToLogin());
        binding.edMPinId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    binding.btnProceed.setEnabled(true);
                    binding.btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {
                    binding.btnProceed.setEnabled(false);
                    binding.btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.onMPinLogin(binding.edMPinId.getText().toString(), activity);
            }
        });

    }
    private void backToLogin() {
        activity.finish();
        Intent intent = new Intent(activity, LoginFaceIDActivity.class);
        intent.putExtra(Constant.BACK_FLOW,"1");
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }
    @Override
    public void onSuccess(String type) {
        activity.finish();
        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
        intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
        activity.startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPasswordSuccess() {

    }

    @Override
    public void OnSuccess(ArrayList<DayOfLine> arrayList) {

    }
}