package com.app.b4s.view.DCM.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.app.b4s.R;
import com.app.b4s.adapter.DcmAdapter.SummaryAdapter;
import com.app.b4s.model.Dcm.Summarymodel;

import java.util.ArrayList;

public class ViewSummaryActivity extends AppCompatActivity {

    SummaryAdapter summaryAdapter;
    RecyclerView rvSummary;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_summary);
        activity = ViewSummaryActivity.this;


        rvSummary = findViewById(R.id.rvSummary);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,3);
        rvSummary.setLayoutManager(gridLayoutManager);
        summary();




    }

    private void summary() {



        ArrayList<Summarymodel> summarymodels = new ArrayList<>();


        Summarymodel rings1 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings2 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");
        Summarymodel rings3 = new Summarymodel("Kannada","NAVELLARU ONDE POEM","10:30 am");


        summarymodels.add(rings1);
        summarymodels.add(rings2);
        summarymodels.add(rings3);

        summaryAdapter = new SummaryAdapter(summarymodels, activity);
        rvSummary.setAdapter(summaryAdapter);


    }
}