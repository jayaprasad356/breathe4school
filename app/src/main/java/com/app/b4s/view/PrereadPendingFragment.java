package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.PendingHomeWorkAdapter;
import com.app.b4s.adapter.PendingPrereadAdapter;
import com.app.b4s.model.PendingHomeWork;
import com.app.b4s.model.PendingPreread;

import java.util.ArrayList;


public class PrereadPendingFragment extends Fragment {


    RecyclerView rvpending;
    PendingPrereadAdapter pendingPrereadAdapter;


    public PrereadPendingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_preread_pending, container, false);



        rvpending = root.findViewById(R.id.rvpending);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvpending.setLayoutManager(gridLayoutManager);
        pending();



        return root;
    }

    private void pending() {


        ArrayList<PendingPreread> pendingPrereads = new ArrayList<>();

        PendingPreread rings1 = new PendingPreread("Kannada","Poem","Today | 10:30 AM");
        PendingPreread rings2 = new PendingPreread("Kannada","Poem","Today | 10:30 AM");
        PendingPreread rings3 = new PendingPreread("Kannada","Poem","Today | 10:30 AM");



        pendingPrereads.add(rings1);
        pendingPrereads.add(rings2);
        pendingPrereads.add(rings3);



        pendingPrereadAdapter = new PendingPrereadAdapter(pendingPrereads, getActivity());
        rvpending.setAdapter(pendingPrereadAdapter);

    }
}