package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetMpinBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Register.RegisterSuccessfullActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MpinViewModel extends ViewModel {

    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;
    ActivitySetMpinBinding binding;
    Activity activity;
    Session session;
    boolean Pin = false;
    String flow;

    public MutableLiveData<User> getUser(ActivitySetMpinBinding binding, Activity activity, String flow) {
        this.activity = activity;
        this.binding = binding;
        this.flow = flow;
        session = new Session(activity);

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {
        if (binding.edConfirmMPinId.getText().toString().equals(binding.edMPinId.getText().toString())) {
            if (flow.equals(Constant.FORGOT))
                setMPin(session.getData(Constant.UNIQUE_ID), binding.edConfirmMPinId.getText().toString());
            else
                registerUser(binding.edConfirmMPinId.getText().toString());
        } else
            Toast.makeText(activity, activity.getString(R.string.miss_match_mpin), Toast.LENGTH_SHORT).show();
    }

    private void registerUser(String mpin) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        params.put(Constant.MPIN, mpin);
        params.put(Constant.PASSWORD, session.getData(Constant.PASSWORD));
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        session.setBoolean(Constant.IS_REGISTER, true);
                        Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
                        intent.putExtra(Constant.SKIP_FACE_ID, 0);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.REGISTER_USER, params, true, 1);
    }

    private void setMPin(String uniqueID, String mpin) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueID);
        params.put(Constant.MPIN, mpin);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID, uniqueID);
                        intent.putExtra(Constant.SKIP_FACE_ID, 0);
                        activity.startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.RESET_MPIN, params, true, 2);
    }

    public void confirmTextChanged(CharSequence text, int start) {
        if (Pin = true) {

            if (!text.toString().equals("")) {
                binding.btnContinue.setEnabled(true);
                binding.btnContinue.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
            } else {
                binding.btnContinue.setEnabled(false);
                binding.btnContinue.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));
            }

        } else {
            binding.btnContinue.setEnabled(false);
            binding.btnContinue.setBackgroundTintList(activity.getResources().getColorStateList(R.color.btncolor));

        }

    }

    public void newMpinTextChanged(CharSequence text, int start) {
        if (!text.toString().equals("")) {
            Pin = true;
        } else {
            Pin = false;
        }
    }

    public void ivBack() {
        activity.onBackPressed();
    }
}
