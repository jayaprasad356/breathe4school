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
import com.app.b4s.controller.FilterController;
import com.app.b4s.controller.IfilterController;
import com.app.b4s.databinding.FragmentHomeWorkManagementBinding;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.OnHomeWorkData;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.DCM.FilterListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeWorkManagementFragment extends Fragment implements FilterListener {
    RecyclerView rvSubject, rvpending, rvReview, rvCompleted;
    HomeWorkSubjectAdapter homeWorkSubjectAdapter;
    ViewPager viewPager;

    TextView tvSortby, tvFilter;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    IfilterController filterHomeWorkController;
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


        filterHomeWorkController = new FilterController(this);

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
        binding.tvToday.setVisibility(View.VISIBLE);
        binding.tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = true;
                review = false;
                completed = false;
                binding.tvToday.setVisibility(View.VISIBLE);
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
                binding.tvToday.setVisibility(View.GONE);
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
                binding.tvToday.setVisibility(View.GONE);
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

                tvSortby.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_arrows, 0);
                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        tvSortby.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
                        // Toast message on menu item clicked
                        Toast.makeText(getActivity(), "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu popupMenu) {
                        tvSortby.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
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
        String url;
        Gson g = new Gson();
        // url = Constant.FILTER_BY_STUDENT_ID + session.getData(Constant.STUDENT_ID)+"/"+"completed";
        url = Constant.HomeWork_Url + Constant.STUDENT_SUMMARY + "get/" + Constant.STUDENTID + session.getData(Constant.STUDENT_ID);



        Map<String, String> params = new HashMap<>();

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HomeWorkSubject group = g.fromJson(jsonObject1.toString(), HomeWorkSubject.class);
                            homeWorkSubjects.add(group);

                        }
                        homeWorkSubjectAdapter = new HomeWorkSubjectAdapter(homeWorkSubjects, getActivity());
                        rvSubject.setAdapter(homeWorkSubjectAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, getActivity(), url, params, true, 0);

    }


    @Override
    public void onSuccess(JSONArray jsonArray) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        // rvpending.setLayoutManager(gridLayoutManager);
        Gson g = new Gson();
        ArrayList<OnHomeWorkData> filterData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = null;

            try {
                JSONObject homeWorkObject = jsonArray.getJSONObject(i);
                jsonObject1 = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject1 != null) {
                OnHomeWorkData group = g.fromJson(jsonObject1.toString(), OnHomeWorkData.class);
                filterData.add(group);
            } else {
                break;
            }

            if (pending) {
                binding.tvToday.setText(String.format("Today (%d)", filterData.size()));
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
