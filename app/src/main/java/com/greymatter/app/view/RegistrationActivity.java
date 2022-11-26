package com.greymatter.app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.greymatter.app.R;

public class RegistrationActivity extends AppCompatActivity {
    EditText edUniqueId;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edUniqueId = findViewById(R.id.edUniqueId);
        btnContinue = findViewById(R.id.btnContinue);

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
    }
}