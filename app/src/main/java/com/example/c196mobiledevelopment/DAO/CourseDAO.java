package com.example.c196mobiledevelopment.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Course;

import java.util.List;

/**
 * Manipulate the data for Courses
 */
@Dao
public interface CourseDAO {
    /**
     * Inserts a course class into the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCourse(Course course);

    /**
     * Updates a course class in the database.
     */
    @Update
    void updateCourse(Course course);

    /**
     * Deletes a course from the database.
     */
    @Delete
    void deleteCourse(Course course);

    /**
     * Grabs all the courses from the database.
     */
    @Query("SELECT * FROM courses")
    List<Course> getAllCourses();

}
