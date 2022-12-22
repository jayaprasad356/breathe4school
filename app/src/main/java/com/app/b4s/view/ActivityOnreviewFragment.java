package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.OnReviewActivityAdapter;
import com.app.b4s.adapter.PendingActivityAdapter;
import com.app.b4s.model.PendingActivity;

import java.util.ArrayList;


public class ActivityOnreviewFragment extends Fragment {


    RecyclerView rvReview;
    OnReviewActivityAdapter onReviewActivityAdapter;




    public ActivityOnreviewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_activity_onreview, container, false);


        rvReview = root.findViewById(R.id.rvReview);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvReview.setLayoutManager(gridLayoutManager);
        onreview();


        return root;
    }

    private void onreview() {


        ArrayList<com.app.b4s.model.OnReviewActivity> onReviewActivities = new ArrayList<>();

        com.app.b4s.model.OnReviewActivity rings1 = new com.app.b4s.model.OnReviewActivity("Kannada","Poem1","Today | 10:30 AM");
        com.app.b4s.model.OnReviewActivity rings2 = new com.app.b4s.model.OnReviewActivity("Kannada","Poem","Today | 10:30 AM");
        com.app.b4s.model.OnReviewActivity rings3 = new com.app.b4s.model.OnReviewActivity("Kannada","Poem","Today | 10:30 AM");



        onReviewActivities.add(rings1);
        onReviewActivities.add(rings2);
        onReviewActivities.add(rings3);



        onReviewActivityAdapter = new OnReviewActivityAdapter(onReviewActivities, getActivity());
        rvReview.setAdapter(onReviewActivityAdapter);
    }
}