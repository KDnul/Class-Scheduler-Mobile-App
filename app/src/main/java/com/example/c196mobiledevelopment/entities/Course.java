package com.example.c196mobiledevelopment.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Database table for the course class.
 */
@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String title;
    private String startDate;
    private String endDate;
    private String status;
    private String note;
    private int termId;
    private int instructorId;

    /**
     * Course constructor.
     */
    public Course(int courseId, String title, String startDate, String endDate, String status, String note, int termId, int instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
        this.termId = termId;
        this.instructorId = instructorId;
    }

    /**
     * @return the courseId.
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
     * @return the startDate.
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate sets the startDate.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate.
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate sets the endDate.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status sets the status.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the note.
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note sets the note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return termId.
     */
    public int getTermId() {
        return termId;
    }

    /**
     * @param termId sets the termId.
     */
    public void setTermId(int termId) {
        this.termId = termId;
    }

    /**
     * @return instructorId.
     */
    public int getInstructorId() {
        return instructorId;
    }

    /**
     * @param instructorId sets the instructorId.
     */
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }
}
