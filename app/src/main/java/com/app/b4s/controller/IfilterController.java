package com.app.b4s.controller;

import androidx.fragment.app.FragmentActivity;

public interface IfilterController {
    void getFilterHomeWork(String type, FragmentActivity activity,String link);
    void getFilterPreRead(String type, FragmentActivity activity);
}
