package com.app.b4s.controller;

import android.app.Activity;

public interface IRegisterController {
    void onRegister(String uniqueId, Activity activity);
    void onLogin(String uniqueId, Activity activity);
    void setMPinPassword(String mPin, Activity activity);
    void onRegisterUser(Activity activity);
    void generateTempMpin(Activity activity);
}
