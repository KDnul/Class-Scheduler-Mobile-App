package com.example.c196mobiledevelopment.UI;

import android.os.Bundle;
import android.util.Log;
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

import com.example.c196mobiledevelopment.R;
import com.example.c196mobiledevelopment.database.Repository;
import com.example.c196mobiledevelopment.entities.Instructor;

public class InstructorDetails extends AppCompatActivity {
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhone;
    Repository repository;
    int instructorId;

    String name;
    String email;
    String phone;

    /**
     * Displays the Course Details activity with the appropriate xml tags.
     * Allows user input to construct, edit, or delete an instructor.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_instructor_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());

        // Edit Details of Instructor
        instructorName = findViewById(R.id.instructorNameEditText);
        instructorEmail = findViewById(R.id.instructorEmailEditText);
        instructorPhone = findViewById(R.id.instructorPhoneEditText);
        instructorId = getIntent().getIntExtra("instructorId", -1);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phoneNumber");
        instructorName.setText(name);
        instructorEmail.setText(email);
        instructorPhone.setText(phone);

    }

    /**
     * Creates and inflates the menu items from the menu_instructordetails xml.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_instructordetails, menu);
        return true;
    }

    /**
     * @param item the selected menu item the user clicked on.
     *             If the user clicked on the back menu item, it will return the user to the previous activity.
     *             If the user clicked on the Save menu item, it will grab all the currently displayed user inputs and create a new instructor class or updates
     *             an existing instructor class to the database.
     *             If the user clicked on the Delete menu item, it will delete the currently displayed instructor class from the database.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        }

        // Save Instructor
        if (item.getItemId() == R.id.instructorSave) {
            // Validation Check
            if (instructorName.getText().length() < 1) {
                Toast.makeText(InstructorDetails.this, "Missing Name", Toast.LENGTH_LONG).show();
            } else if (instructorEmail.getText().length() < 1) {
                Toast.makeText(InstructorDetails.this, "Missing Email", Toast.LENGTH_LONG).show();
            } else if (instructorPhone.getText().length() < 1) {
                Toast.makeText(InstructorDetails.this, "Missing Phone Number", Toast.LENGTH_LONG).show();
            } else {
                Instructor instructor;
                Log.d("instructor", "Instructor id when editing an instructor is: " + instructorId);
                if (instructorId == -1) {
                    try {
                        if (repository.getmAllInstructors().isEmpty()) {
                            instructorId = 1;
                            instructor = new Instructor(instructorId, instructorName.getText().toString(), instructorEmail.getText().toString(), instructorPhone.getText().toString());
                            repository.insertInstructor(instructor);
                            this.finish();
                        } else {
                            instructorId = repository.getmAllInstructors().get(repository.getmAllInstructors().size() - 1).getInstructorId() + 1;
                            instructor = new Instructor(instructorId, instructorName.getText().toString(), instructorEmail.getText().toString(), instructorPhone.getText().toString());
                            repository.insertInstructor(instructor);
                            Toast.makeText(InstructorDetails.this, instructor.getName() + " was added", Toast.LENGTH_LONG).show();
                            this.finish();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    instructor = new Instructor(instructorId, instructorName.getText().toString(), instructorEmail.getText().toString(), instructorPhone.getText().toString());
                    repository.updateInstructor(instructor);
                    Toast.makeText(InstructorDetails.this, instructor.getName() + " was updated", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }

        }

        // Delete Instructor
        if (item.getItemId() == R.id.instructorDelete) {
            for (Instructor instructor : repository.getmAllInstructors()) {
                if (instructor.getInstructorId() == instructorId) {
                    repository.deleteInstructor(instructor);
                    Toast.makeText(InstructorDetails.this, instructor.getName() + " was deleted", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


}