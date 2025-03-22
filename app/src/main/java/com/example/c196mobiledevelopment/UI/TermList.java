package com.example.c196mobiledevelopment.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.database.Repository;
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
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(TermList.this, TermDetails.class);
            startActivity(intent);
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());

        // Display Terms
        try {
            List<Term> allTerms = repository.getmAllTerms();
            final TermAdapter termAdapter = new TermAdapter(this);
            recyclerView.setAdapter(termAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            termAdapter.setTerms(allTerms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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


}