package com.app.b4s.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.OnHomeWordData;
import com.app.b4s.preferences.Session;
import com.app.b4s.utilities.ApiConfig;
import com.app.b4s.utilities.Constant;
import com.app.b4s.view.HWM.Activity.QuestionsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.ViewHolder> {
    Activity activity;
    ArrayList<OnHomeWordData> onHomeWordData;
    Session session;
    String type;

    public HomeWorkAdapter(String type, ArrayList<OnHomeWordData> onReviewHomeWorks, Activity activity) {
        this.onHomeWordData = onReviewHomeWorks;
        this.activity = activity;
        this.type = type;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.layout_pending, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvSubName.setText(onHomeWordData.get(position).getName());
        holder.tvDate.setText(onHomeWordData.get(position).getDeadline());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHomeWordDetails();
            }
        });


    }

    private void loadHomeWordDetails() {

        session = new Session(activity);
        String url="";
        // url = Constant.FILTER_BY_STUDENT_ID + session.getData(Constant.STUDENT_ID)+"/"+"completed";
        if (type.equals(Constant.COMPLETED))
            url = Constant.HomeWork_Url + session.getData(Constant.HOMEWORD_ID) + "/studentResult/student" + "/" + session.getData(Constant.STUDENT_ID) + "/get";
        if (type.equals(Constant.REVIEW))
            url = Constant.HomeWork_Url + session.getData(Constant.HOMEWORD_ID) + "/studentResponse/student" + "/" + session.getData(Constant.STUDENT_ID) + "/get";
        if (type.equals(Constant.PENDING))
            url = Constant.HomeWork_Url + "get" + "/" + session.getData(Constant.HOMEWORD_ID);
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.STATUS)) {
                        JSONObject jsonArray = jsonObject.getJSONObject(Constant.DATA);
                        session.setData(Constant.QUESTION_DATA, jsonArray.toString());
                        Intent intent = new Intent(activity, QuestionsActivity.class);
                        intent.putExtra(Constant.TYPE,type);
                        activity.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, url, params, true, 0);

    }


    @Override
    public int getItemCount() {
        return onHomeWordData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubName, tvTopic, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubName = itemView.findViewById(R.id.tvSubName);
            this.tvTopic = itemView.findViewById(R.id.tvSubName);
            this.tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
