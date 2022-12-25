package com.app.b4s.adapter.DcmAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.b4s.view.DCM.Fragment.AssementCompletedFragment;
import com.app.b4s.view.DCM.Fragment.AssessmentOnreviewFragment;
import com.app.b4s.view.DCM.Fragment.AssessmentPendingFragment;

public class AssessmentViewPagerAdapter
        extends FragmentPagerAdapter {

    public AssessmentViewPagerAdapter(
            @NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;
        if (position == 0)
            fragment = new AssessmentPendingFragment();

        else if (position == 1)
            fragment = new AssessmentOnreviewFragment();


        else if (position == 2)
            fragment = new AssementCompletedFragment();

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Pending";
        else if (position == 1)
            title = "On Review";
        else if (position == 2)
            title = "Compleded";

        return title;
    }
}