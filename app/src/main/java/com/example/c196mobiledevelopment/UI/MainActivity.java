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

    /**
     * Main activity of the program. Displays a list of buttons for the user to click on.
     */
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