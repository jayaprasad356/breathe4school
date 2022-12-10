package com.app.b4s.commons;

import com.app.b4s.model.DayOfLine;

import java.util.ArrayList;

public interface ResponseListener {
    void onSuccess(String type);
    void onFailure(String msg);
    void setPasswordSuccess();
    void OnSuccess(ArrayList<DayOfLine> arrayList );
}
