package com.app.b4s.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.OnReviewHomeWorkAdapter;
import com.app.b4s.adapter.PendingHomeWorkAdapter;
import com.app.b4s.model.OnReviewHomeWork;
import com.app.b4s.model.PendingHomeWork;

import java.util.ArrayList;

public class OnReviewFragment extends Fragment {

    RecyclerView rvReview;
    OnReviewHomeWorkAdapter onReviewHomeWorkAdapter;




    public OnReviewFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_on_review, container, false);

        rvReview = root.findViewById(R.id.rvReview);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvReview.setLayoutManager(gridLayoutManager);
        Review();


        return root;


    }

    private void Review() {


        ArrayList<OnReviewHomeWork> onReviewHomeWorks = new ArrayList<>();

        OnReviewHomeWork rings1 = new OnReviewHomeWork("Kannada","Poem","22/12/2022");
        OnReviewHomeWork rings2 = new OnReviewHomeWork("Kannada","Poem","22/12/2022");
        OnReviewHomeWork rings3 = new OnReviewHomeWork("Kannada","Poem","22/12/2022");



        onReviewHomeWorks.add(rings1);
        onReviewHomeWorks.add(rings2);
        onReviewHomeWorks.add(rings3);



        onReviewHomeWorkAdapter = new OnReviewHomeWorkAdapter(onReviewHomeWorks, getActivity());
        rvReview.setAdapter(onReviewHomeWorkAdapter);

    }


}