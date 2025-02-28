package com.example.c196mobiledevelopment.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196mobiledevelopment.DAO.AssessmentDAO;
import com.example.c196mobiledevelopment.DAO.CourseDAO;
import com.example.c196mobiledevelopment.DAO.InstructorDAO;
import com.example.c196mobiledevelopment.DAO.TermDAO;
import com.example.c196mobiledevelopment.entities.Assessment;
import com.example.c196mobiledevelopment.entities.Course;
import com.example.c196mobiledevelopment.entities.Instructor;
import com.example.c196mobiledevelopment.entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract InstructorDAO instructorDAO();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "C195.db")
                            .fallbackToDestructiveMigration()
                            // For Synchronous .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
