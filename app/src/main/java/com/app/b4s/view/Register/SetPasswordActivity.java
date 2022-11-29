package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.app.b4s.R;

import java.util.regex.Pattern;

public class SetPasswordActivity extends AppCompatActivity {

    EditText edSetPasswordId,edConfirmPasswordId;
    TextInputLayout edSetPasswordInp;
    Activity activity;
    TextView atoz, AtoZ, num, charcount,special_char;
    LinearLayout llinfo;
    ImageButton ibBackBtn;
    Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        activity = SetPasswordActivity.this;


        edSetPasswordId = findViewById(R.id.edSetPasswordId);
        edConfirmPasswordId = findViewById(R.id.edConfirmPasswordId);
        atoz = findViewById(R.id.atoz);
        AtoZ = findViewById(R.id.AtoZ);
        num = findViewById(R.id.num);
        charcount = findViewById(R.id.charcount);
        special_char = findViewById(R.id.special_char);
        llinfo = findViewById(R.id.llinfo);
        edSetPasswordInp = findViewById(R.id.edSetPasswordInp);
        ibBackBtn = findViewById(R.id.ibBackBtn);
        btnProceed = findViewById(R.id.btnProceed);


        ibBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        edSetPasswordInp.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llinfo.setVisibility(View.VISIBLE);
            }
        });


        edSetPasswordId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // get the password when we start typing
                String password = edSetPasswordId.getText().toString();
                validatepass(password);

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
                Intent intent = new Intent(activity,SetFaceIdActivity.class);
                startActivity(intent);
            }
        });






    }


    public void validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[$&+,:;=?@#|'<>.-^*()%!]");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.RED);
        } else {
            // if lowercase character is  present
            atoz.setTextColor(getResources().getColor(R.color.text_green));
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ.setTextColor(Color.RED);
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(getResources().getColor(R.color.text_green));
        }


        // if Special character is not present
        if (!special.matcher(password).find()) {
            special_char.setTextColor(Color.RED);
        } else {
            // if Special character is  present
            special_char.setTextColor(getResources().getColor(R.color.text_green));
        }


        // if digit is not present
        if (!digit.matcher(password).find()) {
            num.setTextColor(Color.RED);
        } else {
            // if digit is present
            num.setTextColor(getResources().getColor(R.color.text_green));
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.RED);
        } else {
            charcount.setTextColor(getResources().getColor(R.color.text_green));
        }
    }
}