package com.app.b4s.controller;

public interface IRegisterView {
    void onRegisterSuccess(String message);
    void onRegisterError(String message);
    void onLoginSuccess();
    void onLoginFailure(String message);
    void onResetSuccess(String message);

}
