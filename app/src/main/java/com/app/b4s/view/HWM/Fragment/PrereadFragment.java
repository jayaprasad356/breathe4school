package com.app.b4s.view.HWM.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.app.b4s.adapter.CompletedPrereadAdapter;
import com.app.b4s.adapter.HomeWorkSubjectAdapter;
import com.app.b4s.adapter.PendingPrereadAdapter;
import com.app.b4s.databinding.FragmentPrereadBinding;
import com.app.b4s.model.CompletedPreread;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.PendingPreread;
import com.app.b4s.model.Subject;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class PrereadFragment extends Fragment {

    RecyclerView rvSubject;
    HomeWorkSubjectAdapter homeWorkSubjectAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tvSortby, tvFilter;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    Boolean pending = false, completed = false;
    PendingPrereadAdapter pendingPrereadAdapter;
    CompletedPrereadAdapter completedPrereadAdapter;

    FragmentPrereadBinding binding;


    public PrereadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrereadBinding.inflate(inflater, container, false);

        rvSubject = binding.rvSubject;
        tvSortby = binding.tvSortby;
        tvFilter = binding.tvFilter;
        linearLayout1 = binding.linearLayout1;

        setLayoutManagers();
        pending = true;

        completed = false;

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
        binding.tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = true;
                completed = false;
                binding.rvCompleted.setVisibility(View.GONE);
                binding.rvReview.setVisibility(View.GONE);
                binding.rvpending.setVisibility(View.VISIBLE);
                binding.viewCompleted.setBackgroundColor(0);
                binding.viewPending.setBackgroundColor(getActivity().getColor(R.color.primary));
                pending();
                binding.tvCompleted.setTypeface(null);
                binding.tvPending.setTypeface(null, Typeface.BOLD);
            }
        });

        binding.tvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = false;
                completed = true;
                binding.rvCompleted.setVisibility(View.VISIBLE);
                binding.rvReview.setVisibility(View.GONE);
                binding.rvpending.setVisibility(View.GONE);
                binding.viewCompleted.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.viewPending.setBackgroundColor(0);
                binding.tvCompleted.setTypeface(null, Typeface.BOLD);
                binding.tvPending.setTypeface(null);
                Completed();
//                filterHomeWorkController.getFilterHomeWork(Constant.COMPLETED, getActivity());
            }
        });

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFilter.setVisibility(View.INVISIBLE);

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.filter_popup, null);

                TextView tvFilterclose = customView.findViewById(R.id.tvFilterclose);

                //instantiate popup window
                popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchInterceptor(new View.OnTouchListener() {

                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        rvSubject.setLayoutManager(linearLayoutManager);

        homework();
        pending();

        return binding.getRoot();
    }

    private void setLayoutManagers() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        binding.rvpending.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 3);
        binding.rvCompleted.setLayoutManager(gridLayoutManager2);
    }

    private void homework() {

        ArrayList<HomeWorkSubject> homeWorkSubjects = new ArrayList<>();
        Subject subject = new Subject("1", "Hindi");
        HomeWorkSubject rings1 = new HomeWorkSubject("Kannada", "Not Started", "On review", subject);


        homeWorkSubjects.add(rings1);


        homeWorkSubjectAdapter = new HomeWorkSubjectAdapter(homeWorkSubjects, getActivity());
        rvSubject.setAdapter(homeWorkSubjectAdapter);
    }

    private void pending() {


        ArrayList<PendingPreread> pendingPrereads = new ArrayList<>();

        PendingPreread rings1 = new PendingPreread("Kannada", "Poem", "Today | 10:30 AM");
        PendingPreread rings2 = new PendingPreread("Kannada", "Poem", "Today | 10:30 AM");
        PendingPreread rings3 = new PendingPreread("Kannada", "Poem", "Today | 10:30 AM");


        pendingPrereads.add(rings1);
        pendingPrereads.add(rings2);
        pendingPrereads.add(rings3);


        pendingPrereadAdapter = new PendingPrereadAdapter(pendingPrereads, getActivity());
        binding.rvpending.setAdapter(pendingPrereadAdapter);

    }

    private void Completed() {


        ArrayList<CompletedPreread> completedPrereads = new ArrayList<>();

        CompletedPreread rings1 = new CompletedPreread("Kannada","Poem","Today | 10:30 AM");
        CompletedPreread rings2 = new CompletedPreread("Kannada","Poem","Today | 10:30 AM");
        CompletedPreread rings3 = new CompletedPreread("Kannada","Poem","Today | 10:30 AM");



        completedPrereads.add(rings1);
        completedPrereads.add(rings2);
        completedPrereads.add(rings3);



        completedPrereadAdapter = new CompletedPrereadAdapter(completedPrereads, getActivity());
        binding.rvCompleted.setAdapter(completedPrereadAdapter);
    }
}




