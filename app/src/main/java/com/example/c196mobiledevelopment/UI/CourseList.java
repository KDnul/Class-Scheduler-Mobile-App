package com.example.c196mobiledevelopment.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.database.Repository;
import com.example.c196mobiledevelopment.entities.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseList extends AppCompatActivity {
    private Repository repository;
    int termId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseList.this, TermList.class);
                startActivity(intent);
            }
        });

        // Display All courses
        RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> courseList;
        try {
            courseList = new ArrayList<>(repository.getmAllCourses());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        courseAdapter.setCourses(courseList);

    }

    protected void onResume() {
        try {
            super.onResume();
            // Display All courses
            RecyclerView recyclerView = findViewById(R.id.courseListRecyclerView);
            repository = new Repository(getApplication());
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> courseList;
            courseList = new ArrayList<>(repository.getmAllCourses());
            courseAdapter.setCourses(courseList);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}