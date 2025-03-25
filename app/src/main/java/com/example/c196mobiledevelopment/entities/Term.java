package com.example.c196mobiledevelopment.entities;

import androidx.room.PrimaryKey;
import androidx.room.Entity;

/**
 * Database table for the term class
 */
@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String title;
    private String startDate;
    private String endDate;

    /**
     * Constructor for the term class.
     */
    public Term(int termId, String title, String startDate, String endDate) {
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the termId.
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
}
