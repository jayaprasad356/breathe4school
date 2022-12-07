package com.app.b4s.commons;

public interface ResponseListener {
    void onSuccess(String type);
    void onFailure(String msg);
    void setPasswordSuccess();
}
