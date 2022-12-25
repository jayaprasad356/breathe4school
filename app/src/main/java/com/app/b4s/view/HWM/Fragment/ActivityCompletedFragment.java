package com.app.b4s.view.HWM.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.CompletedActivityAdapter;
import com.app.b4s.model.CompletedActivitymodel;

import java.util.ArrayList;


public class ActivityCompletedFragment extends Fragment {

    RecyclerView rvCompleted;
    CompletedActivityAdapter completedActivityAdapter;



    public ActivityCompletedFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_activity_completed, container, false);


        rvCompleted = root.findViewById(R.id.rvCompleted);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvCompleted.setLayoutManager(gridLayoutManager);
        completed();


        return root;
    }

    private void completed() {


        ArrayList<CompletedActivitymodel> completedActivitymodels = new ArrayList<>();

        CompletedActivitymodel rings1 = new CompletedActivitymodel("Kannada","Poem2","Today | 10:30 AM");
        CompletedActivitymodel rings2 = new CompletedActivitymodel("Kannada","Poem","Today | 10:30 AM");
        CompletedActivitymodel rings3 = new CompletedActivitymodel("Kannada","Poem","Today | 10:30 AM");



        completedActivitymodels.add(rings1);
        completedActivitymodels.add(rings2);
        completedActivitymodels.add(rings3);



        completedActivityAdapter = new CompletedActivityAdapter(completedActivitymodels, getActivity());
        rvCompleted.setAdapter(completedActivityAdapter);
    }
}