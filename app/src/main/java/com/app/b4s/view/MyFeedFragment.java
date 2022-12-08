package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.b4s.R;


public class MyFeedFragment extends Fragment {

    RecyclerView rvClasses;
    View includedEditProfile;
    ImageView ivProfile;
    LinearLayout llmyfeed;
    ImageButton ibBackBtn;





    public MyFeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_feed, container, false);


        includedEditProfile = root.findViewById(R.id.includedEditProfile);
        ivProfile = root.findViewById(R.id.ivProfile);
        llmyfeed = root.findViewById(R.id.llmyfeed);
        ibBackBtn = root.findViewById(R.id.ibBackBtn);



        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editprofile();

            }
        });
        ibBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                includedEditProfile.setVisibility(View.GONE);
                llmyfeed.setVisibility(View.VISIBLE);

            }
        });



        return root;
    }

    private void editprofile() {

        includedEditProfile.setVisibility(View.VISIBLE);
        llmyfeed.setVisibility(View.GONE);
    }
}