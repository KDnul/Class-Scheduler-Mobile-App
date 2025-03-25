package com.example.c196mobiledevelopment.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Instructor;

import java.util.List;

/**
 * Manipulates the data for Instructors.
 */
@Dao
public interface InstructorDAO {
    /**
     * Inserts an instructor class into the database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInstructor(Instructor instructor);

    /**
     * Updates an instructor class in the database.
     */
    @Update
    void updateInstructor(Instructor instructor);

    /**
     * Deletes an instructor from the database.
     */
    @Delete
    void deleteInstructor(Instructor instructor);

    /**
     * Grabs all the instructors from the database.
     */
    @Query("SELECT * FROM instructors")
    List<Instructor> getAllInstructors();
}
