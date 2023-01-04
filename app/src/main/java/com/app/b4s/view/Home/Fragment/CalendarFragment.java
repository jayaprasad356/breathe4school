package com.app.b4s.view.Home.Fragment;

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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.adapter.HomepageAdapter.DailyTimeTableAdapter;
import com.app.b4s.adapter.HomepageAdapter.WeeklyTimeTableAdapter;
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
import com.app.b4s.model.days.Friday;
import com.app.b4s.model.days.Monday;
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
import java.util.List;
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

        List<String> subjects = session.getArrayList(Constant.SUBJECTS_KEY);
        List<String> subjectIds = session.getArrayList(Constant.SUBJECTS_ID_KEY);
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.add_study_planer);
        Spinner spinner = dialog.findViewById(R.id.spinnerSubjects);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, subjects);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubject = parent.getItemAtPosition(position).toString();
                String selectedSubjectId = subjectIds.get(position);
                session.setData(Constant.SELECTED_SUBJECT, selectedSubject);
                session.setData(Constant.SELECTED_SUBJECT_ID, selectedSubjectId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Spinner startTime = dialog.findViewById(R.id.spinnerST);
        Spinner endTime = dialog.findViewById(R.id.spinnerET);
        startTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 1) {
                    String startDate = adapterView.getItemAtPosition(i).toString();
                    session.setData(Constant.START_TIME, commonMethods.ordinaryToMilitaryTime(startDate));
                    System.out.println(startDate);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        endTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 1) {
                    String endTime = adapterView.getItemAtPosition(i).toString();
                    session.setData(Constant.END_TIME, commonMethods.ordinaryToMilitaryTime(endTime));
                    System.out.println(endTime);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
        String subName, description;
        TextView planerName = dialog.findViewById(R.id.tvPlanerName);
        TextView planerDesc = dialog.findViewById(R.id.etDescription);
        session.setData(Constant.PLANER_NAME, planerName.getText().toString());
        session.setData(Constant.PLANER_DESC, planerDesc.getText().toString());
        studyPlanerController.createStudyPlaner(getActivity(), checkedBox);


    }

    private ArrayList selectedDays(Dialog dialog, ArrayList<String> checkedBox) {
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
        return checkedBox;
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
                        ArrayList<DailyTimeTables> dailyTimeTables = new ArrayList<>();
                        if (!(jsonArray.length() == 0)) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                            JSONArray schedules = jsonObject2.getJSONArray(Constant.SCHEDULES);
                            JSONObject jsonObject3 = schedules.getJSONObject(0);
                            JSONArray lectures = jsonObject3.getJSONArray(Constant.LECTURES);
                            JSONArray general_categories = null;
                            if (jsonObject3.has("general_categories"))
                                general_categories = jsonObject3.getJSONArray("general_categories");


                            Log.d("DAILY TIME TABLES", schedules.toString());
                            Gson g = new Gson();

                            JSONObject jsonObject1 = null;
                            JSONObject proxyObject = null;
                            Boolean lunch = true;
                            JSONArray proxyLectures = null;
                            JSONObject lunchObject = null;

                            if (type.equals(Constant.LIVE_SESSION)) {
                                JSONArray splLectures = jsonObject3.getJSONArray(Constant.SPECIAL_LECTURES);
                                proxyLectures = jsonObject3.getJSONArray(Constant.PROXY_LECTURES);
                                for (int j = 0; j < splLectures.length(); j++) {
                                    JSONObject splLectureObject = splLectures.getJSONObject(j);
                                    if (splLectureObject != null) {
                                        DailyTimeTables group = g.fromJson(splLectureObject.toString(), DailyTimeTables.class);
                                        dailyTimeTables.add(group);
                                    }
                                }
                            }

                            for (int i = 0; i < lectures.length(); i++) {
                                jsonObject1 = lectures.getJSONObject(i);
                                if (jsonObject1 != null) {
                                    if (type.equals(Constant.LIVE_SESSION)){
                                        if (jsonObject1.getInt(Constant.START_TIME) >= 1400) {
                                            lunch = false;
                                            lunchObject = general_categories.getJSONObject(0);
                                            if (lunchObject != null) {
                                                DailyTimeTables groups = g.fromJson(lunchObject.toString(), DailyTimeTables.class);
                                                dailyTimeTables.add(groups);
                                            }
                                        }
                                }
                                    DailyTimeTables group = g.fromJson(jsonObject1.toString(), DailyTimeTables.class);
                                    dailyTimeTables.add(group);
                                } else {
                                    break;
                                }
                            }
                            if (type.equals(Constant.LIVE_SESSION) && lunch) {
                                lunchObject = general_categories.getJSONObject(0);
                                if (lunchObject != null) {
                                    DailyTimeTables groups = g.fromJson(lunchObject.toString(), DailyTimeTables.class);
                                    dailyTimeTables.add(groups);
                                }
                            }
                            if (type.equals(Constant.LIVE_SESSION)) {
                                for (int i = 0; i < proxyLectures.length(); i++) {
                                    proxyObject = proxyLectures.getJSONObject(i);
                                    if (proxyObject != null) {
                                        for (int k = 0; k < dailyTimeTables.size(); k++) {
                                            if (dailyTimeTables.get(k).getStart_time().equals(proxyObject.getString(Constant.START_TIME))) {
                                                dailyTimeTables.get(k).setStart_time(proxyObject.getString(Constant.START_TIME));
                                                dailyTimeTables.get(k).setEnd_time(proxyObject.getString(Constant.END_TIME));
                                                dailyTimeTables.get(k).setActivity_id(proxyObject.getString(Constant.ACTIVITY_ID));
                                                dailyTimeTables.get(k).setAssessment_id(proxyObject.getString(Constant.ASSESSMENT_ID));
                                                dailyTimeTables.get(k).setPre_read_id(proxyObject.getString(Constant.PRE_READ_ID));
                                                dailyTimeTables.get(k).setBbb_lecture_id(proxyObject.getString(Constant.BB_LECTURE_ID));
                                                dailyTimeTables.get(k).setName(proxyObject.getString(Constant.NAME));
                                                dailyTimeTables.get(k).setSubject(proxyObject.getString(Constant.SUBJECT));
                                            }
                                        }

                                    } else {
                                        break;
                                    }
                                }
                            }
                            rcDailyTables.setVisibility(View.VISIBLE);
                            binding.titleLayout.setVisibility(View.VISIBLE);
                            DailyTimeTableAdapter adapter = new DailyTimeTableAdapter(dailyTimeTables, getActivity(), type);
                            rcDailyTables.setAdapter(adapter);
                        } else {
                            rcDailyTables.setVisibility(View.GONE);
                            binding.titleLayout.setVisibility(View.GONE);
                        }

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
                        String mon_name = "", tue_name = "", wed_name = "", thurs_name = "", fri_name = "", satur_name = "", sun_name = "";
                        ArrayList<WeeklyTimeTable> wt = null;
                        for (int i = 0; i < lectures.length(); i++) {
                            JSONObject jsonObject1 = lectures.getJSONObject(i);
                            if (jsonObject1 != null) {
                                int startTime = jsonObject1.getInt(Constant.START_TIME);
                                int endTime = jsonObject1.getInt(Constant.END_TIME);
                                mon_name = mondaySubjects(startTime, endTime, jsonObject1, Constant.MONDAY, mon_name);
                                tue_name = tuesdaySubjects(startTime, endTime, jsonObject1, Constant.TUESDAY, tue_name);
                                wed_name = wednesdaySunjects(startTime, endTime, jsonObject1, Constant.WEDNESDAY, wed_name);
                                thurs_name = thursdaySubjects(startTime, endTime, jsonObject1, Constant.THURSDAY, thurs_name);
                                fri_name = fridaySubjects(startTime, endTime, jsonObject1, Constant.FRIDAY, fri_name);
                                satur_name = saturdaySubjects(startTime, endTime, jsonObject1, Constant.SATURDAY, satur_name);
                                sun_name = sundaySubjects(startTime, endTime, jsonObject1, Constant.SUNDAY, sun_name);
                                wt = new ArrayList<>();
                                //WeeklyTimeTable w1 = new WeeklyTimeTable("Maths","Science","Hindi","Social","Maths","English","Computer");
                                WeeklyTimeTable w1 = new WeeklyTimeTable(mon_name, tue_name, wed_name, thurs_name, fri_name, satur_name, sun_name);

                                wt.add(w1);
                            } else {
                                break;
                            }
                        }
//                        ArrayList<WeeklyTimeTable> wt = new ArrayList<>();
//                        //WeeklyTimeTable w1 = new WeeklyTimeTable("Maths","Science","Hindi","Social","Maths","English","Computer");
//                        WeeklyTimeTable w1 = new WeeklyTimeTable(mon_name, tue_name, wed_name, thurs_name, fri_name, satur_name, sun_name);
//
//                        wt.add(w1);
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

    private String sundaySubjects(int startTime, int endTime, JSONObject jsonObject1, String sunday, String sun_name) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            sun_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            sun_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            sun_name = jsonObject1.getString(Constant.NAME);

        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            sun_name = jsonObject1.getString(Constant.NAME);

        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.SUNDAY)) {
            sun_name = jsonObject1.getString(Constant.NAME);

        }


        return sun_name;
    }

    private String saturdaySubjects(int startTime, int endTime, JSONObject jsonObject1, String saturday, String satur_name) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            satur_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            satur_name = jsonObject1.getString(Constant.NAME);

        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            satur_name = jsonObject1.getString(Constant.NAME);

        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            satur_name = jsonObject1.getString(Constant.NAME);

        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.SATURDAY)) {
            satur_name = jsonObject1.getString(Constant.NAME);

        }
        return satur_name;
    }

    private String fridaySubjects(int startTime, int endTime, JSONObject jsonObject1, String friday, String fri_name) throws JSONException {

        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            fri_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            fri_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            fri_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            fri_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.FRIDAY)) {
            fri_name = jsonObject1.getString(Constant.NAME);
        }
        return fri_name;
    }

    private String thursdaySubjects(int startTime, int endTime, JSONObject jsonObject1, String thursday, String thurs_name) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            thurs_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            thurs_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            thurs_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.THURSDAY)) {
            thurs_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            thurs_name = jsonObject1.getString(Constant.NAME);
        }
        return thurs_name;
    }

    private String wednesdaySunjects(int startTime, int endTime, JSONObject jsonObject1, String wednesday, String wed_name) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {
            wed_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {
            wed_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {
            wed_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {
            wed_name = jsonObject1.getString(Constant.NAME);
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.WEDNESDAY)) {
            wed_name = jsonObject1.getString(Constant.NAME);
        }
        return wed_name;
    }

    private String tuesdaySubjects(int startTime, int endTime, JSONObject jsonObject1, String tuesday, String tue_name) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            tue_name = jsonObject1.get(Constant.NAME).toString();
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            tue_name = jsonObject1.get(Constant.NAME).toString();
        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            tue_name = jsonObject1.get(Constant.NAME).toString();
        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            tue_name = jsonObject1.get(Constant.NAME).toString();
        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.TUESDAY)) {
            tue_name = jsonObject1.get(Constant.NAME).toString();
        }
        return tue_name;
    }

    private String mondaySubjects(int startTime, int endTime, JSONObject jsonObject1, String monday, String mon_name) throws JSONException {
        if (startTime >= 700 && endTime <= 740 && jsonObject1.get(Constant.DAY).equals(monday)) {
            mon_name = jsonObject1.get(Constant.NAME).toString();
        }
        if (startTime >= 740 && endTime <= 820 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            mon_name = jsonObject1.get(Constant.NAME).toString();

        }
        if (startTime >= 820 && endTime <= 900 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            mon_name = jsonObject1.get(Constant.NAME).toString();

        }
        if (startTime >= 1130 && endTime <= 1230 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            mon_name = jsonObject1.get(Constant.NAME).toString();

        }
        if (startTime >= 1400 && endTime <= 1500 && jsonObject1.get(Constant.DAY).equals(Constant.MONDAY)) {
            mon_name = jsonObject1.get(Constant.NAME).toString();

        }
        return mon_name;
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