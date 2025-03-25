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
import com.example.c196mobiledevelopment.entities.Assessment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AssessmentList extends AppCompatActivity {
    private Repository repository;

    /**
     * Displays the Assessment List activity with the appropriate xml tags.
     * Populates the assessment RecyclerView with all the assessments from the database.
     * when the add floating action button is clicked, it will lead the user to course list to add an assessment to.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assessment_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentList.this, CourseList.class);
                startActivity(intent);
            }
        });

        // Display all assessments
        RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> assessmentList;
        assessmentList = new ArrayList<>(repository.getmAllAssessments());
        assessmentAdapter.setAssessments(assessmentList);
    }

    /**
     * Displays the assessment RecyclerViewer with the appropriate data from the database in case it there was a change.
     */
    protected void onResume() {
        try {
            super.onResume();
            // Display all assessments
            RecyclerView recyclerView = findViewById(R.id.assessmentListRecyclerView);
            repository = new Repository(getApplication());
            final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
            recyclerView.setAdapter(assessmentAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Assessment> assessmentList;
            assessmentList = new ArrayList<>(repository.getmAllAssessments());
            assessmentAdapter.setAssessments(assessmentList);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}