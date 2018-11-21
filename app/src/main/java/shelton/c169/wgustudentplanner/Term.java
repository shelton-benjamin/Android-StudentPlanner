package shelton.c169.wgustudentplanner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "term_ID")
    private int termID;

    @NonNull
    private String termTitle;

    @NonNull
    private String termStartDate;

    @NonNull
    private String termEndDate;


    public Term(@NonNull String termTitle, @NonNull String termStartDate, @NonNull String termEndDate) {
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;


    }

    int getTermID() {
        return termID;
    }

    void setTermID(int termID) {
        this.termID = termID;
    }

    @NonNull
    String getTermTitle() {
        return termTitle;
    }

    void setTermTitle(@NonNull String termTitle) {
        this.termTitle = termTitle;
    }

    @NonNull
    String getTermStartDate() {
        return termStartDate;
    }

    void setTermStartDate(@NonNull String termStartDate) {
        this.termStartDate = termStartDate;
    }

    @NonNull
    String getTermEndDate() {
        return termEndDate;
    }

    void setTermEndDate(@NonNull String termEndDate) {
        this.termEndDate = termEndDate;
    }


}
