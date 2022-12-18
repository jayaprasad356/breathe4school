package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.CompletedHomeWorkAdapter;
import com.app.b4s.adapter.PendingHomeWorkAdapter;
import com.app.b4s.model.ComplededHomeWork;
import com.app.b4s.model.PendingHomeWork;

import java.util.ArrayList;


public class CompletedFragment extends Fragment {

    RecyclerView rvCompleted;
    CompletedHomeWorkAdapter completedHomeWorkAdapter;


    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_completed, container, false);


        rvCompleted = root.findViewById(R.id.rvCompleted);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvCompleted.setLayoutManager(gridLayoutManager);
        Completed();


        return root;

    }

    private void Completed() {
        ArrayList<ComplededHomeWork> complededHomeWorks = new ArrayList<>();

        ComplededHomeWork rings1 = new ComplededHomeWork("Kannada","Poem","22/12/2022");
        ComplededHomeWork rings2 = new ComplededHomeWork("Kannada","Poem","22/12/2022");
        ComplededHomeWork rings3 = new ComplededHomeWork("Kannada","Poem","22/12/2022");



        complededHomeWorks.add(rings1);
        complededHomeWorks.add(rings2);
        complededHomeWorks.add(rings3);



        completedHomeWorkAdapter = new CompletedHomeWorkAdapter(complededHomeWorks, getActivity());
        rvCompleted.setAdapter(completedHomeWorkAdapter);




    }
}