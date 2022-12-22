package com.app.b4s.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.b4s.view.ActivityCompletedFragment;
import com.app.b4s.view.ActivityOnreviewFragment;
import com.app.b4s.view.ActivityPendingFragment;
import com.app.b4s.view.PrereadCompletedFragment;
import com.app.b4s.view.PrereadPendingFragment;

public class ViewPagerActivityAdapter
        extends FragmentPagerAdapter {

    public ViewPagerActivityAdapter(
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
            fragment = new ActivityPendingFragment();
        else if (position == 1)
            fragment = new ActivityOnreviewFragment();


        else if (position == 2)
            fragment = new ActivityCompletedFragment();

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