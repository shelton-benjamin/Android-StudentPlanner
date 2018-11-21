package shelton.c169.wgustudentplanner;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Term.class, Course.class, Note.class, Assessment.class}, version = 1, exportSchema = false)
public abstract class TermRoomDatabase extends RoomDatabase {

    public abstract TermDao termDao();

    private static volatile TermRoomDatabase INSTANCE;

    static TermRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TermRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TermRoomDatabase.class, "term_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TermDao mDao;

        PopulateDbAsync(TermRoomDatabase db) {
            mDao = db.termDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}


