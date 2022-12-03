package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetPasswordBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.RegisterSuccessfullActivity;
import com.app.b4s.view.Register.SetMPinActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class PasswordViewModel extends ViewModel {

    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    ActivitySetPasswordBinding binding;
    Activity activity;
    Session session;

    public MutableLiveData<User> getUser(ActivitySetPasswordBinding binding, Activity activity) {
        this.activity = activity;
        this.binding = binding;
        session = new Session(activity);

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onUsernameTextChanged(CharSequence text,int start) {
        if (start>=0){
            binding.llinfo.setVisibility(View.VISIBLE);
            // get the password when we start typing
            String password = text.toString();
            validatepass(password);
        }
        if (!text.toString().equals("")) {
            binding.btnProceed.setEnabled(true);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
        } else {
            // btnProceed.setEnabled(false);
            binding.btnProceed.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
        }

    }
    public void onClick() {
        checkPassword();
    }
    public void showPassword(){
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
    public void back() {
        activity.onBackPressed();
    }
    private void checkPassword() {
        if (validatepass(binding.edSetPasswordId.getText().toString())) {
            if (binding.edConfirmPasswordId.getText().toString().equals(binding.edSetPasswordId.getText().toString())) {
                setPassword(binding.edConfirmPasswordId.getText().toString());
            } else {
                Toast.makeText(activity, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(activity, R.string.invalid_password, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("(.*[!@#$%^&*()\\-__+.]){1,}");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            binding.atoz.setTextColor(Color.BLACK);
            return false;
        } else {
            // if lowercase character is  present
            binding.atoz.setTextColor(activity.getResources().getColor(R.color.text_green));
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            binding.AtoZ.setTextColor(Color.BLACK);
            return false;
        } else {
            // if uppercase character is  present
            binding.AtoZ.setTextColor(activity.getResources().getColor(R.color.text_green));
        }


        // if Special character is not present
        if (!special.matcher(password).find()) {
            binding.specialChar.setTextColor(Color.BLACK);
            return false;
        } else {
            // if Special character is  present
            binding.specialChar.setTextColor(activity.getResources().getColor(R.color.text_green));
        }


        // if digit is not present
        if (!digit.matcher(password).find()) {
            binding.num.setTextColor(Color.BLACK);
            return false;
        } else {
            // if digit is present
            binding.num.setTextColor(activity.getResources().getColor(R.color.text_green));
        }
        // if password length is less than 8
        if (password.length() < 8) {
            binding.charcount.setTextColor(Color.BLACK);
            return false;
        } else {
            binding.charcount.setTextColor(activity.getResources().getColor(R.color.text_green));
        }
        binding.llinfo.setVisibility(View.GONE);
        return true;
    }

    private void setPassword(String password) {
        String flow = activity.getIntent().getStringExtra(Constant.FLOW);
        String uniqueID = session.getData(Constant.UNIQUE_ID);
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueID);
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        if (flow.equals(Constant.NORMAL)) {
                            Intent intent = new Intent(activity, SetMPinActivity.class);
                            intent.putExtra(Constant.UNIQUE_ID, uniqueID);
                            intent.putExtra(Constant.SKIP_FACE_ID, 0);
                            activity.startActivity(intent);
                        } else {
                            Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
                            intent.putExtra(Constant.UNIQUE_ID, uniqueID);
                            intent.putExtra(Constant.SKIP_FACE_ID, 0);
                            activity.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.RESET_PASSWORD, params, true, 2);
    }
}
