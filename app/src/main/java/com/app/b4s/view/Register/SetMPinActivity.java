package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.b4s.R;

public class SetMPinActivity extends AppCompatActivity {


    EditText edConfirmMPinId,edMPinId;
    Button btnContinue;
    boolean Pin = false;
    ImageButton ibBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_mpin);



        edMPinId = findViewById(R.id.edMPinId);
        edConfirmMPinId = findViewById(R.id.edConfirmMPinId);
        btnContinue = findViewById(R.id.btnContinue);
        ibBackBtn = findViewById(R.id.ibBackBtn);


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

                    Pin = true;
//
//                    btnContinue.setEnabled(true);
//                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));




                }
                else {


                    Pin = false;

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