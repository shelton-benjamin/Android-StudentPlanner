package shelton.c169.wgustudentplanner;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TermDao {

    @Insert
    void insert(Term term);

    @Query("SELECT * from term_table ORDER BY term_ID ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * from term_table where term_ID = :givenID LIMIT 1")
    Term selectTermByID(int givenID);

    @Update(onConflict = REPLACE)
    void updateTerm(Term term);

    @Query("SELECT COUNT(tbl_term_ID) from course_table where tbl_term_ID = :givenID")
    int getCourseCount(int givenID);

    @Delete
    void deleteTerm(Term term);

    @Insert
    void insert(Course course);

    @Query("SELECT * from course_table where tbl_term_ID = :termID")
    LiveData<List<Course>> getCourses(int termID);

    @Query("SELECT * from course_table where course_ID = :givenID LIMIT 1")
    Course selectCourseByID(int givenID);

    @Update(onConflict = REPLACE)
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Insert
    void insert(Note note);

    @Query("SELECT * from note_table where tbl_course_ID = :courseID")
    LiveData<List<Note>> getNotes(int courseID);

    @Query("SELECT * from note_table where note_ID = :givenID LIMIT 1")
    Note selectNoteByID(int givenID);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Insert
    void insert(Assessment assessment);

    @Query("SELECT * from assessment_table where tbl_course_ID = :courseID")
    LiveData<List<Assessment>> getAssessments(int courseID);

    @Query("SELECT * from assessment_table where assessment_ID = :givenID")
    Assessment selectAssessmentByID(int givenID);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

}
