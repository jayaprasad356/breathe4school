package com.app.b4s.view.DCM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.os.Bundle;

import com.app.b4s.R;
import com.app.b4s.view.ActivityFragment;
import com.app.b4s.view.DCM.Fragments.SummaryFragment;
import com.app.b4s.view.DCM.Fragments.TodaySummeryDetailFragment;
import com.app.b4s.view.HomeWorkManagementActivity;
import com.app.b4s.view.HomeWorkManagementFragment;
import com.app.b4s.view.PrereadFragment;
import com.google.android.material.navigationrail.NavigationRailView;

public class DigitalClassManagementActivity extends AppCompatActivity {

    NavigationRailView navigationrail;
    FragmentManager fragmentManager;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_class_management);
        activity = DigitalClassManagementActivity.this;


        navigationrail = findViewById(R.id.navigationRailView);


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.rlContainer, new SummaryFragment()).commit();
        navigationrail.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.nav_summery:
                    fragmentManager.beginTransaction().replace(R.id.rlContainer, new TodaySummeryDetailFragment()).commit();
                    break;

            }

            return true;
        });

    }
}