package com.app.b4s.view;

import static com.app.b4s.utilities.Constant.STATUS;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.adapter.NoticeAdapter;
import com.app.b4s.adapter.UpcommingAdapter;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.DashBoardController;
import com.app.b4s.controller.IDashBoardController;
import com.app.b4s.databinding.FragmentMyFeedBinding;
import com.app.b4s.model.DayOfLine;
import com.app.b4s.model.StudentNotice;
import com.app.b4s.model.UpCommingData;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyFeedFragment extends Fragment implements ResponseListener {

    RecyclerView rcNotices, reClasses;
    LinearLayout llmyfeed;
    FragmentMyFeedBinding binding;
    IDashBoardController dashBoardController;
    Activity context;
    Session session;
    String studentName, greeting, link, tipUrl;

    public MyFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_feed, container, false);
        context = getActivity();
        session = new Session(getActivity());
        llmyfeed = binding.llmyfeed;
        dashBoardController = new DashBoardController(this);
        dashBoardController.getThisDay(context);
        dashBoardController.getThought(context);
        rcNotices = binding.NoticeBoardRecycler;
        reClasses = binding.rvClasses;
        setGreatingText();
        setStudentInfo();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager upCommingClassesManager = new LinearLayoutManager(getActivity());
        rcNotices.setLayoutManager(linearLayoutManager);
        reClasses.setLayoutManager(upCommingClassesManager);
        loadStudentNotice();
        loadUpcommingClasses();
        studentName = session.getData(Constant.NAME);
        binding.tvTitleName.setText("Hi " + studentName + ", " + greeting);
        binding.ivProfile.setOnClickListener(v -> editprofile());
        binding.ibBackBtn.setOnClickListener(v -> {
            binding.editProfile.setVisibility(View.GONE);
            llmyfeed.setVisibility(View.VISIBLE);
        });

        binding.onThisDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(link));
                startActivity(browserIntent);
            }
        });
        return binding.getRoot();
    }

    private void loadUpcommingClasses() {
        String url = "http://143.244.132.170:3001/api/v1/timetable/getUpcomingClasses/academicYearId/" +
                session.getData(Constant.ACADEMIC_YEAR_ID) + "/schoolId/" + session.getData(Constant.SCHOOL_ID) +
                "/standardId/" + session.getData(Constant.STANDARD_ID) + "/sectionId/" +
                session.getData(Constant.SECTION_ID) + "/timetableSessionId/" +
                session.getData(Constant.TIME_TABLE_SESSION_ID);

        //String url1 = "http://143.244.132.170:3001/api/v1/timetable/getUpcomingClasses/academicYearId/62a843c2c657c9f8ab54e629/schoolId/629e48822d8dc59764b3b057/standardId/62adb3030a7ac055c1ec6fd1/sectionId/629e48be2d8dc59764b3b059/timetableSessionId/62a855ac1e42b892d5951606";
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
                        Log.d("UPCOMING_CLASS", schedules.toString());
                        Gson g = new Gson();
                        ArrayList<UpCommingData> upCommingData = new ArrayList<>();
                        for (int i = 0; i < lectures.length(); i++) {
                            JSONObject jsonObject1 = lectures.getJSONObject(i);
                            if (jsonObject1 != null) {
                                UpCommingData group = g.fromJson(jsonObject1.toString(), UpCommingData.class);
                                upCommingData.add(group);
                            } else {
                                break;
                            }
                        }
                        UpcommingAdapter adapter = new UpcommingAdapter(upCommingData, getActivity());
                        reClasses.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, getActivity(), url, params, true, 0);

    }

    private void loadStudentNotice() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.STUDENT_ID, session.getData(Constant.STUDENT_ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(STATUS)) {
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        Gson g = new Gson();
                        ArrayList<StudentNotice> studentNotices = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            if (jsonObject1 != null) {
                                StudentNotice group = g.fromJson(jsonObject1.toString(), StudentNotice.class);
                                studentNotices.add(group);
                            } else {
                                break;
                            }
                        }
                        NoticeAdapter adapter = new NoticeAdapter(studentNotices, getActivity());
                        rcNotices.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, getActivity(), Constant.GET_NOTICE_BY_STUDENT_ID, params, true, 1);
    }

    private void setStudentInfo() {
        if (session.getData(Constant.GENDER).equals(Constant.MALE)) {
            binding.ivGenderImage.setImageResource(R.drawable.img_title_card);
            binding.gender.setText("MALE");
        } else {
            binding.ivGenderImage.setImageResource(R.drawable.female_logo);
            binding.gender.setText("FEMALE");
        }
        binding.dateOfBirth.setText(session.getData(Constant.DOB));
        binding.fatherName.setText(session.getData(Constant.FATHER_NAME));
        binding.motherName.setText("-");
        binding.contactNumber.setText("-");
        binding.alterNumber.setText("-");
        binding.imei.setText("-");
        binding.clas.setText("-");
        binding.division.setText("-");
        binding.classTeacher.setText("-");
        binding.parentEmailId.setText(session.getData(Constant.PARENT_EMAIL));
    }

    private void setGreatingText() {
        Date dt = new Date();
        int hours = dt.getHours();

        if (hours >= 1 && hours <= 12) {
            greeting = getString(R.string.good_morning);
        } else if (hours >= 12 && hours <= 16) {
            greeting = getString(R.string.good_afternoon);
        } else if (hours >= 16 && hours <= 21) {
            greeting = getString(R.string.good_evening);
        } else if (hours >= 21 && hours <= 24) {
            greeting = getString(R.string.good_night);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void editprofile() {

        binding.editProfile.setVisibility(View.VISIBLE);
        llmyfeed.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String type) {
        binding.tvdescription.setText(session.getData(Constant.QUOTE));
        binding.tvAuther.setText("-" + session.getData(Constant.AUTHOR));
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void setPasswordSuccess() {

    }

    @Override
    public void OnSuccess(ArrayList<DayOfLine> arrayList) {
        binding.onThisDay.setText("-" + arrayList.get(0).getText());
        binding.tvDayTitle.setText(arrayList.get(0).getTitle());
        tipUrl = arrayList.get(0).getOrignal_image();
        link = arrayList.get(0).getDetails_url();

        Glide.with(context)
                .load(tipUrl)
                .into(binding.imageTips);

    }
}