package com.app.b4s.view.DCM.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.b4s.R;
import com.app.b4s.adapter.DcmAdapter.SummaryAdapter;
import com.app.b4s.model.Dcm.Summarymodel;
import com.app.b4s.view.DCM.Activity.ViewSummaryActivity;

import java.util.ArrayList;


public class SummaryDcmFragment extends Fragment {

    RecyclerView rvToday,rvViewPreview;
    Activity activity;
    SummaryAdapter summaryAdapter;
    TextView tvView,tvViewPreview;



    public SummaryDcmFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_summary_dcm, container, false);
        activity = getActivity();

        rvToday = root.findViewById(R.id.rvToday);
        rvViewPreview = root.findViewById(R.id.rvViewPreview);
        tvView = root.findViewById(R.id.tvView);
        tvViewPreview = root.findViewById(R.id.tvViewPreview);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,3);
        rvToday.setLayoutManager(gridLayoutManager);
        summary();
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewSummaryActivity.class);
                startActivity(intent);
            }
        });


        GridLayoutManager gridLayoutManager1 = new GridLayoutManager(activity,3);
        rvViewPreview.setLayoutManager(gridLayoutManager1);
        summarypreview();
        tvViewPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ViewSummaryActivity.class);
                startActivity(intent);
            }
        });







        return root;

    }

    private void summarypreview() {

        ArrayList<Summarymodel> summarymodels = new ArrayList<>();

        Summarymodel rings1 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings2 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings3 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");



        summarymodels.add(rings1);
        summarymodels.add(rings2);
        summarymodels.add(rings3);



        summaryAdapter = new SummaryAdapter(summarymodels, getActivity());
        rvViewPreview.setAdapter(summaryAdapter);

    }

    private void summary() {



        ArrayList<Summarymodel> summarymodels = new ArrayList<>();

        Summarymodel rings1 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings2 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings3 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");



        summarymodels.add(rings1);
        summarymodels.add(rings2);
        summarymodels.add(rings3);



        summaryAdapter = new SummaryAdapter(summarymodels, getActivity());
        rvToday.setAdapter(summaryAdapter);


    }
}