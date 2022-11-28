package com.app.b4s.viewmodels;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.b4s.model.User;


public class RegistrationViewModel extends ViewModel {

    public MutableLiveData<String> UniqueId = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void onClick() {

        User user = new User(UniqueId.getValue());

        userMutableLiveData.setValue(user);

    }

}