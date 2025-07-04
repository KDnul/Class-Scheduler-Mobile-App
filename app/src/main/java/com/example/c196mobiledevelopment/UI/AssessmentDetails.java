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
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AssessmentDetails extends AppCompatActivity {
    private Repository repository;
    EditText assessmentTitle;
    TextView assessmentDate;
    Spinner spinner;
    String assessmentName;
    String date;
    String assessmentType;
    int assessmentId;
    int courseId;
    DatePickerDialog.OnDateSetListener datePicker;
    final Calendar myCalendarStart = Calendar.getInstance();

    /**
     * Displays the Course Details activity with the appropriate xml tags.
     * Allows user input to construct, edit, or delete an assessment.
     */
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
        assessmentDate = findViewById(R.id.assessmentDateText);
        assessmentType = getIntent().getStringExtra("type");
        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        courseId = getIntent().getIntExtra("courseId", -1);

        assessmentName = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");
        assessmentTitle.setText(assessmentName);
        assessmentDate.setText(date);

        // Date Picker
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        assessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-Generated method stub
                Date date;
                // get value from other screen, but i'm going to hardcode it
                String info = assessmentDate.getText().toString();
                if (info.isEmpty()) info = "01/01/25";
                try {
                    myCalendarStart.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, datePicker, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    /**
     * Sets the Textview of the assessment date to display the appropriate date format.
     */
    public void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        assessmentDate.setText(sdf.format(myCalendarStart.getTime()));
    }

    /**
     * @return Gets the current position of the array item on the assessment status spinner.
     */
    private int getIndex(Spinner spinner, String courseStatus) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(courseStatus)) {
                index = i;
            }
        }
        return index;
    }

    /**
     * Creates and inflates the menu items from the menu_assessmentdetails xml.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessmentdetails, menu);
        return true;
    }

    /**
     * @param item the selected menu item the user clicked on.
     *             If the user clicked on the back menu item, it will return the user to the previous activity.
     *             If the user clicked on the Save menu item, it will grab all the currently displayed user inputs and create a new assessment class or updates
     *             an existing assessment class to the database.
     *             If the user clicked on the Notify menu item, it will grab the currently displayed date of the assessment and will give the user a notification on when
     *             the assessment starts.
     *             If the user clicked on the Delete menu item, it will delete the currently displayed assessment class from the database.
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        // Save details of the assessment and make it
        if (item.getItemId() == R.id.assessmentSave) {
            if (courseId == -1) {
                Toast.makeText(AssessmentDetails.this, "Please make a course first", Toast.LENGTH_LONG).show();
            } else {
                // Validation Check
                if (assessmentTitle.getText().length() < 1) {
                    Toast.makeText(AssessmentDetails.this, "Missing Title", Toast.LENGTH_LONG).show();
                } else if (assessmentDate.getText().length() < 1) {
                    Toast.makeText(AssessmentDetails.this, "Missing Date", Toast.LENGTH_LONG).show();
                } else {
                    Assessment assessment;
                    Spinner spinner = findViewById(R.id.assessmentTypeSpinner);
                    assessmentType = spinner.getSelectedItem().toString();
                    if (assessmentId == -1) {
                        try {
                            if (repository.getmAllAssessments().isEmpty()) {
                                assessmentId = 1;
                                assessment = new Assessment(assessmentId, assessmentTitle.getText().toString(), assessmentDate.getText().toString(), assessmentType, courseId);
                                repository.insertAssessment(assessment);
                                Toast.makeText(AssessmentDetails.this, assessmentTitle.getText().toString() + " was added to Assessment List", Toast.LENGTH_LONG).show();
                                this.finish();
                            } else {
                                assessmentId = repository.getmAllAssessments().get(repository.getmAllAssessments().size() - 1).getAssessmentId() + 1;
                                assessment = new Assessment(assessmentId, assessmentTitle.getText().toString(), assessmentDate.getText().toString(), assessmentType, courseId);
                                repository.insertAssessment(assessment);
                                Toast.makeText(AssessmentDetails.this, assessmentTitle.getText().toString() + " was added to Assessment List", Toast.LENGTH_LONG).show();
                                this.finish();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        return true;
                    } else {
                        assessment = new Assessment(assessmentId, assessmentTitle.getText().toString(), assessmentDate.getText().toString(), assessmentType, courseId);
                        repository.updateAssessment(assessment);
                        Toast.makeText(AssessmentDetails.this, assessmentTitle.getText().toString() + " was updated", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                }
            }
        }

        // Notify Assessment
        if (item.getItemId() == R.id.assessmentNotify) {
            String dateFromScreen = assessmentDate.getText().toString();
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
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", assessmentTitle.getText().toString() + " assessment is today!");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        // Delete Assessment
        if (item.getItemId() == R.id.assessmentDelete) {
            for (Assessment a : repository.getmAllAssessments()) {
                if (a.getAssessmentId() == assessmentId) {
                    repository.deleteAssessment(a);
                    Toast.makeText(AssessmentDetails.this, a.getTitle() + " was deleted", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
}