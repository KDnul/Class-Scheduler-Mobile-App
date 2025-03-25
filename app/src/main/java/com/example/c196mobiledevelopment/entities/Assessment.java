package com.example.c196mobiledevelopment.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Database table for Assessment class.
 */
@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String title;
    private String date;
    private String type;
    private int courseId;

    /**
     * Assessment Constructor.
     */
    public Assessment(int assessmentId, String title, String date, String type, int courseId) {
        this.assessmentId = assessmentId;
        this.title = title;
        this.date = date;
        this.type = type;
        this.courseId = courseId;
    }

    /**
     * @return the assessmentId.
     */
    public int getAssessmentId() {
        return assessmentId;
    }

    /**
     * @param assessmentId sets the assessmentId.
     */
    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    /**
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title sets the title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the date.
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date sets the date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type sets the type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return courseId.
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @param courseId sets the courseId.
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
