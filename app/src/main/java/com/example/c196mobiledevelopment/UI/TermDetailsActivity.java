package com.example.c196mobiledevelopment.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
import com.example.c196mobiledevelopment.entities.Course;
import com.example.c196mobiledevelopment.entities.Term;

import java.util.ArrayList;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    Repository repository;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    String title;
    String startDate;
    String endDate;
    int termId;
    Term term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Editing details of term
        editTitle = findViewById(R.id.termTitle);
        editStartDate = findViewById(R.id.startText);
        editEndDate = findViewById(R.id.endText);
        termId = getIntent().getIntExtra("id", -1);

        title = getIntent().getStringExtra("title");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        editTitle.setText(title);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);


        // Display courses in Term Details
        RecyclerView recyclerView = findViewById(R.id.termRecyclerview);
        repository = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repository.getmAllCourses()) {
            if (c.getTermId() == termId) filteredCourses.add(c);
        }
        courseAdapter.setCourses(filteredCourses);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.termSave) {
            Term term;
            if (termId == -1) {
                try {
                    if (repository.getmAllTerms().isEmpty()) termId = 1;
                    else {
                        termId = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermId() + 1;
                        term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                        repository.insertTerm(term);
                        this.finish();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
            repository.updateTerm(term);
            this.finish();
        }
        return true;
    }

}