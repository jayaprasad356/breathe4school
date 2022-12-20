package com.app.b4s.view;

import static com.app.b4s.utilities.Constant.STATUS;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.adapter.DailyTimeTableAdapter;
import com.app.b4s.adapter.WeeklyTimeTableAdapter;
import com.app.b4s.commons.CommonMethods;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.CalendarController;
import com.app.b4s.controller.CalendarResponse;
import com.app.b4s.controller.ICalendarController;
import com.app.b4s.controller.IStudyPlanerController;
import com.app.b4s.controller.StudyPlanerController;
import com.app.b4s.databinding.FragmentCalendarBinding;
import com.app.b4s.model.DailyTimeTables;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.model.WeeklyTimeTable;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CalendarFragment extends Fragment implements CalendarResponse, ResponseListener {

    private FragmentCalendarBinding binding;
    CommonMethods commonMethods;
    IStudyPlanerController studyPlanerController;
    Session session;
    RecyclerView rcDailyTables, rcWeeklyTables;
    private Spinner spinner;
    private PopupWindow popupWindow;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calendar, container, false);
        commonMethods = new CommonMethods();
        session = new Session(getActivity());
        studyPlanerController=new StudyPlanerController(this);
        ICalendarController calendarController = new CalendarController(this);
        calendarController.loadTimeTable(getActivity());
        LinearLayoutManager dailyTimeTable = new LinearLayoutManager(getActivity());
        LinearLayoutManager weeklyTimeTable = new LinearLayoutManager(getActivity());
        rcDailyTables = binding.dailyRecycler;
        rcWeeklyTables = binding.rcWeekly;
        rcWeeklyTables.setLayoutManager(weeklyTimeTable);
        rcDailyTables.setLayoutManager(dailyTimeTable);
        loadDailyTimeTables();
        binding.ivAddStudyPlaner.setOnClickListener(view -> showPopup());
        binding.tvWeekly.setOnClickListener(view -> {
            handleWeekly();
        });
        binding.tvDaily.setOnClickListener(view -> {
            handleDaily();
        });


        return binding.getRoot();
    }

    private void showPopup() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_study_planer);
        dialog.findViewById(R.id.cbMonday).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbMonday)));
        dialog.findViewById(R.id.cbTuesday).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbTuesday)));
        dialog.findViewById(R.id.cbWednes).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbWednes)));
        dialog.findViewById(R.id.cbThurs).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbThurs)));
        dialog.findViewById(R.id.cbFriday).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbFriday)));
        dialog.findViewById(R.id.cbSatur).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbSatur)));
        dialog.findViewById(R.id.cbSun).setOnClickListener(view -> checkStatus(view, dialog.findViewById(R.id.cbSun)));
        dialog.findViewById(R.id.btnCreateStudy).setOnClickListener(view -> apiCall(dialog));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }

    private void apiCall(Dialog dialog) {
        ArrayList<String> checkedBox = new ArrayList<>();
        selectedDays(dialog,checkedBox);
        Toast.makeText(getContext(), checkedBox.toString(), Toast.LENGTH_SHORT).show();
        studyPlanerController.createStudyPlaner(getActivity(),checkedBox);


    }

    private void selectedDays(Dialog dialog, ArrayList<String> checkedBox) {

        CheckBox monday, tuesDay, wednesday, thursday, friday, saturday, sunday;
        monday = dialog.findViewById(R.id.cbMonday);
        tuesDay = dialog.findViewById(R.id.cbTuesday);
        wednesday = dialog.findViewById(R.id.cbWednes);
        thursday = dialog.findViewById(R.id.cbThurs);
        friday = dialog.findViewById(R.id.cbFriday);
        saturday = dialog.findViewById(R.id.cbSatur);
        sunday = dialog.findViewById(R.id.cbSun);
        if (monday.isChecked()) {
            checkedBox.add("Monday");
        }
        if (tuesDay.isChecked()) {
            checkedBox.add("Tuesday");
        }
        if (wednesday.isChecked()) {
            checkedBox.add("Wednesday");
        }
        if (thursday.isChecked()) {
            checkedBox.add("Thursday");
        }
        if (friday.isChecked()) {
            checkedBox.add("Friday");
        }
        if (saturday.isChecked()) {
            checkedBox.add("Saturday");
        }
        if (sunday.isChecked()) {
            checkedBox.add("Sunday");
        }
    }

    private <T extends View> void checkStatus(View view, T viewById) {
        Boolean isChecked = ((MaterialCheckBox) view).isChecked();
        if (isChecked) {
            viewById.setBackgroundResource(R.drawable.check_box_bg);
        } else
            viewById.setBackgroundResource(R.drawable.check_bg);
    }


    private void handleDaily() {
        loadDailyTimeTables();
        binding.tvWeekly.setBackgroundResource(R.drawable.underline_drawable);
        binding.tvDaily.setBackgroundResource(0);
        binding.weeklyCard.setRadius(0);
        binding.dailyCard.setRadius(6);
        binding.weeklyCard.setCardBackgroundColor(0);
        binding.dailyCard.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
        binding.weeklyLayout.setVisibility(View.GONE);
        binding.dailyLayout.setVisibility(View.VISIBLE);
    }

    private void handleWeekly() {
        binding.weeklyCard.setRadius(6);
        binding.dailyCard.setRadius(0);
        binding.tvDaily.setBackgroundResource(R.drawable.underline_drawable);
        binding.tvWeekly.setBackgroundResource(0);

        binding.weeklyCard.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
        binding.dailyCard.setCardBackgroundColor(0);
        binding.dailyLayout.setVisibility(View.GONE);
        binding.weeklyLayout.setVisibility(View.VISIBLE);
        loadWeeklyTimeTables();
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
//        System.out.println(jsonArray);
//        try {
//            int startTime = Integer.parseInt(jsonArray.getJSONObject(0).getString(Constant.START_TIME));
//            int endTime = Integer.parseInt(jsonArray.getJSONObject(0).getString(Constant.END_TIME));
//            binding.tvSubject.setText(jsonArray.getJSONObject(0).getString(Constant.NAME));
//            binding.tvstartTime.setText(commonMethods.militaryToOrdinaryTime(startTime));
//            binding.tvEndTime.setText(commonMethods.militaryToOrdinaryTime(endTime));
//            binding.tvDuriation.setText(endTime - startTime + "mins");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onSuccess(String type) {

    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void setPasswordSuccess() {

    }

    @Override
    public void OnSuccess(ArrayList<DayOfLine> arrayList) {

    }

    private void loadDailyTimeTables() {
        String url = "http://143.244.132.170:3001/api/v1/timetable/getDailyTimetable/academicYearId/" +
                session.getData(Constant.ACADEMIC_YEAR_ID) + "/schoolId/" + session.getData(Constant.SCHOOL_ID) +
                "/standardId/" + session.getData(Constant.STANDARD_ID) + "/sectionId/" +
                session.getData(Constant.SECTION_ID) + "/timetableSessionId/" +
                session.getData(Constant.TIME_TABLE_SESSION_ID);

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                        JSONArray schedules = jsonObject2.getJSONArray(Constant.SCHEDULES);
                        JSONObject jsonObject3 = schedules.getJSONObject(0);
                        JSONArray lectures = jsonObject3.getJSONArray(Constant.LECTURES);
                        Log.d("DAILY TIME TABLES", schedules.toString());
                        Gson g = new Gson();
                        ArrayList<DailyTimeTables> dailyTimeTables = new ArrayList<>();
                        for (int i = 0; i < lectures.length(); i++) {
                            JSONObject jsonObject1 = lectures.getJSONObject(i);
                            if (jsonObject1 != null) {
                                DailyTimeTables group = g.fromJson(jsonObject1.toString(), DailyTimeTables.class);
                                dailyTimeTables.add(group);
                            } else {
                                break;
                            }
                        }
                        DailyTimeTableAdapter adapter = new DailyTimeTableAdapter(dailyTimeTables, getActivity());
                        rcDailyTables.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, getActivity(), url, params, true, 0);

    }

    private void loadWeeklyTimeTables() {
        String url = "http://143.244.132.170:3001/api/v1/timetable/get/academicYearId/" +
                session.getData(Constant.ACADEMIC_YEAR_ID) + "/schoolId/" + session.getData(Constant.SCHOOL_ID) +
                "/standardId/" + session.getData(Constant.STANDARD_ID) + "/sectionId/" +
                session.getData(Constant.SECTION_ID) + "/timetableSessionId/" +
                session.getData(Constant.TIME_TABLE_SESSION_ID);

        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                        JSONArray schedules = jsonObject2.getJSONArray(Constant.SCHEDULES);
                        JSONObject jsonObject3 = schedules.getJSONObject(0);
                        JSONArray lectures = jsonObject3.getJSONArray(Constant.LECTURES);
                        Log.d("DAILY TIME TABLES", schedules.toString());
                        Gson g = new Gson();
                        ArrayList<WeeklyTimeTable> weeklyTimeTables = new ArrayList<>();
                        for (int i = 0; i < lectures.length(); i++) {
                            JSONObject jsonObject1 = lectures.getJSONObject(i);
                            if (jsonObject1 != null) {
                                WeeklyTimeTable group = g.fromJson(jsonObject1.toString(), WeeklyTimeTable.class);
                                weeklyTimeTables.add(group);
                            } else {
                                break;
                            }
                        }
                        WeeklyTimeTableAdapter adapter = new WeeklyTimeTableAdapter(weeklyTimeTables, getActivity());
                        rcWeeklyTables.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, getActivity(), url, params, true, 0);

    }
}