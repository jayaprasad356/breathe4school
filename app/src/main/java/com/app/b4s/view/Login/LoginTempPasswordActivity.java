package com.app.b4s.view.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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
import com.app.b4s.databinding.ActivityLoginTempPasswordBinding;
import com.app.b4s.utilities.Constant;

public class LoginTempPasswordActivity extends AppCompatActivity {

    TextView tvLoginMPin;
    ImageButton ibBackBtn;
    Activity activity;
    RelativeLayout rlEnterTempCode, rlGenrateTempCode;

    ImageButton ibBackGenratecode;
    TextView tvForgotPin;
    EditText edMPinId;
    Button btnProceed;
    ActivityLoginTempPasswordBinding binding;
    String uniqueId, tempMpin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_temp_password);
        activity = LoginTempPasswordActivity.this;

        uniqueId = getIntent().getStringExtra(Constant.UNIQUE_ID);
        tempMpin = getIntent().getStringExtra(Constant.MPIN);
        ibBackBtn = binding.ibBackBtn;
        tvLoginMPin = binding.tvLoginMPin;
        rlEnterTempCode = binding.rlEnterTempCode;
        rlGenrateTempCode = binding.rlGenrateTempCode;
        ibBackGenratecode = binding.ibBackGenratecode;
        tvForgotPin = binding.tvForgotPin;
        edMPinId = binding.edMPinId;
        btnProceed = binding.btnProceed;
        binding.tvCode.setText(tempMpin);


        ibBackGenratecode.setOnClickListener(v -> {
            rlGenrateTempCode.setVisibility(View.VISIBLE);
            rlEnterTempCode.setVisibility(View.GONE);
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
                if (!editable.toString().equals("")) {
                    btnProceed.setEnabled(true);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {
                    btnProceed.setEnabled(false);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });


        btnProceed.setOnClickListener(v -> {
            Intent intent = new Intent(activity, LoginSuccessfullActivity.class);
            intent.putExtra(Constant.UNIQUE_ID, uniqueId);
            intent.putExtra(Constant.TITLE, Constant.SUCCESS);
//                intent.putExtra(   "Descripition","You have successfully completed the registration");
            startActivity(intent);
        });


        tvForgotPin.setOnClickListener(v -> {


            rlGenrateTempCode.setVisibility(View.VISIBLE);
            rlEnterTempCode.setVisibility(View.GONE);
        });

        ;


        ibBackBtn.setOnClickListener(v -> onBackPressed());
        tvLoginMPin.setOnClickListener(v -> {

            rlGenrateTempCode.setVisibility(View.GONE);
            rlEnterTempCode.setVisibility(View.VISIBLE);
        });

    }
}