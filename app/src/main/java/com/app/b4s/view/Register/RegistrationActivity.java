package com.app.b4s.view.Register;

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
import com.app.b4s.controller.IRegisterController;
import com.app.b4s.controller.IRegisterView;
import com.app.b4s.controller.RegisterController;
import com.app.b4s.databinding.ActivityRegistrationBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginFaceIDActivity;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity implements IRegisterView {
    Activity activity;
    private ActivityRegistrationBinding binding;
    Session session;
    public boolean mobileVerify, emailverify;
    IRegisterController registerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = RegistrationActivity.this;
        session = new Session(activity);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        registerController = new RegisterController(this);
        binding.btnContinue.setOnClickListener(view -> registerController.onRegister(binding.edUniqueId.getText().toString(), activity));
        binding.tvVerifyEmail.setOnClickListener(View -> verifyEmail());
        binding.tvVerifyMobile.setOnClickListener(View -> verifyMobile());
        binding.tvAlreadyRegister.setOnClickListener(View -> loginSuccess());
        showActivity();
        binding.edUniqueId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    binding.btnContinue.setEnabled(true);
                    binding.tvAlreadyRegister.setVisibility(View.VISIBLE);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {
                    binding.btnContinue.setEnabled(false);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });
        binding.btnContinuePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPasswordActivity();
            }
        });

    }

    @Override
    public void onRegisterSuccess(String message) {
        session.setData(Constant.UNIQUE_ID, Objects.requireNonNull(binding.edUniqueId.getText()).toString());
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        showVerification();
    }


    @Override
    public void onRegisterError(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        loginSuccess();
    }

    private void loginSuccess() {
        session.setBoolean(Constant.IS_REGISTER, true);
        session.setData(Constant.UNIQUE_ID,binding.edUniqueId.getText().toString());
        Intent intent = new Intent(activity, LoginFaceIDActivity.class);
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }

    @Override
    public void onLoginFailure(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResetSuccess(String message) {

    }

    private void showVerification() {
        binding.tvAlreadyRegister.setVisibility(View.GONE);
        binding.llInputFeild.setVisibility(View.VISIBLE);
        binding.rlUniqueInp.setVisibility(View.GONE);
        binding.btnContinue.setVisibility(View.GONE);
    }

    public void verifyEmail() {
        if (binding.edEmailId.getText().toString().equals("")) {
            binding.edEmailId.setError(getString(R.string.enter_mail_id));
            binding.edEmailId.requestFocus();
        } else {
            session.setData(Constant.EMAIL, binding.edEmailId.getText().toString());
            showOtpActivity(binding.edEmailId.getText().toString(), Constant.EMAIL);
        }
    }

    public void verifyMobile() {
        if (binding.edMobilenoId.getText().toString().equals("")) {
            binding.edMobilenoId.setError(getString(R.string.enter_mobile_no));
            binding.edMobilenoId.requestFocus();
        } else {
            session.setData(Constant.MOBILE, binding.edMobilenoId.getText().toString());
            showOtpActivity(binding.edMobilenoId.getText().toString(), Constant.MOBILE);
        }

    }

    private void showOtpActivity(String dataText, String dataType) {
        Intent intent = new Intent(activity, OtpActivity.class);
        intent.putExtra(Constant.DESCRIPTION, dataText);
        intent.putExtra(Constant.TYPE, dataType);
        activity.startActivity(intent);
        activity.finish();
    }

    public void showActivity() {
        if (session.getBoolean(Constant.MOBILE_OTP_VERIFY)) {
            layoutVisibility();
            binding.edMobilenoId.setText(session.getData(Constant.MOBILE));
            mobileVerify = true;
            binding.tvVerifyMobile.setVisibility(View.GONE);
            binding.ivMobileTick.setVisibility(View.VISIBLE);
        }
        if (session.getBoolean(Constant.EMAIL_OTP_VERIFY)) {
            layoutVisibility();
            binding.edEmailId.setText(session.getData(Constant.EMAIL));
            emailverify = true;
            binding.tvVerifyEmail.setVisibility(View.GONE);
            binding.ivMailTick.setVisibility(View.VISIBLE);
        }
        if (emailverify || mobileVerify) {
            binding.btnContinuePass.setEnabled(true);
            binding.btnContinuePass.setVisibility(View.VISIBLE);
        }
    }

    private void layoutVisibility() {
        binding.llInputFeild.setVisibility(View.VISIBLE);
        binding.rlUniqueInp.setVisibility(View.GONE);
        binding.btnContinue.setVisibility(View.GONE);
    }
    public void showPasswordActivity() {
        if (mobileVerify) {
            String uniqueID = binding.edUniqueId.getText().toString();
            Intent intent = new Intent(activity, SetPasswordActivity.class);
            intent.putExtra(Constant.FLOW, Constant.NORMAL);
            intent.putExtra(Constant.UNIQUE_ID, uniqueID);
            activity.startActivity(intent);
        }
    }
}