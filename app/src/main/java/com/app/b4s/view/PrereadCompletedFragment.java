package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.CompletedPrereadAdapter;
import com.app.b4s.model.CompletedPreread;

import java.util.ArrayList;


public class PrereadCompletedFragment extends Fragment {


    RecyclerView rvCompleted;
    CompletedPrereadAdapter completedPrereadAdapter;


    public PrereadCompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_preread_completed, container, false);


        rvCompleted = root.findViewById(R.id.rvCompleted);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvCompleted.setLayoutManager(gridLayoutManager);
        Completed();


        return root;

    }

    private void Completed() {


        ArrayList<CompletedPreread> completedPrereads = new ArrayList<>();

        CompletedPreread rings1 = new CompletedPreread("Kannada","Poem","Today | 10:30 AM");
        CompletedPreread rings2 = new CompletedPreread("Kannada","Poem","Today | 10:30 AM");
        CompletedPreread rings3 = new CompletedPreread("Kannada","Poem","Today | 10:30 AM");



        completedPrereads.add(rings1);
        completedPrereads.add(rings2);
        completedPrereads.add(rings3);



        completedPrereadAdapter = new CompletedPrereadAdapter(completedPrereads, getActivity());
        rvCompleted.setAdapter(completedPrereadAdapter);
    }
}