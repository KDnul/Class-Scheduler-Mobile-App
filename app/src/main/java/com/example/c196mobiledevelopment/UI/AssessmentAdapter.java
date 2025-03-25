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

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewholder> {
    private List<Assessment> mAssessments;
    private Context context;
    private LayoutInflater mInflater;

    /**
     * Class for the assessment ViewHolder.
     */
    class AssessmentViewholder extends RecyclerView.ViewHolder {
        private final TextView assessmentTitleView;
        private final TextView assessmentDateView;
        private final TextView assessmentTypeView;

        /**
         * @param itemView Grabs the necessary information from a specific assessment class when clicked.
         *                 When an assessment is clicked, it grabs all the information about that assessment and puts that data into the
         *                 AssessmentDetails.class for it to be added/edited.
         */
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

    /**
     * @param context Assessment class that is clicked.
     *                Grabs data from the assessments table from the database.
     */
    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * @param parent   Parent of the xml.
     * @param viewType type of view.
     *                 Populates the assessment RecyclerView with the necessary data.
     */
    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewholder(itemView);
    }

    /**
     * @param holder   Assessment holds the data of the selected assessment.
     * @param position Currently selected assessment class.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewholder holder, int position) {
        if (mAssessments != null) {
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

    /**
     * @return the number of assessments in the database if there any.
     * return 0 if there are no assessments in the database.
     */
    @Override
    public int getItemCount() {
        if (mAssessments != null) {
            return mAssessments.size();
        } else {
            return 0;
        }
    }

    /**
     * @param assessments assessment class.
     *                    A list of assessments for the ViewHolder to display.
     */
    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
