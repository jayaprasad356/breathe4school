package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.adapter.HomeWorkSubjectAdapter;
import com.app.b4s.adapter.ViewPagerAdapter;
import com.app.b4s.model.HomeWorkSubject;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class HomeWorkManagementFragment extends Fragment {
    RecyclerView rvSubject;
    HomeWorkSubjectAdapter homeWorkSubjectAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView tvSortby;


    public HomeWorkManagementFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_work_management, container, false);

        rvSubject = root.findViewById(R.id.rvSubject);
        tvSortby = root.findViewById(R.id.tvSortby);


        tvSortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(getActivity(), tvSortby);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        // Toast message on menu item clicked
                        Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        rvSubject.setLayoutManager(linearLayoutManager);
        homework();


        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tabs);

        viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);


        tabLayout.setupWithViewPager(viewPager);


        return root;
    }

    private void homework() {

        ArrayList<HomeWorkSubject> homeWorkSubjects = new ArrayList<>();

        HomeWorkSubject rings1 = new HomeWorkSubject("Kannada","Not Started","On review","Completed");



        homeWorkSubjects.add(rings1);



        homeWorkSubjectAdapter = new HomeWorkSubjectAdapter(homeWorkSubjects, getActivity());
        rvSubject.setAdapter(homeWorkSubjectAdapter);
    }
}