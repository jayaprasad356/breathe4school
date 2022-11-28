package com.app.b4s.view.Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.databinding.ActivityRegistrationBinding;
import com.app.b4s.model.User;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.RegistrationViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.app.b4s.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {
    Activity activity;
    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = RegistrationActivity.this;
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        binding.setLifecycleOwner(this);
        binding.setViewModel(registrationViewModel);

        registrationViewModel.getUser().observe(this, user -> {
            validateUniqueId();




        });

        binding.edUniqueId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){
                    binding.btnContinue.setEnabled(true);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                }
                else {
                    binding.btnContinue.setEnabled(false);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });

        binding.tvVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edEmailId.getText().toString().equals("")){

                    binding.edEmailId.setError("Enter email Id");
                    binding.edEmailId.requestFocus();
                }

                else {

                    Intent intent = new Intent(activity, OtpActivity.class);
                    startActivity(intent);
                }


            }
        });
    }

    private void validateUniqueId()
    {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID,binding.edUniqueId.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        binding.llInputFeild.setVisibility(View.VISIBLE);
                        binding.rlUniqueInp.setVisibility(View.GONE);


                    }
                    else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.VALIDATEUNIQUEID_URL, params, true,1);




    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }


}