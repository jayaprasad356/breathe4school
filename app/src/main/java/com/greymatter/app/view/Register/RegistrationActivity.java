package com.greymatter.app.view.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.greymatter.app.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText edUniqueId,edEmailId;
    Button btnContinue;
    LinearLayout llInputFeild;
    RelativeLayout rlUniqueInp;
    TextView tvVerifyEmail,tvInvalidID;
    Activity activity;
    TextInputLayout edUniqueInp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        activity = RegistrationActivity.this;


        edUniqueId = findViewById(R.id.edUniqueId);
        edEmailId = findViewById(R.id.edEmailId);
        btnContinue = findViewById(R.id.btnContinue);
        llInputFeild = findViewById(R.id.llInputFeild);
        rlUniqueInp = findViewById(R.id.rlUniqueInp);
        tvVerifyEmail = findViewById(R.id.tvVerifyEmail);
        tvInvalidID = findViewById(R.id.tvInvalidID);
        edUniqueInp = findViewById(R.id.edUniqueInp);

        edUniqueId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    btnContinue.setEnabled(true);
                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));




                }
                else {
                    btnContinue.setEnabled(false);
                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next();
            }
        });

        tvVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edEmailId.getText().toString().equals("")){

                    edEmailId.setError("Enter email Id");
                    edEmailId.requestFocus();



                }

                else {

                    Intent intent = new Intent(activity, OtpActivity.class);
                    startActivity(intent);
                }


            }
        });
    }

    private void next() {



        if (edUniqueId.getText().toString().equals("123456")){


            llInputFeild.setVisibility(View.VISIBLE);
            rlUniqueInp.setVisibility(View.GONE);


        }

        else{

            edUniqueInp.setBoxStrokeColor(getResources().getColor(R.color.red));

            tvInvalidID.setVisibility(View.VISIBLE);
            tvInvalidID.setText("Invalid Unique ID");


        }
    }
}