package com.app.b4s.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.app.b4s.R;
import com.google.android.material.navigationrail.NavigationRailView;

public class HomeWorkManagementActivity extends AppCompatActivity {

    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_management);


        navigationrail = findViewById(R.id.navigationRailView);

        if (fragment == null ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlContainer,new HomeWorkManagementFragment());
            fragmentTransaction.commit();
        }

        navigationrail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.nav_homework:
                    fragment = new HomeWorkManagementFragment();
                    break;

                case R.id.nav_pre_read:
                    fragment = new PrereadFragment();
                    break;



                case R.id.nav_activity:
                    fragment = new ActivityFragment();
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
