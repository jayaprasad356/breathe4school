package com.app.b4s.view.HWM.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.app.b4s.R;
import com.app.b4s.view.DCM.Activity.DcmActivity;
import com.app.b4s.view.HWM.Fragment.ActivityFragment;
import com.app.b4s.view.HWM.Fragment.HomeWorkManagementFragment;
import com.app.b4s.view.HWM.Fragment.PrereadFragment;
import com.app.b4s.view.Home.Activity.HomeActivity;
import com.google.android.material.navigationrail.NavigationRailView;

public class HomeWorkManagementActivity extends AppCompatActivity {

    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    Activity activity;

    LinearLayout bn_Home,bn_DCM,bn_HWM;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_management);
        activity = HomeWorkManagementActivity.this;


        navigationrail = findViewById(R.id.navigationRailView);

        bn_Home = findViewById(R.id.bn_Home);
        bn_DCM = findViewById(R.id.bn_DCM);
        bn_HWM = findViewById(R.id.bn_HWM);


        bn_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, HomeActivity.class);
                startActivity(intent);
            }
        });
        bn_DCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(activity, DcmActivity.class);
//                startActivity(intent);
            }
        });

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.rlContainer, new HomeWorkManagementFragment()).commit();





        navigationrail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.nav_homework:
                    fragmentManager.beginTransaction().replace(R.id.rlContainer, new HomeWorkManagementFragment()).commit();
                    break;

                case R.id.nav_pre_read:
                    fragmentManager.beginTransaction().replace(R.id.rlContainer, new PrereadFragment()).commit();
                    break;


                case R.id.nav_activity:
                    fragmentManager.beginTransaction().replace(R.id.rlContainer, new ActivityFragment()).commit();
                    break;
            }

            return true;
        });
    }

    }
