package com.example.c196mobiledevelopment.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.entities.Assessment;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewholder>{
    private List<Assessment> mAssessments;
    private Context context;
    private LayoutInflater mInflater;

    class AssessmentViewholder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitleView;
        private final TextView assessmentDateView;
        private final TextView assessmentTypeView;

        private AssessmentViewholder(View itemView) {
            super(itemView);
            assessmentTitleView = itemView.findViewById(R.id.assessmentTitleView);
            assessmentDateView = itemView.findViewById(R.id.assessmentDateView);
            assessmentTypeView = itemView.findViewById(R.id.assessmentTypeView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Assessment current = mAssessments.get(position);
                Intent intent = new Intent(context, AssessmentDetails.class);
                intent.putExtra("assessmentId", current.getAssessmentId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("date", current.getDate());
                intent.putExtra("type", current.getType());
                intent.putExtra("courseId", current.getCourseId());
                context.startActivity(intent);
            });


        }


    }
    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent,false);
        return new AssessmentViewholder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewholder holder, int position) {
        if(mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String title = current.getTitle();
            String date = current.getDate();
            String type = current.getType();
            holder.assessmentTitleView.setText(title);
            holder.assessmentDateView.setText(date);
            holder.assessmentTypeView.setText(type);
        } else {
            holder.assessmentTitleView.setText("No Assessments");
        }
    }

    @Override
    public int getItemCount() {
        if(mAssessments != null) {
            return mAssessments.size();
        }else {
            return 0;
        }
    }
    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
