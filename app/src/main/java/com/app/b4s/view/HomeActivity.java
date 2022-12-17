package com.app.b4s.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.b4s.R;
import com.google.android.material.navigationrail.NavigationRailView;

public class HomeActivity extends AppCompatActivity {

    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationrail = findViewById(R.id.navigationRailView);

        if (fragment == null ){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.rlContainer,new MyFeedFragment());
            fragmentTransaction.commit();
        }

        navigationrail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.nav_myfeed:
                    fragment = new MyFeedFragment();
                    break;

                case R.id.nav_calendar:
                    fragment = new CalendarFragment();
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