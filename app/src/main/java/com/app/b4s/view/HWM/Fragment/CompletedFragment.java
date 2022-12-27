package com.app.b4s.view.HWM.Fragment;

import android.os.Bundle;

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
import com.app.b4s.model.OnHomeWordData;
import com.app.b4s.preferences.Session;
import com.app.b4s.view.DCM.FilterHomeWorkListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CompletedFragment extends Fragment implements FilterHomeWorkListener {

    RecyclerView rvCompleted;
    Session session;
    IFilterHomeWorkController filterHomeWorkController;
    HomeWorkAdapter onReviewHomeWorkAdapter;



    public CompletedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_completed, container, false);


        rvCompleted = root.findViewById(R.id.rvCompleted);
        filterHomeWorkController = new FilterHomeWorkController(this);
        filterHomeWorkController.getFilterHomeWork("completed",getActivity());


        return root;

    }



    @Override
    public void onSuccess(JSONArray jsonArray) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        rvCompleted.setLayoutManager(gridLayoutManager);
        Gson g = new Gson();
        ArrayList<OnHomeWordData>  filterData= new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = null;
            try {
                jsonObject1 = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jsonObject1 != null) {
                OnHomeWordData group = g.fromJson(jsonObject1.toString(), OnHomeWordData.class);
                filterData.add(group);
            } else {
                break;
            }
            onReviewHomeWorkAdapter = new HomeWorkAdapter("pending", filterData, getActivity());
            rvCompleted.setAdapter(onReviewHomeWorkAdapter);

        }
    }
}