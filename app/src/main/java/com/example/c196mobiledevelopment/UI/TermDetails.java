package com.example.c196mobiledevelopment.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TermDetails extends AppCompatActivity {
    private Repository repository;
    EditText editTitle;
    TextView editStartDate;
    TextView editEndDate;
    String title;
    String startDate;
    String endDate;
    int termId;
    int numCourses;
    Term currentTerm;
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    final Calendar myCalendarStart = Calendar.getInstance();

    /**
     * Displays the Course Details activity with the appropriate xml tags.
     * Allows user input to construct, edit, or delete an instructor.
     */
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
            Intent intent = new Intent(TermDetails.this, CourseDetails.class);
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


        // Date Picker
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-Generated method stub
                Date date;
                // get value from other screen, but i'm going to hardcode it
                String info = editStartDate.getText().toString();
                if (info.isEmpty()) info = "01/01/25";
                try {
                    myCalendarStart.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDatePicker, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-Generated method stub
                Date date;
                // get value from other screen, but i'm going to hardcode it
                String info = editEndDate.getText().toString();
                if (info.isEmpty()) info = "01/01/25";
                try {
                    myCalendarStart.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDatePicker, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    /**
     * Sets the Textview of the course start date to display the appropriate date format.
     */
    public void updateLabelStart() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStartDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    /**
     * Sets the Textview of the course end date to display the appropriate date format.
     */
    public void updateLabelEnd() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEndDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    /**
     * Creates and inflates the menu items from the menu_termsdetails xml.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termdetails, menu);
        return true;
    }

    /**
     * @param item the selected menu item the user clicked on.
     *             If the user clicked on the back menu item, it will return the user to the previous activity.
     *             If the user clicked on the Save menu item, it will grab all the currently displayed user inputs and create a new term class or updates
     *             an existing term class to the database.
     *             If the user clicked on the Notify menu item, it will grab the currently displayed date of the term and will give the user a notification on when
     *             the term starts.
     *             If the user clicked on the Delete menu item, it will delete the currently displayed term class from the database.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        // Save Term
        if (item.getItemId() == R.id.termSave) {
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            try {
                // Validation Check
                if (editTitle.getText().length() < 1) {
                    Toast.makeText(TermDetails.this, "Missing Title", Toast.LENGTH_LONG).show();
                } else if (editStartDate.getText().length() < 1) {
                    Toast.makeText(TermDetails.this, "Missing Start date", Toast.LENGTH_LONG).show();
                } else if (editEndDate.getText().length() < 1) {
                    Toast.makeText(TermDetails.this, "Missing End date", Toast.LENGTH_LONG).show();
                } else {
                    Date start = sdf.parse(editStartDate.getText().toString());
                    Date end = sdf.parse(editEndDate.getText().toString());
                    assert start != null;
                    if (start.after(end)) {
                        Toast.makeText(TermDetails.this, "Start Date cannot be after end date", Toast.LENGTH_LONG).show();
                    } else {
                        Term term;
                        if (termId == -1) {
                            try {
                                if (repository.getmAllTerms().isEmpty()) {
                                    termId = 1;
                                    term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                                    repository.insertTerm(term);
                                    Toast.makeText(TermDetails.this, term.getTitle() + " term was added", Toast.LENGTH_LONG).show();
                                    this.finish();
                                } else {
                                    termId = repository.getmAllTerms().get(repository.getmAllTerms().size() - 1).getTermId() + 1;
                                    term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                                    repository.insertTerm(term);
                                    Toast.makeText(TermDetails.this, term.getTitle() + " term was added", Toast.LENGTH_LONG).show();
                                    this.finish();
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            term = new Term(termId, editTitle.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                            repository.updateTerm(term);
                            Toast.makeText(TermDetails.this, term.getTitle() + " term was updated", Toast.LENGTH_LONG).show();
                            this.finish();
                        }
                    }

                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        // Delete current Term
        if (item.getItemId() == R.id.termDelete) {
            try {
                for (Term t : repository.getmAllTerms()) {
                    if (t.getTermId() == termId) currentTerm = t;
                }
                numCourses = 0;
                for (Course c : repository.getmAllCourses()) {
                    if (c.getTermId() == termId) ++numCourses;
                }
                if (numCourses == 0) {
                    repository.deleteTerm(currentTerm);
                    Toast.makeText(TermDetails.this, currentTerm.getTitle() + " term was deleted", Toast.LENGTH_LONG).show();
                    this.finish();
                } else {
                    Toast.makeText(TermDetails.this, "Can't delete a term with courses in it", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // Notify Term
        if (item.getItemId() == R.id.termNotify) {
            String dateFromScreen = editStartDate.getText().toString();
            String myFormat = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            Date myDate = null;
            try {
                myDate = sdf.parse(dateFromScreen);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                //Receiver
                Long trigger = myDate.getTime();
                Intent intent = new Intent(TermDetails.this, MyReceiver.class);
                intent.putExtra("key", editTitle.getText().toString() + " term is starting");
                PendingIntent sender = PendingIntent.getBroadcast(TermDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Redisplay the RecyclerViewers with the appropriate data when.
     */
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