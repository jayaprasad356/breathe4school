package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.b4s.R;

public class SubmissionHomeworkFormbasedActivity extends AppCompatActivity {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_homework_formbased);


        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SubmissionHomeworkFormbasedActivity.this);
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
                builder.setView(customLayout);

                Button btnOk = customLayout.findViewById(R.id.btnOk);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(SubmissionHomeworkFormbasedActivity.this,HomeWorkManagementActivity.class);
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