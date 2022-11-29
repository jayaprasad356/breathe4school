package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.app.b4s.databinding.ActivityRegistrationBinding;
import com.app.b4s.view.Login.LoginFaceIDActivity;
import com.app.b4s.viewmodels.RegistrationViewModel;
import com.app.b4s.R;

public class RegistrationActivity extends AppCompatActivity {
    Activity activity;
    private RegistrationViewModel registrationViewModel;
    private ActivityRegistrationBinding binding;
    boolean data = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = RegistrationActivity.this;
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, LoginFaceIDActivity.class);
                startActivity(intent);
            }
        });

        binding.setLifecycleOwner(this);
        binding.setViewModel(registrationViewModel);

        registrationViewModel.getUser().observe(this, user -> {

            binding.llInputFeild.setVisibility(View.VISIBLE);
            binding.rlUniqueInp.setVisibility(View.GONE);
            binding.btnContinue.setVisibility(View.GONE);
            binding.llLogintv.setVisibility(View.GONE);


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

        binding.edEmailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")){

                    data = true;

//                    binding.btnContinue.setEnabled(true);
//                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                }
                else {

                    data = false;
//                    binding.btnContinue.setEnabled(false);
//                    binding.btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });
        binding.edMobilenoId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                data = false;


                if ( data = true){

                    if (!editable.toString().equals("")){
                        binding.btnContinueFinish.setEnabled(true);
                        binding.btnContinueFinish.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    }


                    else {
                        binding.btnContinueFinish.setEnabled(false);
                        binding.btnContinueFinish.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    }



                }


                else {
                    binding.btnContinueFinish.setEnabled(false);
                    binding.btnContinueFinish.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

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




        binding.btnContinueFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, SetPasswordActivity.class);
                intent.putExtra("Title","Kudos!");
                intent.putExtra(   "Descripition","You have successfully completed the registration");
                startActivity(intent);
            }
        });


    }
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }


}