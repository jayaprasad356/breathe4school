package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.databinding.ActivitySetPasswordBinding;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.PasswordViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.app.b4s.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SetPasswordActivity extends AppCompatActivity {

    EditText edSetPasswordId, edConfirmPasswordId;
    TextInputLayout edSetPasswordInp;
    Activity activity;
    TextView atoz, AtoZ, num, charcount, special_char;
    LinearLayout llinfo;
    ImageButton ibBackBtn;
    Button btnProceed;
    String uniqueID, flow;
    ActivitySetPasswordBinding binding;
    PasswordViewModel passwordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passwordViewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_password);

        activity = SetPasswordActivity.this;
        uniqueID = getIntent().getStringExtra(Constant.UNIQUE_ID);
        flow = getIntent().getStringExtra(Constant.FLOW);
        passwordViewModel.getUser().observe(this, user -> {
            checkPassword();
        });

        edSetPasswordId = binding.edSetPasswordId;
        edConfirmPasswordId = binding.edConfirmPasswordId;
        atoz = binding.atoz;
        AtoZ = binding.AtoZ;
        num = binding.num;
        charcount = binding.charcount;
        special_char = binding.specialChar;
        llinfo = binding.llinfo;
        edSetPasswordInp = binding.edSetPasswordInp;
        ibBackBtn = binding.ibBackBtn;
        btnProceed = binding.btnProceed;


        edSetPasswordInp.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llinfo.setVisibility(View.VISIBLE);
            }
        });


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
                llinfo.setVisibility(View.VISIBLE);
                // get the password when we start typing
                String password = edSetPasswordId.getText().toString();
                validatepass(password);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    btnProceed.setEnabled(true);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));


                } else {
                    // btnProceed.setEnabled(false);
                    btnProceed.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });


        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();

            }
        });

        binding.tvShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.tvShowPass.getText().equals("Show Password")) {


                    binding.tvShowPass.setText("Hide Password");
                    edSetPasswordId.setTransformationMethod(null);
                    edConfirmPasswordId.setTransformationMethod(null);


                } else if (binding.tvShowPass.getText().equals("Hide Password")) {


                    binding.tvShowPass.setText("Show Password");
                    edSetPasswordId.setTransformationMethod(new PasswordTransformationMethod());
                    edConfirmPasswordId.setTransformationMethod(new PasswordTransformationMethod());


                }


            }
        });


    }

    private void checkPassword() {
        if (validatepass(binding.edSetPasswordId.getText().toString())) {
            if (edConfirmPasswordId.getText().toString().equals(edSetPasswordId.getText().toString())) {
                setPassword(edConfirmPasswordId.getText().toString());
            } else {
                Toast.makeText(activity, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setPassword(String password) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueID);
        params.put(Constant.PASSWORD, password);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        if (flow.equals(Constant.NORMAL)){
                            Intent intent = new Intent(activity, SetMPinActivity.class);
                            intent.putExtra(Constant.UNIQUE_ID, uniqueID);
                            intent.putExtra(Constant.SKIP_FACE_ID, 0);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
                            intent.putExtra(Constant.UNIQUE_ID, uniqueID);
                            intent.putExtra(Constant.SKIP_FACE_ID, 0);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.RESET_PASSWORD, params, true, 2);
    }


    public boolean validatepass(String password) {

        // check for pattern
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern lowercase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("(.*[!@#$%^&*()\\-__+.]){1,}");

        // if lowercase character is not present
        if (!lowercase.matcher(password).find()) {
            atoz.setTextColor(Color.BLACK);
            return false;
        } else {
            // if lowercase character is  present
            atoz.setTextColor(getResources().getColor(R.color.text_green));
        }

        // if uppercase character is not present
        if (!uppercase.matcher(password).find()) {
            AtoZ.setTextColor(Color.BLACK);
            return false;
        } else {
            // if uppercase character is  present
            AtoZ.setTextColor(getResources().getColor(R.color.text_green));
        }


        // if Special character is not present
        if (!special.matcher(password).find()) {
            special_char.setTextColor(Color.BLACK);
            return false;
        } else {
            // if Special character is  present
            special_char.setTextColor(getResources().getColor(R.color.text_green));
        }


        // if digit is not present
        if (!digit.matcher(password).find()) {
            num.setTextColor(Color.BLACK);
            return false;
        } else {
            // if digit is present
            num.setTextColor(getResources().getColor(R.color.text_green));
        }
        // if password length is less than 8
        if (password.length() < 8) {
            charcount.setTextColor(Color.BLACK);
            return false;
        } else {
            charcount.setTextColor(getResources().getColor(R.color.text_green));
        }
        llinfo.setVisibility(View.GONE);
        return true;
    }
}