package com.app.b4s.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.b4s.view.CompletedFragment;
import com.app.b4s.view.OnReviewFragment;
import com.app.b4s.view.PendingFragment;

public class ViewPagerAdapter
        extends FragmentPagerAdapter {

    public ViewPagerAdapter(
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
            fragment = new PendingFragment();
        else if (position == 1)
            fragment = new OnReviewFragment();
        else if (position == 2)
            fragment = new CompletedFragment();

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