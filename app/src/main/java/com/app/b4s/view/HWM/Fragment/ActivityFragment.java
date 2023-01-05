package com.app.b4s.view.HWM.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.adapter.HomeWorkSubjectAdapter;
import com.app.b4s.adapter.ViewPagerActivityAdapter;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.Subject;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class ActivityFragment extends Fragment {
    RecyclerView rvSubject;
    HomeWorkSubjectAdapter homeWorkSubjectAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerActivityAdapter viewPagerActivityAdapter;
    TextView tvSortby,tvFilter;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;



    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_activity, container, false);


        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tabs);
        viewPagerActivityAdapter = new ViewPagerActivityAdapter(getParentFragmentManager());
        viewPager.setAdapter(viewPagerActivityAdapter);
        tabLayout.setupWithViewPager(viewPager);



        rvSubject = root.findViewById(R.id.rvSubject);
        tvSortby = root.findViewById(R.id.tvSortby);
        tvFilter = root.findViewById(R.id.tvFilter);
        linearLayout1 = root.findViewById(R.id.linearLayout1);









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

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFilter.setVisibility(View.INVISIBLE);



                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.filter_popup,null);



                TextView tvFilterclose = customView.findViewById(R.id.tvFilterclose);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener()
                {

                    public boolean onTouch(View v, MotionEvent event)
                    {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                        {
                            popupWindow.dismiss();
                            tvFilter.setVisibility(View.VISIBLE);

                            return true;
                        }


                        return false;
                    }
                });





                //display the popup window
                popupWindow.showAsDropDown(tvFilter, -650, -100);




                //close the popup window on button click


                tvFilterclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        tvFilter.setVisibility(View.VISIBLE);
                    }
                });









            }

        });



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        rvSubject.setLayoutManager(linearLayoutManager);
        homework();




        return root;
    }

    private void homework() {

        ArrayList<HomeWorkSubject> homeWorkSubjects = new ArrayList<>();
        Subject subject = new Subject("1", "Hindi");

        HomeWorkSubject rings1 = new HomeWorkSubject("Kannada","Not Started","On review",subject);



        homeWorkSubjects.add(rings1);



        homeWorkSubjectAdapter = new HomeWorkSubjectAdapter(homeWorkSubjects, getActivity());
        rvSubject.setAdapter(homeWorkSubjectAdapter);
    }





}
