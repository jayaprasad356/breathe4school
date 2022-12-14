package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.b4s.R;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.IPasswordController;
import com.app.b4s.controller.PasswordController;
import com.app.b4s.databinding.ActivitySetPasswordBinding;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SetPasswordActivity extends AppCompatActivity implements ResponseListener {
    Activity activity;
    String uniqueID, flow;
    Session session;
    ActivitySetPasswordBinding binding;
    IPasswordController passwordController;

    boolean validPassword = false, length = false, digits = false, atoz = false, AtoZ = false, splChar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_password);
        activity = SetPasswordActivity.this;
        session = new Session(activity);
        passwordController = new PasswordController(this);
        uniqueID = getIntent().getStringExtra(Constant.UNIQUE_ID);
        flow = getIntent().getStringExtra(Constant.FLOW);
        binding.edSetPasswordInp.setEndIconOnClickListener(view -> setPasswordInfo());
        binding.btnProceed.setOnClickListener(view -> checkPassword());
        binding.ibBackBtn.setOnClickListener(view -> onBackPressed());
        binding.tvShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassword();
            }
        });
        binding.edSetPasswordId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.llinfo.setVisibility(View.VISIBLE);
                // get the password when we start typing
                String password = binding.edSetPasswordId.getText().toString();
                validatepass(password);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    binding.btnProceed.setEnabled(true);
                    binding.btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));


                } else {
                    // btnProceed.setEnabled(false);
                    binding.btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });

    }

    public void showPassword() {
        if (binding.tvShowPass.getText().equals(activity.getString(R.string.show_password))) {
            binding.tvShowPass.setText(R.string.hide_password);
            binding.edSetPasswordId.setTransformationMethod(null);
            binding.edConfirmPasswordId.setTransformationMethod(null);
        } else if (binding.tvShowPass.getText().equals(activity.getString(R.string.hide_password))) {
            binding.tvShowPass.setText(R.string.show_password);
            binding.edSetPasswordId.setTransformationMethod(new PasswordTransformationMethod());
            binding.edConfirmPasswordId.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    public void setPasswordInfo() {
        binding.llinfo.setVisibility(View.VISIBLE);
    }

    public void validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("(.*[!@#$%^&*()\\-__+.]){1,}");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            binding.atoz.setTextColor(Color.BLACK);
            atoz = false;
        } else {
            // if lowercase character is  present
            binding.atoz.setTextColor(activity.getResources().getColor(R.color.text_green));
            atoz = true;
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ = false;
            binding.AtoZ.setTextColor(Color.BLACK);
        } else {
            // if uppercase character is  present
            binding.AtoZ.setTextColor(activity.getResources().getColor(R.color.text_green));
            AtoZ = true;
        }


        // if Special character is not present
        if (!special.matcher(password).find()) {
            splChar = false;
            binding.specialChar.setTextColor(Color.BLACK);
        } else {
            // if Special character is  present
            binding.specialChar.setTextColor(activity.getResources().getColor(R.color.text_green));
            splChar = true;
        }


        // if digit is not present
        if (!digit.matcher(password).find()) {
            digits = false;
            binding.num.setTextColor(Color.BLACK);
        } else {
            // if digit is present
            binding.num.setTextColor(activity.getResources().getColor(R.color.text_green));
            digits = true;
        }
        // if password length is less than 8
        if (password.length() < 8) {
            binding.charcount.setTextColor(Color.BLACK);
            length = false;
        } else {
            binding.charcount.setTextColor(activity.getResources().getColor(R.color.text_green));
            length = true;
        }
        if (length && digits && splChar && AtoZ && atoz) {
            validPassword = true;
            binding.llinfo.setVisibility(View.GONE);
        }
    }

    private void checkPassword() {
        if (validPassword) {
            if (binding.edConfirmPasswordId.getText().toString().equals(binding.edSetPasswordId.getText().toString())) {
                if (flow.equals(Constant.NORMAL))
                    registerPassword(binding.edConfirmPasswordId.getText().toString());
                else
                    passwordController.setPassword(binding.edConfirmPasswordId.getText().toString(), activity);
            } else {
                Toast.makeText(activity, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, R.string.invalid_password, Toast.LENGTH_SHORT).show();
        }
    }

    private void registerPassword(String password) {
        session.setData(Constant.PASSWORD, password);
        Intent intent = new Intent(activity, SetFaceIdActivity.class);
        intent.putExtra(Constant.FLOW, Constant.FORGOT);
        intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }

    @Override
    public void onSuccess(String type) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void setPasswordSuccess() {
        Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
        intent.putExtra(Constant.UNIQUE_ID, uniqueID);
        intent.putExtra(Constant.SKIP_FACE_ID, 0);
        activity.startActivity(intent);
    }

    @Override
    public void OnSuccess(ArrayList<DayOfLine> arrayList) {

    }
}