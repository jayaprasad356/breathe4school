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
import android.widget.AdapterView;
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
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.days.Friday;
import com.app.b4s.model.days.Monday;
import com.app.b4s.model.WeeklyTimeTable;
import com.app.b4s.model.days.Saturday;
import com.app.b4s.model.days.Sunday;
import com.app.b4s.model.days.Thursday;
import com.app.b4s.model.days.Tuesday;
import com.app.b4s.model.days.Wednesday;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
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
        studyPlanerController = new StudyPlanerController(this);
        ICalendarController calendarController = new CalendarController(this);
        calendarController.loadTimeTable(getActivity());
        LinearLayoutManager dailyTimeTable = new LinearLayoutManager(getActivity());
        LinearLayoutManager weeklyTimeTable = new LinearLayoutManager(getActivity());
        rcDailyTables = binding.dailyRecycler;
        rcWeeklyTables = binding.rcWeekly;
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item_position = String.valueOf(i);
                int itemposition = Integer.parseInt(item_position);
                loadDailyTimeTables(itemposition);
                System.out.println(itemposition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        rcWeeklyTables.setLayoutManager(weeklyTimeTable);
        rcDailyTables.setLayoutManager(dailyTimeTable);
        loadDailyTimeTables(1);
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
        selectedDays(dialog, checkedBox);
        studyPlanerController.createStudyPlaner(getActivity(), checkedBox);


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
        loadDailyTimeTables(1);
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

    private void loadDailyTimeTables(int itemposition) {
        String url, type;
        if (itemposition == 2) {
            type = Constant.STUDY_PLANER;
            url = "http://143.244.132.170:3001/api/v1/studyPlanner/getDailyStudyPlanner/academicYearId/" +
                    session.getData(Constant.ACADEMIC_YEAR_ID) + "/schoolId/" + session.getData(Constant.SCHOOL_ID) +
                    "/standardId/" + session.getData(Constant.STANDARD_ID) + "/sectionId/" +
                    session.getData(Constant.SECTION_ID) + "/studentId/" + session.getData(Constant.STUDENT_ID);
        } else {
            type = Constant.LIVE_SESSION;
            url = "http://143.244.132.170:3001/api/v1/timetable/getDailyTimetable/academicYearId/" +
                    session.getData(Constant.ACADEMIC_YEAR_ID) + "/schoolId/" + session.getData(Constant.SCHOOL_ID) +
                    "/standardId/" + session.getData(Constant.STANDARD_ID) + "/sectionId/" +
                    session.getData(Constant.SECTION_ID) + "/timetableSessionId/" +
                    session.getData(Constant.TIME_TABLE_SESSION_ID);
        }
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
                        DailyTimeTableAdapter adapter = new DailyTimeTableAdapter(dailyTimeTables, getActivity(), type);
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
                        ArrayList<Monday> mondays = new ArrayList<>();
                        ArrayList<Tuesday> tuesdays = new ArrayList<>();
                        ArrayList<Wednesday> wednesdays = new ArrayList<>();
                        ArrayList<Thursday> thursdays = new ArrayList<>();
                        ArrayList<Friday> fridays = new ArrayList<>();
                        ArrayList<Saturday> saturdays = new ArrayList<>();
                        ArrayList<Sunday> sundays = new ArrayList<>();
                        String mon_name = "",tue_name = "",wed_name = "",thurs_name = "",fri_name = "",satur_name = "",sun_name = "";

                        for (int i = 0; i < lectures.length(); i++) {
                            JSONObject jsonObject1 = lectures.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int startTime = jsonObject1.getInt(Constant.START_TIME);
                                int endTime = jsonObject1.getInt(Constant.END_TIME);
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
                                    mon_name = jsonObject1.get(Constant.NAME).toString();
                                }
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
                                    tue_name = jsonObject1.get(Constant.NAME).toString();
                                }
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {
                                    wed_name = jsonObject1.get(Constant.NAME).toString();
                                }
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
                                    thurs_name = jsonObject1.get(Constant.NAME).toString();
                                }
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
                                    fri_name = jsonObject1.get(Constant.NAME).toString();
                                }
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
                                    satur_name = jsonObject1.get(Constant.NAME).toString();
                                }
                                if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
                                    sun_name = jsonObject1.get(Constant.NAME).toString();
                                }

                            } else {
                                break;
                            }
                        }
                        ArrayList<WeeklyTimeTable> wt = new ArrayList<>();
                        //WeeklyTimeTable w1 = new WeeklyTimeTable("Maths","Science","Hindi","Social","Maths","English","Computer");
                        WeeklyTimeTable w1 = new WeeklyTimeTable(mon_name,tue_name,wed_name,thurs_name,fri_name,satur_name,sun_name);

                        wt.add(w1);
                        WeeklyTimeTableAdapter adapter = new WeeklyTimeTableAdapter(wt, getActivity());
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

    private void mondayDatas(Gson g, ArrayList<Monday> mondays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            Monday group = g.fromJson(jsonObject1.toString(), Monday.class);
            mondays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            Monday group = g.fromJson(jsonObject1.toString(), Monday.class);
            mondays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            Monday group = g.fromJson(jsonObject1.toString(), Monday.class);
            mondays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            Monday group = g.fromJson(jsonObject1.toString(), Monday.class);
            mondays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            Monday group = g.fromJson(jsonObject1.toString(), Monday.class);
            mondays.add(group);
        }
    }

    private void tuesDayDatas(Gson g, ArrayList<Tuesday> tuesdays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            Tuesday group = g.fromJson(jsonObject1.toString(), Tuesday.class);
            tuesdays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            Tuesday group = g.fromJson(jsonObject1.toString(), Tuesday.class);
            tuesdays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            Tuesday group = g.fromJson(jsonObject1.toString(), Tuesday.class);
            tuesdays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            Tuesday group = g.fromJson(jsonObject1.toString(), Tuesday.class);
            tuesdays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            Tuesday group = g.fromJson(jsonObject1.toString(), Tuesday.class);
            tuesdays.add(group);
        }
    }

    private void wednesDayDatas(Gson g, ArrayList<Wednesday> wednesdays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {

            Wednesday group = g.fromJson(jsonObject1.toString(), Wednesday.class);
            wednesdays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {

            Wednesday group = g.fromJson(jsonObject1.toString(), Wednesday.class);
            wednesdays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {

            Wednesday group = g.fromJson(jsonObject1.toString(), Wednesday.class);
            wednesdays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {

            Wednesday group = g.fromJson(jsonObject1.toString(), Wednesday.class);
            wednesdays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {

            Wednesday group = g.fromJson(jsonObject1.toString(), Wednesday.class);
            wednesdays.add(group);
        }
    }

    private void thursDayDatas(Gson g, ArrayList<Thursday> thursdays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            Thursday group = g.fromJson(jsonObject1.toString(), Thursday.class);
            thursdays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            Thursday group = g.fromJson(jsonObject1.toString(), Thursday.class);
            thursdays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            Thursday group = g.fromJson(jsonObject1.toString(), Thursday.class);
            thursdays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            Thursday group = g.fromJson(jsonObject1.toString(), Thursday.class);
            thursdays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            Thursday group = g.fromJson(jsonObject1.toString(), Thursday.class);
            thursdays.add(group);
        }
    }
    private void fridayDatas(Gson g, ArrayList<Friday> fridays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            Friday group = g.fromJson(jsonObject1.toString(), Friday.class);
            fridays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            Friday group = g.fromJson(jsonObject1.toString(), Friday.class);
            fridays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            Friday group = g.fromJson(jsonObject1.toString(), Friday.class);
            fridays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            Friday group = g.fromJson(jsonObject1.toString(), Friday.class);
            fridays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            Friday group = g.fromJson(jsonObject1.toString(), Friday.class);
            fridays.add(group);
        }
    }

    private void saturdayDatas(Gson g, ArrayList<Saturday> saturdays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            Saturday group = g.fromJson(jsonObject1.toString(), Saturday.class);
            saturdays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            Saturday group = g.fromJson(jsonObject1.toString(), Saturday.class);
            saturdays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            Saturday group = g.fromJson(jsonObject1.toString(), Saturday.class);
            saturdays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            Saturday group = g.fromJson(jsonObject1.toString(), Saturday.class);
            saturdays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            Saturday group = g.fromJson(jsonObject1.toString(), Saturday.class);
            saturdays.add(group);
        }
    }

    private void sundayDatas(Gson g, ArrayList<Sunday> sundays, JSONObject jsonObject1, int startTime, int endTime) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            Sunday group = g.fromJson(jsonObject1.toString(), Sunday.class);
            sundays.add(group);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            Sunday group = g.fromJson(jsonObject1.toString(), Sunday.class);
            sundays.add(group);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            Sunday group = g.fromJson(jsonObject1.toString(), Sunday.class);
            sundays.add(group);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            Sunday group = g.fromJson(jsonObject1.toString(), Sunday.class);
            sundays.add(group);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            Sunday group = g.fromJson(jsonObject1.toString(), Sunday.class);
            sundays.add(group);
        }
    }

}