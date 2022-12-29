package com.app.b4s.adapter.DcmAdapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.b4s.R;
import com.app.b4s.model.Dcm.Summarymodel;
import com.app.b4s.view.DCM.Activity.SummaryDetailsActivity;
import com.app.b4s.view.DCM.Activity.ViewSummaryActivity;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {
    Activity activity;
    ArrayList<Summarymodel> summarymodels;

    public SummaryAdapter(ArrayList<Summarymodel> summarymodels, Activity activity) {
        this.summarymodels = summarymodels;
        this.activity = activity;
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
        holder.tvSubName.setText(summarymodels.get(position).getSub_name());
        holder.tvDescription.setText(summarymodels.get(position).getTopic());
        holder.tvDate.setText(summarymodels.get(position).getTime());


        if (activity instanceof ViewSummaryActivity){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, SummaryDetailsActivity.class);
                    activity.startActivity(intent);
                }
            });


        }
        else {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity,ViewSummaryActivity.class);
                    activity.startActivity(intent);
                }
            });


        }



    }


    @Override
    public int getItemCount() {
        return summarymodels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSubName, tvDescription,tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubName = itemView.findViewById(R.id.tvSubName);
            this.tvDescription = itemView.findViewById(R.id.tvDescription);
            this.tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
