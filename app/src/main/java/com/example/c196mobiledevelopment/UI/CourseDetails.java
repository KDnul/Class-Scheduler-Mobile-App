package com.example.c196mobiledevelopment.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.database.Repository;
import com.example.c196mobiledevelopment.entities.Assessment;
import com.example.c196mobiledevelopment.entities.Course;
import com.example.c196mobiledevelopment.entities.Instructor;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {
    private Repository repository;
    EditText courseTitle;
    EditText courseStart;
    EditText courseEnd;
    EditText courseNote;
    String title;
    String startDate;
    String endDate;
    String note;
    int courseId;
    int numAssessments;
    Course course;
    Course currentCourse;
    int termId;
    int instructorId;
    String courseStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());

        // Editing Details of a Course
        courseTitle = findViewById(R.id.courseTitleEditText);
        courseStart = findViewById(R.id.courseStartEditText);
        courseEnd = findViewById(R.id.courseEndEditText);
        courseNote = findViewById(R.id.courseNoteEditText);
        courseStatus = getIntent().getStringExtra("status");
        courseId = getIntent().getIntExtra("courseId", -1);
        termId = getIntent().getIntExtra("termId", -1);
        instructorId = getIntent().getIntExtra("instructorId", -1);

        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        note = getIntent().getStringExtra("notes");
        courseTitle.setText(title);
        courseStart.setText(startDate);
        courseEnd.setText(endDate);
        courseNote.setText(note);

        // Course Instructor Spinner
        ArrayList<Instructor> instructorArrayList = new ArrayList<>();
        instructorArrayList.addAll(repository.getmAllInstructors());

        ArrayList<String> instructorNameList = new ArrayList<>();
        for(Instructor instructor:instructorArrayList) {
            instructorNameList.add(instructor.getName());
        }
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,instructorNameList);
        Spinner iSpinner = findViewById(R.id.courseInstructorSpinner);
        iSpinner.setAdapter(instructorAdapter);
        iSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instructorId = instructorArrayList.get(position).getInstructorId();
                Instructor currentInstructor = instructorArrayList.get(position);
                // Instructor Add Spinner
                Button addInstructorBtn = (Button) findViewById(R.id.addInstructorBtn);
                addInstructorBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(CourseDetails.this, InstructorDetails.class);
                    intent.putExtra("instructorId", currentInstructor.getInstructorId());
                    intent.putExtra("name",currentInstructor.getName());
                    intent.putExtra("email", currentInstructor.getEmail());
                    intent.putExtra("phoneNumber", currentInstructor.getPhone());
                    startActivity(intent);
                });
                Log.d("Instructor", "INSTRUCTOR ID IS: " + instructorId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for(Instructor instructor:instructorArrayList) {
            if(instructorId == instructor.getInstructorId()) {
                iSpinner.setSelection(getIndex(iSpinner, instructor.getName()));
            }
        }


        // Course Status Spinner
        Spinner satSpinner = findViewById(R.id.courseStatusSpinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Completed");
        arrayList.add("Plan to Take");
        arrayList.add("Dropped");
        arrayList.add("In Progress");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        satSpinner.setAdapter(adapter);
        // Set Spinner to the status of the course
        satSpinner.setSelection(getIndex(satSpinner, courseStatus));


        // Assessment Lists in Course Details
        RecyclerView recyclerView = findViewById(R.id.courseDetailAssessmentReyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseId() == courseId) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);

    }

    private int getIndex(Spinner spinner, String courseStatus) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(courseStatus)) {
                index = i;
            }
        }
        return index;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        }
        // Save details of the course and make it
        if (item.getItemId() == R.id.saveCourse) {
            if (termId == -1) {
                Toast.makeText(CourseDetails.this, "Please make a term first", Toast.LENGTH_LONG).show();
            } else {
                Log.d("click", "Save course has been clicked");
                Course course;
                Spinner cSpinner = findViewById(R.id.courseStatusSpinner);
                courseStatus = cSpinner.getSelectedItem().toString();
//                Spinner iSpinner = findViewById(R.id.courseInstructorSpinner);
                Log.d("courseTag", "Course id before if course id is -1 statement: " + courseId);
                if (courseId == -1) {
                    try {
                        if (repository.getmAllCourses().isEmpty()){
                            courseId = 1;
                            course = new Course(courseId, courseTitle.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus, courseNote.getText().toString(), termId, instructorId);
                            repository.insertCourse(course);
                            this.finish();
                        }

                        else {
                            Log.d("courseTag", "Course id is before repository: " + courseId);
                            courseId = repository.getmAllCourses().get(repository.getmAllCourses().size() - 1).getCourseId() + 1;
                            course = new Course(courseId, courseTitle.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus, courseNote.getText().toString(), termId, instructorId);
                            repository.insertCourse(course);
                            this.finish();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                } else {
//            Log.d("courseTag", "CourseId is after repository: " + courseId);
//            Log.d("courseTag", "CourseStatus on UPDATE ELSE STATEMENT: " + courseStatus);
//            Log.d("courseTag", "CourseTITLE is: " + courseTitle.getText().toString() + " after repository");
//            Log.d("courseTag", "CourseSTART is: " + courseStart.getText().toString() + " after repository");
//            Log.d("courseTag", "CourseEND is: " + courseEnd.getText().toString() + " after repository");
//            Log.d("courseTag", "CourseNOTE is: " + courseNote.getText().toString() + " after repository");
//            Log.d("courseTag", "CourseTERMID is: " + termId + " after repository");
                    course = new Course(courseId, courseTitle.getText().toString(), courseStart.getText().toString(), courseEnd.getText().toString(), courseStatus, courseNote.getText().toString(), termId, instructorId);
                    repository.updateCourse(course);
                    this.finish();
                }
            }
        }

        // Share details of the course
        if (item.getItemId() == R.id.shareNotes) {
            if (termId == -1) {
                Toast.makeText(CourseDetails.this, "Please make a term first", Toast.LENGTH_LONG).show();
            } else {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, courseNote.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, courseNote.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            }
        }


        // Delete current Course
        if (item.getItemId() == R.id.deleteCourse) {
            try {
                for (Course c : repository.getmAllCourses()) {
                    if (c.getCourseId() == courseId) currentCourse = c;
                }
                numAssessments = 0;
                for (Assessment a : repository.getmAllAssessments()) {
                    if (a.getCourseId() == courseId) ++numAssessments;
                }
                if (numAssessments == 0) {
                    repository.deleteCourse(currentCourse);
                    Toast.makeText(CourseDetails.this, currentCourse.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CourseDetails.this, "Can't delete a course with assessments in it", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Course Instructor Spinner
        ArrayList<Instructor> instructorArrayList = new ArrayList<>();
        instructorArrayList.addAll(repository.getmAllInstructors());

        ArrayList<String> instructorNameList = new ArrayList<>();
        for(Instructor instructor:instructorArrayList) {
            instructorNameList.add(instructor.getName());
        }
        ArrayAdapter<String> instructorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,instructorNameList);
        Spinner iSpinner = findViewById(R.id.courseInstructorSpinner);
        iSpinner.setAdapter(instructorAdapter);
        iSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                instructorId = instructorArrayList.get(position).getInstructorId();
                Instructor currentInstructor = instructorArrayList.get(position);
                // Instructor Add Spinner
                Button addInstructorBtn = (Button) findViewById(R.id.addInstructorBtn);
                addInstructorBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(CourseDetails.this, InstructorDetails.class);
                    intent.putExtra("instructorId", currentInstructor.getInstructorId());
                    intent.putExtra("name",currentInstructor.getName());
                    intent.putExtra("email", currentInstructor.getEmail());
                    intent.putExtra("phoneNumber", currentInstructor.getPhone());
                    startActivity(intent);
                });
                Log.d("Instructor", "INSTRUCTOR ID IS: " + instructorId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        for(Instructor instructor:instructorArrayList) {
            if(instructorId == instructor.getInstructorId()) {
                iSpinner.setSelection(getIndex(iSpinner, instructor.getName()));
            }
        }

        // Assessment Lists in Course Details
        RecyclerView recyclerView = findViewById(R.id.courseDetailAssessmentReyclerView);
        repository = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repository.getmAllAssessments()) {
            if (a.getCourseId() == courseId) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessments(filteredAssessments);
    }
}