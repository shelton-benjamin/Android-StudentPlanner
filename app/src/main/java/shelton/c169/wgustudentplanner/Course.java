package shelton.c169.wgustudentplanner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static android.arch.persistence.room.ForeignKey.RESTRICT;

@Entity(tableName = "course_table", foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "term_ID",
        childColumns = "tbl_term_ID",
        onDelete = RESTRICT,
        onUpdate = CASCADE))
public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_ID")
    private int courseID;

    @NonNull
    private String courseTitle;

    @NonNull
    private String courseStartDate;

    @NonNull
    private String courseEndDate;

    private String courseMentorName;
    private String courseMentorEmail;
    private String courseMentorPhone;
    private String courseStatus;


    @ColumnInfo(name = "tbl_term_ID")
    private int termID;

    public Course(@NonNull String courseTitle, @NonNull String courseStartDate, @NonNull String courseEndDate,
                  String courseMentorName, String courseMentorEmail,
                  String courseMentorPhone, int termID) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseMentorName = courseMentorName;
        this.courseMentorEmail = courseMentorEmail;
        this.courseMentorPhone = courseMentorPhone;
        this.termID = termID;
        this.courseStatus = Status.PLAN_TO_TAKE.getStatus();

    }

    String getCourseStatus() {
        return courseStatus;
    }

    void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @NonNull
    String getCourseTitle() {
        return courseTitle;
    }

    void setCourseTitle(@NonNull String courseTitle) {
        this.courseTitle = courseTitle;
    }

    @NonNull
    String getCourseStartDate() {
        return courseStartDate;
    }

    void setCourseStartDate(@NonNull String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    @NonNull
    String getCourseEndDate() {
        return courseEndDate;
    }

    void setCourseEndDate(@NonNull String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseMentorName() {
        return courseMentorName;
    }

    public void setCourseMentorName(String courseMentorName) {
        this.courseMentorName = courseMentorName;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }

    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }

    public void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }

    int getTermID() {
        return termID;
    }
}
