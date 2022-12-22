package com.app.b4s.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.b4s.view.CompletedFragment;
import com.app.b4s.view.OnReviewFragment;
import com.app.b4s.view.PendingFragment;
import com.app.b4s.view.PrereadCompletedFragment;
import com.app.b4s.view.PrereadPendingFragment;

public class ViewPagerPreReadAdapter
        extends FragmentPagerAdapter {

    public ViewPagerPreReadAdapter(
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
            fragment = new PrereadPendingFragment();
        else if (position == 1)
            fragment = new PrereadCompletedFragment();

        return fragment;
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Pending";
        else if (position == 1)
            title = "Compleded";
        return title;
    }
}