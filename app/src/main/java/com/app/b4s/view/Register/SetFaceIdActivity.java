package com.app.b4s.view.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetFaceIdBinding;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginFaceIDActivity;

import java.util.concurrent.Executor;


public class SetFaceIdActivity extends AppCompatActivity {

    ImageButton ibBackBtn;
    TextView tvSkipFaceid;
    Boolean Skip_FaceID_tv = false;
    ActivitySetFaceIdBinding binding;
    Activity activity;
    String flow;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_face_id);
        activity = SetFaceIdActivity.this;
        ibBackBtn = binding.ibBackBtn;
        tvSkipFaceid = binding.tvSkipFaceid;
        flow = getIntent().getStringExtra(Constant.FLOW);
        Skip_FaceID_tv = getIntent().getExtras().getBoolean(Constant.SKIP_FACE_ID);
        showVisibility();

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(SetFaceIdActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == 11) {
                    Toast.makeText(activity, errString, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, SetMPinActivity.class);
                    intent.putExtra(Constant.FLOW, Constant.NORMAL);
                    activity.startActivity(intent);
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, SetMPinActivity.class);
                intent.putExtra(Constant.FLOW, Constant.NORMAL);
                activity.startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("bio auth")
                .setSubtitle("face or finger")
                .setNegativeButtonText("cancel")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                .build();
        if (flow.equals(Constant.FORGOT)) {
            binding.ivFaceLogo.setOnClickListener(view -> {

                biometricPrompt.authenticate(promptInfo);

//                Intent intent = new Intent(activity, SetMPinActivity.class);
//                intent.putExtra(Constant.FLOW, Constant.NORMAL);
//                activity.startActivity(intent);
            });
        } else {
            Intent intent = new Intent(activity, LoginFaceIDActivity.class);
            intent.putExtra(Constant.SKIP_FACE_ID, 0);
            activity.startActivity(intent);
        }
        binding.tvSkipFaceid.setOnClickListener(view ->{
            Intent intent = new Intent(activity, SetMPinActivity.class);
            intent.putExtra(Constant.FLOW, Constant.NORMAL);
            activity.startActivity(intent);
        });
    }

    public void showVisibility() {
        if (Skip_FaceID_tv == true) {
            binding.tvSkipFaceid.setVisibility(View.VISIBLE);
        } else {
            binding.tvSkipFaceid.setVisibility(View.GONE);
        }
    }
}