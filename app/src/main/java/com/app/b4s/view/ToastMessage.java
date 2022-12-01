package com.app.b4s.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;

public class ToastMessage {
    private Context context;

    public void show(Activity activity, String hi) {
        this.context = activity;
        LayoutInflater li = ((Activity) context).getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast, ((Activity) context).findViewById(R.id.custom_toast_layout));
        TextView text = layout.findViewById(R.id.custom_toast_message);
        text.setText(hi);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }
}
