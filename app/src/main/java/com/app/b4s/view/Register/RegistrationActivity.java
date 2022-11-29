package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivityRegistrationBinding;
import com.app.b4s.model.User;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.viewmodels.RegistrationViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    Activity activity;
    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;
    Boolean mobileVerify = false, emailverify = false;
    public String uniqueID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = RegistrationActivity.this;
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        binding.setLifecycleOwner(this);
        binding.setViewModel(registrationViewModel);
        binding.edUniqueId.getText();

        registrationViewModel.getUser().observe(this, user -> {
            if (mobileVerify && emailverify) {
                uniqueID=binding.edUniqueId.getText().toString();
                Intent intent = new Intent(activity, SetPasswordActivity.class);
                intent.putExtra(Constant.UNIQUE_ID,uniqueID);
                startActivity(intent);
            } else
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
                if (!editable.toString().equals("")) {
                    binding.btnContinue.setEnabled(true);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {
                    binding.btnContinue.setEnabled(false);
                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });

        binding.tvVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.edEmailId.getText().toString().equals("")) {
                    binding.edEmailId.setError(getString(R.string.enter_mail_id));
                    binding.edEmailId.requestFocus();
                } else {

                    Intent intent = new Intent(activity, OtpActivity.class);
                    intent.putExtra(Constant.TYPE, Constant.EMAIL);
                    startActivityForResult(intent, 2);
                }


            }
        });
        binding.tvVerifyMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.edMobilenoId.getText().toString().equals("")) {
                    binding.edMobilenoId.setError(getString(R.string.enter_mobile_no));
                    binding.edMobilenoId.requestFocus();
                } else {
                    Intent intent = new Intent(activity, OtpActivity.class);
                    intent.putExtra(Constant.TYPE, Constant.MOBILE);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }

    private void validateUniqueId() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, binding.edUniqueId.getText().toString().trim());
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                        binding.llInputFeild.setVisibility(View.VISIBLE);
                        binding.rlUniqueInp.setVisibility(View.GONE);


                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.VALIDATEUNIQUEID_URL, params, true, 1);


    }

    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mobileVerify = true;
                binding.tvVerifyMobile.setVisibility(View.GONE);
                binding.ivMobileTick.setVisibility(View.VISIBLE);
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                emailverify = true;
                binding.tvVerifyEmail.setVisibility(View.GONE);
                binding.ivMailTick.setVisibility(View.VISIBLE);
            }
        }
    }

}