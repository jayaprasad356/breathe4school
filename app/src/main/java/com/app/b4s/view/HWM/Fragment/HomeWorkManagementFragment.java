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
import com.app.b4s.adapter.HomeWorkAdapter;
import com.app.b4s.adapter.HomeWorkSubjectAdapter;
import com.app.b4s.adapter.ViewPagerAdapter;
import com.app.b4s.controller.FilterHomeWorkController;
import com.app.b4s.controller.IFilterHomeWorkController;
import com.app.b4s.databinding.FragmentHomeWorkManagementBinding;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.OnHomeWordData;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.DCM.FilterHomeWorkListener;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeWorkManagementFragment extends Fragment implements FilterHomeWorkListener {
    RecyclerView rvSubject, rvpending, rvReview, rvCompleted;
    HomeWorkSubjectAdapter homeWorkSubjectAdapter;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView tvSortby, tvFilter;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    IFilterHomeWorkController filterHomeWorkController;
    HomeWorkAdapter homeWorkAdapter;
    Boolean pending = false, review = false, completed = false;
    Session session;
    FragmentHomeWorkManagementBinding binding;


    public HomeWorkManagementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeWorkManagementBinding.inflate(inflater, container, false);


        filterHomeWorkController = new FilterHomeWorkController(this);

        session = new Session(getActivity());
        rvpending = binding.rvpending;
        rvReview = binding.rvReview;
        rvCompleted = binding.rvCompleted;


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        rvpending.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 3);
        rvReview.setLayoutManager(gridLayoutManager1);


        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 3);
        rvCompleted.setLayoutManager(gridLayoutManager2);
        pending = true;
        review = false;
        completed = false;
        filterHomeWorkController.getFilterHomeWork(Constant.PENDING, getActivity());
        binding.tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = true;
                review = false;
                completed = false;
                rvCompleted.setVisibility(View.GONE);
                rvReview.setVisibility(View.GONE);
                rvpending.setVisibility(View.VISIBLE);
                binding.viewCompleted.setBackgroundColor(0);
                binding.viewOnReview.setBackgroundColor(0);
                binding.viewPending.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.tvOnReview.setTypeface(null);
                binding.tvCompleted.setTypeface(null);
                binding.tvPending.setTypeface(null, Typeface.BOLD);
                filterHomeWorkController.getFilterHomeWork(Constant.PENDING, getActivity());
            }
        });


        binding.tvOnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = false;
                review = true;
                completed = false;
                rvCompleted.setVisibility(View.GONE);
                rvReview.setVisibility(View.VISIBLE);
                rvpending.setVisibility(View.GONE);
                binding.viewCompleted.setBackgroundColor(0);
                binding.viewOnReview.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.viewPending.setBackgroundColor(0);
                filterHomeWorkController.getFilterHomeWork(Constant.REVIEW, getActivity());
                binding.tvOnReview.setTypeface(null, Typeface.BOLD);
                binding.tvCompleted.setTypeface(null);
                binding.tvPending.setTypeface(null);
            }
        });
        binding.tvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = false;
                review = false;
                completed = true;
                rvCompleted.setVisibility(View.VISIBLE);
                rvReview.setVisibility(View.GONE);
                rvpending.setVisibility(View.GONE);
                binding.viewCompleted.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.viewOnReview.setBackgroundColor(0);
                binding.viewPending.setBackgroundColor(0);
                binding.tvOnReview.setTypeface(null);
                binding.tvCompleted.setTypeface(null, Typeface.BOLD);
                binding.tvPending.setTypeface(null);
                filterHomeWorkController.getFilterHomeWork(Constant.COMPLETED, getActivity());
            }
        });


        rvSubject = binding.rvSubject;
        tvSortby = binding.tvSortby;
        tvFilter = binding.tvFilter;
        linearLayout1 = binding.linearLayout1;

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


        return binding.getRoot();
    }

    private void homework() {

        ArrayList<HomeWorkSubject> homeWorkSubjects = new ArrayList<>();

        HomeWorkSubject rings1 = new HomeWorkSubject("Kannada", "Not Started", "On review", "Completed");


        homeWorkSubjects.add(rings1);


        homeWorkSubjectAdapter = new HomeWorkSubjectAdapter(homeWorkSubjects, getActivity());
        rvSubject.setAdapter(homeWorkSubjectAdapter);
    }


    @Override
    public void onSuccess(JSONArray jsonArray) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        // rvpending.setLayoutManager(gridLayoutManager);
        Gson g = new Gson();
        ArrayList<OnHomeWordData> filterData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = null;

            try {
                JSONObject homeWorkObject = jsonArray.getJSONObject(i);
                session.setData(Constant.HOMEWORD_ID, homeWorkObject.getString(Constant.ID));
                jsonObject1 = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject1 != null) {
                OnHomeWordData group = g.fromJson(jsonObject1.toString(), OnHomeWordData.class);
                filterData.add(group);
            } else {
                break;
            }

            if (pending) {
                homeWorkAdapter = new HomeWorkAdapter(Constant.PENDING, filterData, getActivity());
                rvpending.setAdapter(homeWorkAdapter);
            } else if (completed) {
                homeWorkAdapter = new HomeWorkAdapter(Constant.COMPLETED, filterData, getActivity());
                rvCompleted.setAdapter(homeWorkAdapter);
            } else if (review) {
                homeWorkAdapter = new HomeWorkAdapter(Constant.REVIEW, filterData, getActivity());
                rvReview.setAdapter(homeWorkAdapter);

            }

        }
    }

}
