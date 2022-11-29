package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.b4s.R;

public class LoginMPinActivity extends AppCompatActivity {

    ImageView ibBackBtn;
    Activity activity;
    TextView tvForgotPin,tvLogineithpassword;
    EditText edMPinId;
    Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_mpin);
        activity = LoginMPinActivity.this;


        ibBackBtn = findViewById(R.id.ibBackBtn);
        tvForgotPin = findViewById(R.id.tvForgotPin);
        tvLogineithpassword = findViewById(R.id.tvLogineithpassword);
        edMPinId = findViewById(R.id.edMPinId);
        btnProceed = findViewById(R.id.btnProceed);



        ibBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edMPinId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    btnProceed.setEnabled(true);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                }
                else {
                    btnProceed.setEnabled(false);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
                intent.putExtra("Title","Success!");
//                intent.putExtra(   "Descripition","You have successfully completed the registration");
                startActivity(intent);
            }
        });


        tvForgotPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ForgotMPinActivity.class);
                startActivity(intent);
            }
        });

        tvLogineithpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginPasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}