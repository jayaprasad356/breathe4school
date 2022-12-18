package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.HomeWorkSubjectAdapter;
import com.app.b4s.adapter.PendingHomeWorkAdapter;
import com.app.b4s.model.HomeWorkSubject;
import com.app.b4s.model.PendingHomeWork;

import java.util.ArrayList;


public class PendingFragment extends Fragment {
    RecyclerView rvpending;
    PendingHomeWorkAdapter pendingHomeWorkAdapter;


    public PendingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pending, container, false);
        rvpending = root.findViewById(R.id.rvpending);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvpending.setLayoutManager(gridLayoutManager);
        pending();


        return root;
    }

    private void pending() {


        ArrayList<PendingHomeWork> pendingHomeWorks = new ArrayList<>();

        PendingHomeWork rings1 = new PendingHomeWork("Kannada","Poem","22/12/2022");
        PendingHomeWork rings2 = new PendingHomeWork("Kannada","Poem","22/12/2022");
        PendingHomeWork rings3 = new PendingHomeWork("Kannada","Poem","22/12/2022");



        pendingHomeWorks.add(rings1);
        pendingHomeWorks.add(rings2);
        pendingHomeWorks.add(rings3);



        pendingHomeWorkAdapter = new PendingHomeWorkAdapter(pendingHomeWorks, getActivity());
        rvpending.setAdapter(pendingHomeWorkAdapter);


    }
}