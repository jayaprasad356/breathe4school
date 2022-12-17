package com.app.b4s.controller;

import org.json.JSONArray;

public interface CalendarResponse {
    void onSuccess(JSONArray jsonArray);
    void onFailure(String msg);
}
