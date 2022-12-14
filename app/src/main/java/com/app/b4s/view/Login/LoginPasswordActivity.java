package com.app.b4s.view.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.ILoginController;
import com.app.b4s.controller.LoginController;
import com.app.b4s.databinding.ActivityLoginPasswordBinding;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;

import java.util.ArrayList;

public class LoginPasswordActivity extends AppCompatActivity implements ResponseListener {
    Activity activity;
    ActivityLoginPasswordBinding binding;
    ILoginController loginController;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_password);
        activity = LoginPasswordActivity.this;
        session = new Session(activity);
        loginController = new LoginController(this);
        binding.btnProceed.setOnClickListener(view -> loginController.loginWithPassword(binding.edSetPasswordId.getText().toString(), activity));
        binding.ibBackBtn.setOnClickListener(view -> backToLogin());
        binding.tvLogineithMpin.setOnClickListener(view -> loginInsteadOfMPin());
        binding.edSetPasswordId.addTextChangedListener(new TextWatcher() {
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
        binding.tvForgotPasword.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ForgotpasswordActivity.class);
            intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
            activity.startActivity(intent);
        });
    }

    private void backToLogin() {
        activity.finish();
        Intent intent = new Intent(activity, LoginFaceIDActivity.class);
        intent.putExtra(Constant.BACK_FLOW, "1");
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }

    public void loginInsteadOfMPin() {
        Intent intent = new Intent(activity, LoginMPinActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onSuccess(String message) {
        activity.finish();
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
        intent.putExtra(Constant.TITLE, Constant.SUCCESS);
        activity.startActivity(intent);

    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPasswordSuccess() {

    }

    @Override
    public void OnSuccess(ArrayList<DayOfLine> arrayList) {

    }
}