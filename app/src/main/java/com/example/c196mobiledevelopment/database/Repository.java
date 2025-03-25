package com.example.c196mobiledevelopment.database;

import android.app.Application;

import com.example.c196mobiledevelopment.DAO.AssessmentDAO;
import com.example.c196mobiledevelopment.DAO.CourseDAO;
import com.example.c196mobiledevelopment.DAO.InstructorDAO;
import com.example.c196mobiledevelopment.DAO.TermDAO;
import com.example.c196mobiledevelopment.entities.Assessment;
import com.example.c196mobiledevelopment.entities.Course;
import com.example.c196mobiledevelopment.entities.Instructor;
import com.example.c196mobiledevelopment.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private InstructorDAO mInstructorDAO;

    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Instructor> mAllInstructors;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mInstructorDAO = db.instructorDAO();
    }

    /**
     * Returns all Terms in the database
     *
     * @return terms
     */
    public List<Term> getmAllTerms() throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllTerms;
    }

    /**
     * @param term Inserts a term class into the database.
     */
    public void insertTerm(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.insertTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param term Updates an existing term in the database.
     */
    public void updateTerm(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.updateTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param term Deletes a term in the database.
     */
    public void deleteTerm(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.deleteTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return all Courses in the database
     *
     * @return courses
     */
    public List<Course> getmAllCourses() throws InterruptedException {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return mAllCourses;
    }

    /**
     * @param course Inserts a course class into the database.
     */
    public void insertCourse(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.insertCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param course Updates an existing course in the database.
     */
    public void updateCourse(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.updateCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param course Deletes a course in the database.
     */
    public void deleteCourse(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.deleteCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return all Assessments to the database
     *
     * @return assessment
     */

    public List<Assessment> getmAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    /**
     * @param assessment Inserts a assessment class into the database.
     */
    public void insertAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.insertAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param assessment Updates an existing assessment in the database.
     */
    public void updateAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.updateAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param assessment Deletes an assessment in the database.
     */
    public void deleteAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.deleteAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return all Instructors to the database
     *
     * @return instructor
     */

    public List<Instructor> getmAllInstructors() {
        databaseExecutor.execute(() -> {
            mAllInstructors = mInstructorDAO.getAllInstructors();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllInstructors;
    }

    /**
     * @param instructor Inserts an instructor class into the database.
     */
    public void insertInstructor(Instructor instructor) {
        databaseExecutor.execute(() -> {
            mInstructorDAO.insertInstructor(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param instructor Updates an existing instructor in the database.
     */
    public void updateInstructor(Instructor instructor) {
        databaseExecutor.execute(() -> {
            mInstructorDAO.updateInstructor(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param instructor Deletes an instructor in the database.
     */
    public void deleteInstructor(Instructor instructor) {
        databaseExecutor.execute(() ->
                mInstructorDAO.deleteInstructor(instructor)
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
