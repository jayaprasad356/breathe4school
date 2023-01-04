
package com.app.b4s.adapter;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.app.b4s.R;

public class QuestionsCountAdapter extends RecyclerView.Adapter<QuestionsCountAdapter.ViewHolder> {
    Activity activity;
    int count;
    int setBackground;

    public QuestionsCountAdapter(int i, int setBackground, Activity activity) {
        this.activity = activity;

        this.count=i;
        this.setBackground=setBackground;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.questions_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text="Q"+position;
        holder.question.setText(text);
       if (position==setBackground){
           holder.question.setBackground(activity.getDrawable(R.drawable.question_tv_checked));
           holder.question.setTextColor(activity.getColor(R.color.primary));
       }

    }


    @Override
    public int getItemCount() {
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView question;

        public ViewHolder(View itemView) {
            super(itemView);
            this.question = itemView.findViewById(R.id.tvQuestionNo);

        }
    }
}
