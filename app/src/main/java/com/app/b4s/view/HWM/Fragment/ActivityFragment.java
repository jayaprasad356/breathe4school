package com.app.b4s.view.HWM.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.TimePickerDialog;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.adapter.CompletedActivityAdapter;
import com.app.b4s.adapter.HomeWorkSubjectAdapter;
import com.app.b4s.adapter.OnReviewActivityAdapter;
import com.app.b4s.adapter.PendingActivityAdapter;
import com.app.b4s.databinding.FragmentActivityBinding;
import com.app.b4s.model.CompletedActivitymodel;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.PendingActivity;
import com.app.b4s.model.Subject;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class ActivityFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    RecyclerView rvSubject;
    HomeWorkSubjectAdapter homeWorkSubjectAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tvSortby, tvFilter;
    OnReviewActivityAdapter onReviewActivityAdapter;
    CompletedActivityAdapter completedActivityAdapter;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
    Boolean pending = false, review = false, completed = false;
    FragmentActivityBinding binding;
    PendingActivityAdapter pendingActivityAdapter;
    TextView datePicker;
    private LayoutInflater layoutInflater;

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentActivityBinding.inflate(inflater, container, false);

        setLayoutManagers();
        pending();
        pending = true;
        review = false;
        completed = false;
        binding.tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = true;
                review = false;
                completed = false;
                binding.rvCompleted.setVisibility(View.GONE);
                binding.rvReview.setVisibility(View.GONE);
                binding.rvpending.setVisibility(View.VISIBLE);
                binding.viewCompleted.setBackgroundColor(0);
                binding.viewOnReview.setBackgroundColor(0);
                binding.viewPending.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.tvOnReview.setTypeface(null);
                binding.tvCompleted.setTypeface(null);
                binding.tvPending.setTypeface(null, Typeface.BOLD);
                pending();

            }
        });
        binding.tvOnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pending = false;
                review = true;
                completed = false;
                binding.rvCompleted.setVisibility(View.GONE);
                binding.rvReview.setVisibility(View.VISIBLE);
                binding.rvpending.setVisibility(View.GONE);
                binding.viewCompleted.setBackgroundColor(0);
                binding.viewOnReview.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.viewPending.setBackgroundColor(0);
                onreview();
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
                binding.rvCompleted.setVisibility(View.VISIBLE);
                binding.rvReview.setVisibility(View.GONE);
                binding.rvpending.setVisibility(View.GONE);
                binding.viewCompleted.setBackgroundColor(getActivity().getColor(R.color.primary));
                binding.viewOnReview.setBackgroundColor(0);
                binding.viewPending.setBackgroundColor(0);
                binding.tvOnReview.setTypeface(null);
                completed();
                binding.tvCompleted.setTypeface(null, Typeface.BOLD);
                binding.tvPending.setTypeface(null);

            }
        });
        rvSubject = binding.rvSubject;
        tvSortby = binding.tvSortby;
        tvFilter = binding.tvFilter;
        linearLayout1 = binding.linearLayout1;
        layoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        tvSortby.setOnClickListener(v -> {
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
        });

        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = layoutInflater.inflate(R.layout.filter_popup, null);
                popupWindow = new PopupWindow(
                        popupView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setContentView(popupView);
                TextView monthName= popupView.findViewById(R.id.time);
                monthName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "te", Toast.LENGTH_SHORT).show();
                    }
                });

                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                int location[] = new int[2];
                v.getLocationOnScreen(location);
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,location[0], location[1] + v.getHeight());

            }

        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvSubject.setLayoutManager(linearLayoutManager);
        homework();

        return binding.getRoot();
    }

    private void homework() {

        ArrayList<HomeWorkSubject> homeWorkSubjects = new ArrayList<>();
        Subject subject = new Subject("1", "Hindi");

        HomeWorkSubject rings1 = new HomeWorkSubject("Kannada", "Not Started", "On review", subject);


        homeWorkSubjects.add(rings1);


        homeWorkSubjectAdapter = new HomeWorkSubjectAdapter(homeWorkSubjects, getActivity());
        rvSubject.setAdapter(homeWorkSubjectAdapter);
    }

    private void setLayoutManagers() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        binding.rvpending.setLayoutManager(gridLayoutManager);

        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 3);
        binding.rvReview.setLayoutManager(gridLayoutManager1);


        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getActivity(), 3);
        binding.rvCompleted.setLayoutManager(gridLayoutManager2);
    }

    private void pending() {

        ArrayList<PendingActivity> pendingActivities = new ArrayList<>();

        PendingActivity rings1 = new PendingActivity("Kannada", "Poem", "Today | 10:30 AM");
        PendingActivity rings2 = new PendingActivity("Kannada", "Poem", "Today | 10:30 AM");
        PendingActivity rings3 = new PendingActivity("Kannada", "Poem", "Today | 10:30 AM");


        pendingActivities.add(rings1);
        pendingActivities.add(rings2);
        pendingActivities.add(rings3);


        pendingActivityAdapter = new PendingActivityAdapter(pendingActivities, getActivity());
        binding.rvpending.setAdapter(pendingActivityAdapter);
    }

    private void onreview() {


        ArrayList<com.app.b4s.model.OnReviewActivity> onReviewActivities = new ArrayList<>();

        com.app.b4s.model.OnReviewActivity rings1 = new com.app.b4s.model.OnReviewActivity("Kannada", "Poem1", "Today | 10:30 AM");
        com.app.b4s.model.OnReviewActivity rings2 = new com.app.b4s.model.OnReviewActivity("Kannada", "Poem", "Today | 10:30 AM");
        com.app.b4s.model.OnReviewActivity rings3 = new com.app.b4s.model.OnReviewActivity("Kannada", "Poem", "Today | 10:30 AM");


        onReviewActivities.add(rings1);
        onReviewActivities.add(rings2);
        onReviewActivities.add(rings3);


        onReviewActivityAdapter = new OnReviewActivityAdapter(onReviewActivities, getActivity());
        binding.rvReview.setAdapter(onReviewActivityAdapter);
    }

    private void completed() {


        ArrayList<CompletedActivitymodel> completedActivitymodels = new ArrayList<>();
        CompletedActivitymodel rings1 = new CompletedActivitymodel("Kannada", "Poem2", "Today | 10:30 AM");
        CompletedActivitymodel rings2 = new CompletedActivitymodel("Kannada", "Poem", "Today | 10:30 AM");
        CompletedActivitymodel rings3 = new CompletedActivitymodel("Kannada", "Poem", "Today | 10:30 AM");

        completedActivitymodels.add(rings1);
        completedActivitymodels.add(rings2);
        completedActivitymodels.add(rings3);

        completedActivityAdapter = new CompletedActivityAdapter(completedActivitymodels, getActivity());
        binding.rvCompleted.setAdapter(completedActivityAdapter);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mCalendar.getTime());
        datePicker.setText(selectedDate);
    }
}
