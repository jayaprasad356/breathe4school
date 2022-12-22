package com.app.b4s.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.b4s.utilities.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Session {
    public static final String PREFER_NAME = "breathe4school";
    final int PRIVATE_MODE = 0;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _activity;

    public Session(Context activity) {
        try {
            this._activity = activity;
            pref = _activity.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
            editor = pref.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String id, String val) {
        editor.putString(id, val);
        editor.commit();
    }

    public void setBoolean(String id, boolean val) {
        editor.putBoolean(id, val);
        editor.commit();
    }

    public String getData(String id) {
        return pref.getString(id, "");
    }

    public int getInt(String id) {
        return pref.getInt(id, 0);
    }

    public void setInt(String id, Integer val) {
        editor.putInt(id, val);
        editor.commit();
    }

    public boolean getBoolean(String id) {
        return pref.getBoolean(id, false);
    }

    public void setArrayList(String key, ArrayList list) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constant.SH_PRF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        Set<String> subject = new HashSet<>();
        subject.addAll(list);
        edit.putStringSet(key, subject);
        edit.commit();
    }

    public List<String> getArrayList(String key) {
        SharedPreferences prefs = _activity.getSharedPreferences(Constant.SH_PRF_KEY, Context.MODE_PRIVATE);
        Set<String> list = prefs.getStringSet(key, null);
        List<String> subjects = new ArrayList<>(list);
        return subjects;
    }
}