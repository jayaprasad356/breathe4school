package com.app.b4s.view.HWM.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.b4s.R;
import com.app.b4s.adapter.PendingActivityAdapter;
import com.app.b4s.model.PendingActivity;

import java.util.ArrayList;


public class ActivityPendingFragment extends Fragment {


    RecyclerView rvpending;
    PendingActivityAdapter pendingActivityAdapter;





    public ActivityPendingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_activity_pending, container, false);



        rvpending = root.findViewById(R.id.rvpending);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvpending.setLayoutManager(gridLayoutManager);
        pending();



        return root;
    }

    private void pending() {

        ArrayList<PendingActivity> pendingActivities = new ArrayList<>();

        PendingActivity rings1 = new PendingActivity("Kannada","Poem","Today | 10:30 AM");
        PendingActivity rings2 = new PendingActivity("Kannada","Poem","Today | 10:30 AM");
        PendingActivity rings3 = new PendingActivity("Kannada","Poem","Today | 10:30 AM");



        pendingActivities.add(rings1);
        pendingActivities.add(rings2);
        pendingActivities.add(rings3);



        pendingActivityAdapter = new PendingActivityAdapter(pendingActivities, getActivity());
        rvpending.setAdapter(pendingActivityAdapter);
    }
}