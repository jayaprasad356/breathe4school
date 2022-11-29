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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.b4s.R;

public class LoginTempPasswordActivity extends AppCompatActivity {

    TextView tvLoginMPin;
    ImageButton ibBackBtn;
    Activity activity ;
    RelativeLayout rlEnterTempCode,rlGenrateTempCode;

    ImageButton ibBackGenratecode;
    TextView tvForgotPin;
    EditText edMPinId;
    Button btnProceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp_password);
        activity = LoginTempPasswordActivity.this;


        ibBackBtn = findViewById(R.id.ibBackBtn);
        tvLoginMPin = findViewById(R.id.tvLoginMPin);
        rlEnterTempCode = findViewById(R.id.rlEnterTempCode);
        rlGenrateTempCode = findViewById(R.id.rlGenrateTempCode);
        ibBackGenratecode = findViewById(R.id.ibBackGenratecode);
        tvForgotPin = findViewById(R.id.tvForgotPin);
        edMPinId = findViewById(R.id.edMPinId);
        btnProceed = findViewById(R.id.btnProceed);



        ibBackGenratecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rlGenrateTempCode.setVisibility(View.VISIBLE);
                rlEnterTempCode.setVisibility(View.GONE);

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


                rlGenrateTempCode.setVisibility(View.VISIBLE);
                rlEnterTempCode.setVisibility(View.GONE);
            }
        });

   ;



        ibBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvLoginMPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rlGenrateTempCode.setVisibility(View.GONE);
                rlEnterTempCode.setVisibility(View.VISIBLE);
            }
        });

    }
}