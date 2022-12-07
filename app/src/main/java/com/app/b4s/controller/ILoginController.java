package com.app.b4s.controller;

import android.app.Activity;

public interface ILoginController {
    void onMPinLogin(String mpin, Activity activity);
    void loginWithPassword(String password, Activity activity);
}
