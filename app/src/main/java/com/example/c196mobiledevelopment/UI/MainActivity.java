package com.example.c196mobiledevelopment.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.c196mobiledevelopment.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Instructor instructor = new Instructor(1, "InstructorTest", "instructoremail@email.com", "instructorPHONE");
//        Repository repository = new Repository(getApplication());
//        repository.insertInstructor(instructor);
//        Instructor instructor = new Instructor(2, "Matthew", "matthewmaths@email.com", "matthewPhone");
//        Repository repository = new Repository(getApplication());
//        repository.insertInstructor(instructor);
//        Assessment assessment1 = new Assessment(1,"Assessment 1", "1/1/2024", "6/1/2024", "Objective Assessment", 1);
//        Assessment assessment2 = new Assessment(2,"Assessment 2", "1/1/2024", "6/1/2024", "Performance Assessment", 1);
//        repository.updateAssessment(assessment1);
//        repository.updateAssessment(assessment2);


        Button tListButton = findViewById(R.id.termListBtn);
        tListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TermList.class);
            intent.putExtra("test", "Information Sent");
            startActivity(intent);
        });

        Button cListButton = findViewById(R.id.courseListBtn);
        cListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CourseList.class);
            startActivity(intent);
        });

        Button aListButton = findViewById(R.id.assessmentListBtn);
        aListButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AssessmentList.class);
            startActivity(intent);
        });
    }
}