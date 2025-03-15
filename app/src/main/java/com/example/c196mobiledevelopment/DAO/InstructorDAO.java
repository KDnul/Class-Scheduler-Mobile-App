package com.example.c196mobiledevelopment.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Instructor;

import java.util.List;

@Dao
public interface InstructorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertInstructor(Instructor instructor);

    @Update
    void updateInstructor(Instructor instructor);

    @Delete
    void deleteInstructor(Instructor instructor);

    @Query("SELECT * FROM instructors")
    List<Instructor> getAllInstructors();

    @Query("DELETE FROM instructors")
    void deleteAllInstructorData();

    @Query("SELECT COUNT(*) FROM instructors")
    int countInstructors();
}
