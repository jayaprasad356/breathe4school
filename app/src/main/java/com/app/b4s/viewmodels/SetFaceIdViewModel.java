package com.app.b4s.viewmodels;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.databinding.ActivitySetFaceIdBinding;
import com.app.b4s.model.User;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginFaceIDActivity;

public class SetFaceIdViewModel extends ViewModel {

    private MutableLiveData<User> userMutableLiveData;
    ActivitySetFaceIdBinding binding;
    Activity activity;
    boolean skip_FaceID_tv;
    Session session;

    public MutableLiveData<User> getUser(ActivitySetFaceIdBinding binding, Activity activity, Boolean skip_FaceID_tv) {
        this.binding = binding;
        this.activity = activity;
        this.skip_FaceID_tv = skip_FaceID_tv;
        session = new Session(activity);
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {


    }

    public void ivFace() {
        Intent intent = new Intent(activity, LoginFaceIDActivity.class);
        intent.putExtra(Constant.UNIQUE_ID, session.getData(Constant.UNIQUE_ID));
        activity.startActivity(intent);
    }

    public void ivBack() {
        activity.onBackPressed();
    }

    public void showVisibility() {
        if (skip_FaceID_tv == true) {

            binding.tvSkipFaceid.setVisibility(View.VISIBLE);

        } else {
            binding.tvSkipFaceid.setVisibility(View.GONE);

        }
    }
}
