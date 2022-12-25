package com.app.b4s.view.DCM.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.DcmAdapter.AssessmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class AssessmentDcmFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;
    AssessmentViewPagerAdapter assessmentViewPagerAdapter;
    Activity activity;



    public AssessmentDcmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_assessment_dcm, container, false);
        activity = getActivity();



        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tabs);

        assessmentViewPagerAdapter = new AssessmentViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(assessmentViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        return root;

    }
}