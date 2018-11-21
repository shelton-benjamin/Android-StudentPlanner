package shelton.c169.wgustudentplanner;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class TermRepository {

    private TermDao mTermDao;
    private LiveData<List<Term>> mAllTerms;
    private LiveData<List<Course>> mTermCourses;
    private LiveData<List<Note>> mCourseNotes;
    private LiveData<List<Assessment>> mCourseAssessments;

    TermRepository(Application application) {
        TermRoomDatabase db = TermRoomDatabase.getDatabase(application);
        mTermDao = db.termDao();
        mAllTerms = mTermDao.getAllTerms();
    }


    LiveData<List<Term>> getAllTerms() {
        return mAllTerms;
    }


    void insert(Term term) {
        new insertTermAsyncTask(mTermDao).execute(term);
    }

    Term selectTermByID(int id) {

        return mTermDao.selectTermByID(id);
    }

    void updateTerm(Term term) {

        mTermDao.updateTerm(term);
    }

    int getCourseCount(int termID) {

       return mTermDao.getCourseCount(termID);
    }

    void deleteTerm(Term term) {

        mTermDao.deleteTerm(term);
    }

    LiveData<List<Course>> getCourses(int termID) {

        mTermCourses = mTermDao.getCourses(termID);
        return mTermCourses;
    }

    void insert(Course course) {

        new insertCourseAsyncTask(mTermDao).execute(course);

    }

    Course selectCourseByID(int id) {

        return mTermDao.selectCourseByID(id);
    }

    void updateCourse(Course course) {

        mTermDao.updateCourse(course);
    }

    void deleteCourse(Course course) {

        mTermDao.deleteCourse(course);
    }

    LiveData<List<Note>> getNotes(int courseID) {

        mCourseNotes = mTermDao.getNotes(courseID);
        return mCourseNotes;
    }

    void insert(Note note) {

        new insertNoteAsyncTask(mTermDao).execute(note);
    }

    Note selectNoteByID(int id) {

        return mTermDao.selectNoteByID(id);
    }

    void updateNote(Note note) {

        mTermDao.updateNote(note);
    }

    void deleteNote(Note note) {

        mTermDao.deleteNote(note);
    }

    LiveData<List<Assessment>> getAssessments(int courseID) {

        mCourseAssessments = mTermDao.getAssessments(courseID);
        return mCourseAssessments;
    }

    void insert(Assessment assessment) {

        new insertAssessmentAsyncTask(mTermDao).execute(assessment);
    }

    Assessment selectAssessmentByID(int id) {

        return mTermDao.selectAssessmentByID(id);
    }

    void updateAssessment(Assessment assessment) {

        mTermDao.updateAssessment(assessment);
    }

    void deleteAssessment(Assessment assessment) {

        mTermDao.deleteAssessment(assessment);
    }

    private static class insertTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertTermAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Term... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertCourseAsyncTask extends AsyncTask<Course, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertCourseAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Course... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private static class insertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertNoteAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Note... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private TermDao mAsyncTaskDao;

        insertAssessmentAsyncTask(TermDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Assessment... params) {

            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
