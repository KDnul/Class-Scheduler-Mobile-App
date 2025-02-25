package com.example.c196mobiledevelopment.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.c196mobiledevelopment.entities.CourseStatus;
import com.example.c196mobiledevelopment.entities.Term;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_term_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, TermDetailsActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        try {
            List<Term> allTerms = repository.getmAllTerms();
            final TermAdapter termAdapter = new TermAdapter(this);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(allTerms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // System.out.println(getIntent().getStringExtra("test"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            List<Term> allTerms = repository.getmAllTerms();
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            final TermAdapter termAdapter = new TermAdapter(this);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(allTerms);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mysample) {
            Repository repository = new Repository(getApplication());
            Term term = new Term(0, "Spring", "1/1/24", "6/5/24");
            Term term1 = new Term(0, "Fall", "7/1/24", "9/5/24");
            Course course = new Course(0, "Math", "1/1/24", "6/1/24", CourseStatus.PLAN_TO_TAKE, "test note", term.getTermId() + 1);
            repository.insertCourse(course);
            repository.insertTerm(term);
            repository.insertTerm(term1);
            // Toast.makeText(TermList.this, "put in sample data", Toast.LENGTH_LONG).show();
            try {
                List<Term> allTerms= repository.getmAllTerms();
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                final TermAdapter termadapter = new TermAdapter(this);
                recyclerView.setAdapter(termadapter);
                termadapter.setTerms(allTerms);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

}