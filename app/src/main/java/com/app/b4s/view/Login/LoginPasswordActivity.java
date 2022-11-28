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
import android.widget.TextView;

import com.app.b4s.R;
import com.app.b4s.view.SuccessfullActivity;

public class LoginPasswordActivity extends AppCompatActivity {

    EditText edSetPasswordId;
    Button btnProceed;
    TextView tvForgotPasword;
    Activity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        activity = LoginPasswordActivity.this;


        edSetPasswordId = findViewById(R.id.edSetPasswordId);
        btnProceed = findViewById(R.id.btnProceed);
        tvForgotPasword = findViewById(R.id.tvForgotPasword);



        edSetPasswordId.addTextChangedListener(new TextWatcher() {
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
                Intent intent = new Intent(activity, SuccessfullActivity.class);
                startActivity(intent);
            }
        });


        tvForgotPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ForgotpasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}