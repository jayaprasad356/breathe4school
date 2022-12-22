package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.b4s.R;
import com.google.android.material.navigationrail.NavigationRailView;

public class HomeWorkManagementActivity extends AppCompatActivity {

    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    Activity activity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_management);
        activity = HomeWorkManagementActivity.this;


        navigationrail = findViewById(R.id.navigationRailView);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.rlContainer, new PrereadFragment()).commit();





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
