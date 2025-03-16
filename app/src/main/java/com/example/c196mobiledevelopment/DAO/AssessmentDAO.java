package com.example.c196mobiledevelopment.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("SELECT * FROM assessments")
    List<Assessment> getAllAssessments();

    @Query("UPDATE assessments SET courseId = 0 WHERE courseId =:id")
    void resetAssessmentCourse(int id);

    @Query("UPDATE assessments SET courseId = 0 WHERE courseId =:courseId AND assessmentId =:assessmentId")
    void resetAssessmentCourseSpecific(int courseId, int assessmentId);

    @Query("DELETE FROM assessments")
    void deleteAllAssessmentData();

    @Query("SELECT COUNT(*) FROM assessments")
    int countAssessments();
}
