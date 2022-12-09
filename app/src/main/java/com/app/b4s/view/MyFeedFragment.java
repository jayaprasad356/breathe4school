package com.app.b4s.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.b4s.R;
import com.app.b4s.commons.ResponseListener;
import com.app.b4s.controller.DashBoardController;
import com.app.b4s.controller.IDashBoardController;
import com.app.b4s.databinding.FragmentMyFeedBinding;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.Constant;


public class MyFeedFragment extends Fragment implements ResponseListener {

    RecyclerView rvClasses;
    View includedEditProfile;
    ImageView ivProfile;
    LinearLayout llmyfeed;
    ImageButton ibBackBtn;
    FragmentMyFeedBinding binding;
    IDashBoardController dashBoardController;
    Activity context;
    Session session;

    public MyFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_feed, container, false);
        context = getActivity();
        session=new Session(getActivity());
        includedEditProfile = binding.includedEditProfile;
        llmyfeed = binding.llmyfeed;
        dashBoardController = new DashBoardController(this);
        dashBoardController.getThisDay(context);
        dashBoardController.getThought(context);

        //ibBackBtn =binding.ivProfile;
        binding.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editprofile();

            }
        });
//        ibBackBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                includedEditProfile.setVisibility(View.GONE);
//                llmyfeed.setVisibility(View.VISIBLE);
//
//            }
//        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void editprofile() {

        includedEditProfile.setVisibility(View.VISIBLE);
        llmyfeed.setVisibility(View.GONE);
    }

    @Override
    public void onSuccess(String type) {
        binding.onThisDay.setText("-"+session.getData(Constant.ON_THIS_DAY));
        binding.tvdescription.setText(session.getData(Constant.QUOTE));
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void setPasswordSuccess() {

    }
}