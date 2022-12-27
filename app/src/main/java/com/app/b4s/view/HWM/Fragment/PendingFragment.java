package com.app.b4s.view.HWM.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.adapter.HomeWorkAdapter;
import com.app.b4s.controller.FilterHomeWorkController;
import com.app.b4s.controller.IFilterHomeWorkController;
import com.app.b4s.model.OnHomeWordData;
import com.app.b4s.view.DCM.FilterHomeWorkListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PendingFragment extends Fragment implements FilterHomeWorkListener {
    RecyclerView rvpending;
    IFilterHomeWorkController filterHomeWorkController;
    HomeWorkAdapter homeWorkAdapter;


    public PendingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pending, container, false);
        rvpending = root.findViewById(R.id.rvpending);
        filterHomeWorkController = new FilterHomeWorkController(this);

        filterHomeWorkController.getFilterHomeWork("pending",getActivity());


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        rvpending.setLayoutManager(gridLayoutManager);
       // pending();


        return root;
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        rvpending.setLayoutManager(gridLayoutManager);
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
            homeWorkAdapter = new HomeWorkAdapter("pending", filterData, getActivity());
            rvpending.setAdapter(homeWorkAdapter);

        }
    }
}