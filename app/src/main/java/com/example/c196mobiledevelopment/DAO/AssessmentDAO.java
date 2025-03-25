package com.example.c196mobiledevelopment.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Assessment;

import java.util.List;

/**
 * Manipulate the data for Assessments
 */
@Dao
public interface AssessmentDAO {
    /**
     * Inserts an assessment class to the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    /**
     * Updates an assessment class in the database.
     */
    @Update
    void updateAssessment(Assessment assessment);

    /**
     * Deletes an assessment class from the database.
     */
    @Delete
    void deleteAssessment(Assessment assessment);

    /**
     * Grabs all the assessments from the database.
     */
    @Query("SELECT * FROM assessments")
    List<Assessment> getAllAssessments();
}
