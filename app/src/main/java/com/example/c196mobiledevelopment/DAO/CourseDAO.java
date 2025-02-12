package com.example.c196mobiledevelopment.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Course;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

    @Query("UPDATE courses SET termId =:termId WHERE courseId =:courseId")
    void updateCourseTerm(int termId, int courseId);

    @Query("SELECT * FROM courses WHERE termId = :termId")
    Course getCourseByTerm(int termId);

    @Query("UPDATE courses SET termId = 0 WHERE courseID =:courseId")
    void resetCourseTerm(int courseId);

    @Query("DELETE FROM courses")
    void deleteAllCourseData();

    @Query("SELECT COUNT(*) FROM courses")
    int countCourses();
}
