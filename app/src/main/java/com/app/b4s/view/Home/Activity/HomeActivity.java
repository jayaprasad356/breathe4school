package com.app.b4s.view.Home.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.b4s.R;
import com.app.b4s.view.DCM.Activity.DcmActivity;
import com.app.b4s.view.HWM.Activity.HomeWorkManagementActivity;
import com.app.b4s.view.Home.Fragment.CalendarFragment;
import com.app.b4s.view.Home.Fragment.MyFeedFragment;
import com.google.android.material.navigationrail.NavigationRailView;

public class HomeActivity extends AppCompatActivity {

    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Fragment fragment = null;
    LinearLayout bn_Home,bn_DCM,bn_HWM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationrail = findViewById(R.id.navigationRailView);

        bn_Home = findViewById(R.id.bn_Home);
        bn_DCM = findViewById(R.id.bn_DCM);
        bn_HWM = findViewById(R.id.bn_HWM);


        bn_DCM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DcmActivity.class);
                startActivity(intent);
            }
        });
        bn_HWM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomeWorkManagementActivity.class);
                startActivity(intent);
            }
        });


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