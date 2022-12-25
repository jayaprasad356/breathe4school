package com.app.b4s.view.DCM.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.app.b4s.R;
import com.app.b4s.view.DCM.Fragment.AssessmentDcmFragment;
import com.app.b4s.view.DCM.Fragment.LiveDcmFragment;
import com.app.b4s.view.DCM.Fragment.SummaryDcmFragment;
import com.app.b4s.view.HWM.Activity.HomeWorkManagementActivity;
import com.app.b4s.view.Home.Activity.HomeActivity;
import com.google.android.material.navigationrail.NavigationRailView;

public class DcmActivity extends AppCompatActivity {


    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    LinearLayout bn_Home,bn_DCM,bn_HWM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcm);



        navigationrail = findViewById(R.id.navigationRailView);



        bn_Home = findViewById(R.id.bn_Home);
        bn_DCM = findViewById(R.id.bn_DCM);
        bn_HWM = findViewById(R.id.bn_HWM);


        bn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DcmActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        bn_HWM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DcmActivity.this, HomeWorkManagementActivity.class);
                startActivity(intent);
            }
        });

        if (fragment == null ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlContainer,new SummaryDcmFragment());
            fragmentTransaction.commit();
        }

        navigationrail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.nav_summary:
                    fragment = new SummaryDcmFragment();
                    break;


                case R.id.nav_live:
                    fragment = new LiveDcmFragment();
                    break;

                case R.id.nav_assessment:
                    fragment = new AssessmentDcmFragment();
                    break;
            }
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlContainer,fragment);
            fragmentTransaction.commit();

            return true;
        });
    }
}