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
import com.example.c196mobiledevelopment.entities.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<Course> mCourses;
    private Context context;
    private LayoutInflater mInflater;

    /**
     * Class for the course ViewHolder.
     */
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseDateView;
        private final TextView courseStatusView;

        /**
         * @param itemView Grabs the necessary information from a specific course class when clicked.
         *                 When an course is clicked, it grabs all the information about that course and puts that data into the
         *                 CourseDetails.class for it to be added/edited.
         */
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseNameView);
            courseDateView = itemView.findViewById(R.id.courseDateView);
            courseStatusView = itemView.findViewById(R.id.courseStatusView);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Course current = mCourses.get(position);
                Intent intent = new Intent(context, CourseDetails.class);
                intent.putExtra("courseId", current.getCourseId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("startDate", current.getStartDate());
                intent.putExtra("endDate", current.getEndDate());
                intent.putExtra("termId", current.getTermId());
                intent.putExtra("status", current.getStatus());
                intent.putExtra("notes", current.getNote());
                intent.putExtra("instructorId", current.getInstructorId());
                context.startActivity(intent);
            });
        }
    }

    /**
     * @param context Course class that is clicked.
     *                Grabs data from the course table from the database.
     */
    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * @param parent   Parent of the xml.
     * @param viewType type of view.
     *                 Populates the course RecyclerView with the necessary data.
     */
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    /**
     * @param holder   Course holds the data of the selected course.
     * @param position Currently selected course class.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String title = current.getTitle();
            String date = current.getStartDate() + " to " + current.getEndDate();
            String status = current.getStatus();
            holder.courseItemView.setText(title);
            holder.courseDateView.setText(date);
            holder.courseStatusView.setText(status);
        } else {
            holder.courseItemView.setText("No Courses");
        }
    }

    /**
     * @return the number of courses in the database if there any.
     * return 0 if there are no courses in the database.
     */
    @Override
    public int getItemCount() {
        if (mCourses != null) {
            return mCourses.size();
        } else {
            return 0;
        }
    }

    /**
     * @param courses course class.
     *                A list of courses for the ViewHolder to display.
     */
    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

}
