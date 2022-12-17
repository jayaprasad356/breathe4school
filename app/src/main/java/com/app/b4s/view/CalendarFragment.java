package com.app.b4s.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.app.b4s.R;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.controller.CalendarController;
import com.app.b4s.controller.CalendarResponse;
import com.app.b4s.controller.ICalendarController;
import com.app.b4s.databinding.FragmentCalendarBinding;
import com.app.b4s.utilities.Constant;

import org.json.JSONArray;
import org.json.JSONException;


public class CalendarFragment extends Fragment implements CalendarResponse {

    private FragmentCalendarBinding binding;
    CommonMethods commonMethods;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);
        commonMethods = new CommonMethods();
        ICalendarController calendarController = new CalendarController(this);
        calendarController.loadTimeTable(getActivity());
        binding.tvWeekly.setOnClickListener(view -> {
            binding.weeklyCard.setRadius(6);
            binding.dailyCard.setRadius(0);
            binding.weeklyCard.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
            binding.dailyCard.setCardBackgroundColor(0);
            binding.dailyLayout.setVisibility(View.GONE);
            binding.weeklyLayout.setVisibility(View.VISIBLE);
        });
        binding.tvDaily.setOnClickListener(view -> {
            binding.weeklyCard.setRadius(0);
            binding.dailyCard.setRadius(6);
            binding.weeklyCard.setCardBackgroundColor(0);
            binding.dailyCard.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
            binding.weeklyLayout.setVisibility(View.GONE);
            binding.dailyLayout.setVisibility(View.VISIBLE);
        });


        return binding.getRoot();
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        System.out.println(jsonArray);
        try {
            int startTime = Integer.parseInt(jsonArray.getJSONObject(0).getString(Constant.START_TIME));
            int endTime = Integer.parseInt(jsonArray.getJSONObject(0).getString(Constant.END_TIME));
            binding.tvSubject.setText(jsonArray.getJSONObject(0).getString(Constant.NAME));
            binding.tvstartTime.setText(commonMethods.militaryToOrdinaryTime(startTime));
            binding.tvEndTime.setText(commonMethods.militaryToOrdinaryTime(endTime));
            binding.tvDuriation.setText(endTime - startTime + "mins");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(String msg) {

    }

}