package com.app.b4s.view;

import static com.app.b4s.utilities.Constant.SUCCESS;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.adapter.NoticeAdapter;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.DashBoardController;
import com.app.b4s.controller.IDashBoardController;
import com.app.b4s.databinding.FragmentMyFeedBinding;
import com.app.b4s.model.StudentNotice;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MyFeedFragment extends Fragment implements ResponseListener {

    RecyclerView rvClasses;
    LinearLayout llmyfeed;
    ImageButton ibBackBtn;
    FragmentMyFeedBinding binding;
    IDashBoardController dashBoardController;
    Activity context;
    Session session;
    String studentName, greeting;

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
        rvClasses=binding.NoticeBoardRecycler;
        setGreatingText();
        setStudentInfo();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvClasses.setLayoutManager(linearLayoutManager);
        loadStudentNotice();
        studentName = session.getData(Constant.NAME);
        binding.tvTitleName.setText("Hi " + studentName + ", " + greeting);
        //ibBackBtn =binding.ivProfile;
        binding.ivProfile.setOnClickListener(v -> editprofile());
        binding.ibBackBtn.setOnClickListener(v -> {
            binding.editProfile.setVisibility(View.GONE);
            llmyfeed.setVisibility(View.VISIBLE);
        });


        return binding.getRoot();
    }

    private void loadStudentNotice() {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(SUCCESS)) {
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
                        rvClasses.setAdapter(adapter);
                    } else {
                        Toast.makeText(getActivity(), jsonObject.getString(Constant.MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, getActivity(), Constant.GET_NOTICE_BY_STUDENT_ID, params, true,0);
    }

    private void setStudentInfo() {
        if (session.getData(Constant.GENDER).equals(Constant.MALE)){
            binding.ivGenderImage.setImageResource(R.drawable.img_title_card);
            binding.gender.setText("MALE");
        }else {
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
        binding.onThisDay.setText("-" + session.getData(Constant.ON_THIS_DAY));
        binding.tvdescription.setText(session.getData(Constant.QUOTE));
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void setPasswordSuccess() {

    }
}