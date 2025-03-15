package com.example.c196mobiledevelopment.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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
import com.example.c196mobiledevelopment.entities.Course;
import com.example.c196mobiledevelopment.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetailsActivity extends AppCompatActivity {
    private Repository repository;
    EditText editTitle;
    EditText editStartDate;
    EditText editEndDate;
    String title;
    String startDate;
    String endDate;
    int termId;
    int numCourses;
    Term term;
    Term currentTerm;

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

        // See Course Details when clicking a term
        FloatingActionButton fab = findViewById(R.id.courseAddFAB);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermDetailsActivity.this, CourseDetails.class);
            intent.putExtra("termId", termId);
            startActivity(intent);
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
        try {
            for (Course c : repository.getmAllCourses()) {
                if (c.getTermId() == termId) filteredCourses.add(c);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
                    if (repository.getmAllTerms().isEmpty()) {
                        termId = 1;
                        term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                        repository.insertTerm(term);
                    }
                    else {
                        termId = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermId() + 1;
                        term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                        repository.insertTerm(term);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
//            Log.d("termTag", "TermId is + " + termId);
//            Log.d("termTag", "TermTITLE is + " + editTitle);
//            Log.d("termTag", "TermSTART is + " + editStartDate);
//            Log.d("termTag", "TermEND is + " + editEndDate);
            term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
            repository.updateTerm(term);
        }

        // Delete current Term
        if (item.getItemId() == R.id.termDelete) {
            try {
                for (Term t : repository.getmAllTerms()) {
                    if(t.getTermId() == termId) currentTerm = t;
                }
                numCourses = 0;
                for (Course c : repository.getmAllCourses()) {
                    if (c.getTermId() == termId) ++numCourses;
                }
                if (numCourses == 0) {
                    repository.deleteTerm(currentTerm);
                    Toast.makeText(TermDetailsActivity.this, currentTerm.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TermDetailsActivity.this, "Can't delete a term with courses in it", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.finish();
        }

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    protected void onResume() {
        try {
            super.onResume();
            // Display courses in Term Details
            RecyclerView recyclerView = findViewById(R.id.termRecyclerview);
            repository = new Repository(getApplication());
            final CourseAdapter courseAdapter = new CourseAdapter(this);
            recyclerView.setAdapter(courseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Course> filteredCourses = new ArrayList<>();
            try {
                for (Course c : repository.getmAllCourses()) {
                    if (c.getTermId() == termId) filteredCourses.add(c);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            courseAdapter.setCourses(filteredCourses);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}