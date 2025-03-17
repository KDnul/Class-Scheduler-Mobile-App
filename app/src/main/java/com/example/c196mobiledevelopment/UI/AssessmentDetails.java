package com.example.c196mobiledevelopment.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.database.Repository;
import com.example.c196mobiledevelopment.entities.Assessment;

import java.util.ArrayList;

public class AssessmentDetails extends AppCompatActivity {
    private Repository repository;
    EditText assessmentTitle;
    EditText assessmentDate;
    Spinner spinner;
    String title;
    String date;
    String endDate;
    String assessmentType;
    int assessmentId;
    int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_assessment_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());

        // Edit Details of an Assessment
        assessmentTitle = findViewById(R.id.assessmentTitleEditText);
        assessmentDate = findViewById(R.id.assessmentDateEditText);
        assessmentType = getIntent().getStringExtra("type");
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        endDate = getIntent().getStringExtra("endDate");
        assessmentTitle.setText(title);
        assessmentDate.setText(date);

        // Assessment Type Spinner
        spinner = findViewById(R.id.assessmentTypeSpinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Objective Assessment");
        arrayList.add("Performance Assessment");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
        // Set Spinner to the type of the assessment
        spinner.setSelection(getIndex(spinner, assessmentType));


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
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        // Save details of the assessment and make it
        if (item.getItemId() == R.id.assessmentSave) {
            if(courseId == -1) {
                Toast.makeText(AssessmentDetails.this, "Please make a course first", Toast.LENGTH_LONG).show();
            } else {
                Assessment assessment;
                Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
                assessmentType = spinner.getSelectedItem().toString();
                if (assessmentId == -1) {
                    try {
                        if (repository.getmAllAssessments().isEmpty()) {
                            assessmentId = 1;
                            assessment = new Assessment(assessmentId, assessmentTitle.getText().toString(), assessmentDate.getText().toString(), assessmentType,courseId);
                            repository.insertAssessment(assessment);
                            Toast.makeText(AssessmentDetails.this, assessmentTitle.getText().toString() + " was added to Assessment List", Toast.LENGTH_LONG).show();
                            this.finish();
                        } else {
                            assessmentId = repository.getmAllAssessments().get(repository.getmAllAssessments().size() - 1).getAssessmentId() + 1;
                            assessment = new Assessment(assessmentId, assessmentTitle.getText().toString(), assessmentDate.getText().toString(), assessmentType,courseId);                            repository.insertAssessment(assessment);
                            Toast.makeText(AssessmentDetails.this, assessmentTitle.getText().toString() + " was added to Assessment List", Toast.LENGTH_LONG).show();
                            this.finish();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                } else {
                    assessment = new Assessment(assessmentId, assessmentTitle.getText().toString(), assessmentDate.getText().toString(), assessmentType,courseId);
                    repository.updateAssessment(assessment);
                    Toast.makeText(AssessmentDetails.this, assessmentTitle.getText().toString() + " was updated", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }

        if(item.getItemId() == R.id.assessmentDelete) {
            for (Assessment a : repository.getmAllAssessments()) {
                if(a.getAssessmentId() == assessmentId) {
                    repository.deleteAssessment(a);
                    Toast.makeText(AssessmentDetails.this, a.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }


}