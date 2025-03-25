package com.example.c196mobiledevelopment.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196mobiledevelopment.entities.Term;

import java.util.List;

/**
 * Manipulates the data for Terms
 */
@Dao
public interface TermDAO {
    /**
     * Inserts a term class into the database.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTerm(Term term);

    /**
     * Updates a term class in the database.
     */
    @Update
    void updateTerm(Term term);

    /**
     * Deletes a term from the database.
     */
    @Delete
    void deleteTerm(Term term);

    /**
     * Grabs all the terms from the database.
     */
    @Query("SELECT * FROM terms")
    List<Term> getAllTerms();
}
