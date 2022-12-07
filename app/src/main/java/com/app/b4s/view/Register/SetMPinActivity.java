package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.controller.IRegisterController;
import com.app.b4s.controller.IRegisterView;
import com.app.b4s.controller.RegisterController;
import com.app.b4s.databinding.ActivitySetMpinBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;

public class SetMPinActivity extends AppCompatActivity implements IRegisterView {
    ActivitySetMpinBinding binding;
    Session session;
    Activity activity;
    boolean Pin = false;
    IRegisterController registerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_mpin);
        activity = SetMPinActivity.this;
        session = new Session(activity);
        registerController = new RegisterController(this);
        String flow = getIntent().getStringExtra(Constant.FLOW);
        binding.btnContinue.setOnClickListener(view -> {
            if (binding.edConfirmMPinId.getText().toString().equals(binding.edMPinId.getText().toString())) {
                session.setData(Constant.MPIN, binding.edMPinId.getText().toString());
                if (flow.equals(Constant.FORGOT))
                    registerController.setMPinPassword(binding.edConfirmMPinId.getText().toString(), activity);
                else
                    registerController.onRegisterUser(activity);
            } else
                Toast.makeText(activity, activity.getString(R.string.miss_match_mpin), Toast.LENGTH_SHORT).show();
        });
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
                    Pin = true;
                } else {
                    Pin = true;
                }
            }
        });
        binding.edConfirmMPinId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (Pin = true) {
                    if (!editable.toString().equals("")) {
                        binding.btnContinue.setEnabled(true);
                        binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    } else {
                        binding.btnContinue.setEnabled(false);
                        binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    }
                } else {
                    binding.btnContinue.setEnabled(false);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                }
            }
        });
    }


    @Override
    public void onRegisterSuccess(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        session.setBoolean(Constant.IS_REGISTER, true);
        Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }

    @Override
    public void onRegisterError(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {

    }

    @Override
    public void onLoginFailure(String message) {

    }

    @Override
    public void onResetSuccess(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }
}