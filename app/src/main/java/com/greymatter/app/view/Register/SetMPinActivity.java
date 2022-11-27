package com.greymatter.app.view.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.greymatter.app.R;

public class SetMPinActivity extends AppCompatActivity {


    EditText edConfirmMPinId,edMPinId;
    Button btnContinue;
    boolean Pin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mpin);



        edMPinId = findViewById(R.id.edMPinId);
        edConfirmMPinId = findViewById(R.id.edConfirmMPinId);
        btnContinue = findViewById(R.id.btnContinue);


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

                    Pin = true;
//
//                    btnContinue.setEnabled(true);
//                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));




                }
                else {


                    Pin = true;

//                    btnContinue.setEnabled(false);
//                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });
        edConfirmMPinId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                if (Pin = true){

                    if (!editable.toString().equals("")){
                        btnContinue.setEnabled(true);
                        btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    }


                    else {
                        btnContinue.setEnabled(false);
                        btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    }



                }


                else {
                    btnContinue.setEnabled(false);
                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });


    }
}