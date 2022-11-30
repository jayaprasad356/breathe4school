package com.app.b4s.view.Register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.databinding.ActivitySetMpinBinding;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.Login.LoginSuccessfullActivity;
import com.app.b4s.viewmodels.MpinViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetMPinActivity extends AppCompatActivity {
    EditText edConfirmMPinId, edMPinId;
    Button btnContinue;
    boolean Pin = false;
    ImageButton ibBackBtn;
    ActivitySetMpinBinding binding;
    String uniqueID;
    MpinViewModel mpinViewModel;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_mpin);
        mpinViewModel = ViewModelProviders.of(this).get(MpinViewModel.class);
        activity = SetMPinActivity.this;
        edMPinId = binding.edMPinId;
        edConfirmMPinId = binding.edConfirmMPinId;
        btnContinue = binding.btnContinue;
        ibBackBtn = binding.ibBackBtn;
        Intent intent = getIntent();

        uniqueID = intent.getStringExtra(Constant.UNIQUE_ID);
        ibBackBtn.setOnClickListener(v -> onBackPressed());
        mpinViewModel.getUser().observe(this, user -> {
            Toast.makeText(activity, user.getUniqueId(), Toast.LENGTH_SHORT).show();
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
                    Pin = true;
//                    btnContinue.setEnabled(true);
//                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                } else {

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

                if (Pin = true) {

                    if (!editable.toString().equals("")) {
                        btnContinue.setEnabled(true);
                        btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    } else {
                        btnContinue.setEnabled(false);
                        btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));
                    }

                } else {
                    btnContinue.setEnabled(false);
                    btnContinue.setBackgroundTintList(getResources().getColorStateList(R.color.btncolor));

                }

            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edConfirmMPinId.getText().toString().equals(edMPinId.getText().toString()))
                    setMPin(uniqueID, edConfirmMPinId.getText().toString());
                else
                    Toast.makeText(SetMPinActivity.this, getString(R.string.miss_match_mpin), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setMPin(String uniqueID, String mpin) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.UNIQUE_ID, uniqueID);
        params.put(Constant.MPIN, mpin);
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        Intent intent = new Intent(activity, RegisterSuccessfullActivity.class);
                        intent.putExtra(Constant.UNIQUE_ID, uniqueID);
                        intent.putExtra(Constant.SKIP_FACE_ID, 0);
                        startActivity(intent);
                    } else {
                        Toast.makeText(activity, jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.RESET_MPIN, params, true, 2);
    }
}