package com.app.b4s.view.DCM.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.DcmAdapter.SummaryAdapter;
import com.app.b4s.model.Dcm.Summarymodel;

import java.util.ArrayList;


public class AssessmentOnreviewFragment extends Fragment {


    Activity activity;
    SummaryAdapter summaryAdapter;
    RecyclerView rvOnReminderView;
    


    public AssessmentOnreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_assessment_onreview, container, false);
        activity = getActivity();


        rvOnReminderView = root.findViewById(R.id.rvOnReminderView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,3);
        rvOnReminderView.setLayoutManager(gridLayoutManager);
        onReview();

        return root;
    }

    private void onReview() {
        ArrayList<Summarymodel> summarymodels = new ArrayList<>();


        Summarymodel rings1 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings2 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings3 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");



        summarymodels.add(rings1);
        summarymodels.add(rings2);
        summarymodels.add(rings3);



        summaryAdapter = new SummaryAdapter(summarymodels, activity);
        rvOnReminderView.setAdapter(summaryAdapter);
        
    }
}