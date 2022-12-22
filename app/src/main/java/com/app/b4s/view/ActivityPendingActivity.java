package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.b4s.R;

public class ActivityPendingActivity extends AppCompatActivity {


    Button btnSubmit;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        activity = ActivityPendingActivity.this;



        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout_activity, null);
                builder.setView(customLayout);

                Button btnOk = customLayout.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(activity,HomeWorkManagementActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog dialog
                        = builder.create();
                dialog.show();

            }
        });

    }
}