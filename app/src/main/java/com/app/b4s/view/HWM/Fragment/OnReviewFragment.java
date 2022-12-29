package com.app.b4s.view.HWM.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.b4s.R;
import com.app.b4s.adapter.HomeWorkAdapter;
import com.app.b4s.controller.FilterHomeWorkController;
import com.app.b4s.controller.IFilterHomeWorkController;
import com.app.b4s.model.OnHomeWorkData;
import com.app.b4s.view.DCM.FilterHomeWorkListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OnReviewFragment extends Fragment implements FilterHomeWorkListener {

    RecyclerView rvReview;
    HomeWorkAdapter onReviewHomeWorkAdapter;
    IFilterHomeWorkController filterHomeWorkController;


    public OnReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_on_review, container, false);

        rvReview = root.findViewById(R.id.rvReview);
        filterHomeWorkController = new FilterHomeWorkController(this);

        return root;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filterHomeWorkController.getFilterHomeWork("review",getActivity());

    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        rvReview.setLayoutManager(gridLayoutManager);
        Gson g = new Gson();
        ArrayList<OnHomeWorkData>  filterData= new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject1 != null) {
                OnHomeWorkData group = g.fromJson(jsonObject1.toString(), OnHomeWorkData.class);
                filterData.add(group);
            } else {
                break;
            }
            onReviewHomeWorkAdapter = new HomeWorkAdapter("pending", filterData, getActivity());
            rvReview.setAdapter(onReviewHomeWorkAdapter);

    }
}
}