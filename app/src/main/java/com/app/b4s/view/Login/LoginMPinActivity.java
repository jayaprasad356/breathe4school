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
import com.app.b4s.databinding.ActivityLoginMpinBinding;
import com.app.b4s.utilities.Constant;

public class LoginMPinActivity extends AppCompatActivity implements ResponseListener {
    Activity activity;
    ActivityLoginMpinBinding binding;
    String uniqueId;
    ILoginController loginController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_mpin);
        activity = LoginMPinActivity.this;
        loginController = new LoginController(this);
        binding.btnProceed.setOnClickListener(view -> loginController.onMPinLogin(binding.edMPinId.getText().toString(), activity));
        binding.tvLogineithpassword.setOnClickListener(view -> loginInsteadOfPassword());
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
                    binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
                } else {
                    binding.btnProceed.setEnabled(false);
                    binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

                }
            }
        });
        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());
        binding.tvForgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ForgotMPinActivity.class);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public void onSuccess(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
        intent.putExtra(Constant.UNIQUE_ID, uniqueId);
        activity.startActivity(intent);
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPasswordSuccess() {

    }
    public void loginInsteadOfPassword(){
        Intent intent = new Intent(activity, LoginPasswordActivity.class);
        activity.startActivity(intent);
    }
}