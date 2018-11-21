package shelton.c169.wgustudentplanner;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TermViewModel extends AndroidViewModel {

    private TermRepository mRepository;

    private LiveData<List<Term>> mAllTerms;
    private LiveData<List<Course>> mTermCourses;
    private LiveData<List<Note>> mCourseNotes;
    private LiveData<List<Assessment>> mCourseAssessments;


    public TermViewModel(Application application) {
        super(application);
        mRepository = new TermRepository(application);
        mAllTerms = mRepository.getAllTerms();
    }

    LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }

    void insert(Term term) {
        mRepository.insert(term);
    }

    Term selectTermByID(int id) {

        return mRepository.selectTermByID(id);
    }

    void updateTerm(Term term) {

        mRepository.updateTerm(term);
    }

    int getCourseCount(int termID) {

        return mRepository.getCourseCount(termID);
    }

    void deleteTerm(Term term) {

        mRepository.deleteTerm(term);
    }

    LiveData<List<Course>> getCourses(int termID) {

        mTermCourses = mRepository.getCourses(termID);
        return mTermCourses;
    }

    void insert(Course course) {
        mRepository.insert(course);
    }

    Course selectCourseByID(int id) {

        return mRepository.selectCourseByID(id);
    }

    void updateCourse(Course course) {

        mRepository.updateCourse(course);
    }

    void deleteCourse(Course course) {

        mRepository.deleteCourse(course);
    }

    LiveData<List<Note>> getNotes(int courseID) {

        mCourseNotes = mRepository.getNotes(courseID);
        return mCourseNotes;
    }

    void insert(Note note) {

        mRepository.insert(note);
    }

    Note selectNoteByID(int id) {

        return mRepository.selectNoteByID(id);
    }

    void updateNote(Note note) {

        mRepository.updateNote(note);
    }

    void deleteNote(Note note) {

        mRepository.deleteNote(note);
    }

    LiveData<List<Assessment>> getAssessments(int courseID) {

        mCourseAssessments = mRepository.getAssessments(courseID);
        return mCourseAssessments;
    }

    void insert(Assessment assessment) {

        mRepository.insert(assessment);
    }

    Assessment selectAssessmentByID(int id) {

        return mRepository.selectAssessmentByID(id);
    }

    void updateAssessment(Assessment assessment) {

        mRepository.updateAssessment(assessment);
    }

    void deleteAssessment(Assessment assessment) {

        mRepository.deleteAssessment(assessment);
    }
}