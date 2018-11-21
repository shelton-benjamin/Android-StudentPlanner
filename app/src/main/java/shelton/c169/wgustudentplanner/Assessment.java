package shelton.c169.wgustudentplanner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "assessment_table", foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_ID",
        childColumns = "tbl_course_ID",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assessment_ID")
    private int assessmentID;

    @NonNull
    @ColumnInfo
    private String assessmentTitle;

    @NonNull
    private String assessmentDueDate;

    @ColumnInfo(name = "tbl_course_ID")
    private int courseID;

    public Assessment(@NonNull String assessmentTitle, @NonNull String assessmentDueDate, int courseID) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDueDate = assessmentDueDate;
        this.courseID = courseID;
    }

    int getAssessmentID() {
        return assessmentID;
    }

    void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    @NonNull
    String getAssessmentTitle() {
        return assessmentTitle;
    }

    void setAssessmentTitle(@NonNull String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    @NonNull
    String getAssessmentDueDate() {
        return assessmentDueDate;
    }

    void setAssessmentDueDate(@NonNull String assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
