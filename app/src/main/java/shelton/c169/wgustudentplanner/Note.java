package shelton.c169.wgustudentplanner;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "note_table", foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_ID",
        childColumns = "tbl_course_ID",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_ID")
    private int noteID;

    @ColumnInfo(name = "note_text")
    private String noteText;



    @ColumnInfo(name = "tbl_course_ID")
    private int courseID;

    public Note(String noteText, int courseID) {
        this.noteText = noteText;
        this.courseID = courseID;
    }



    int getNoteID() {
        return noteID;
    }

    void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    String getNoteText() {
        return noteText;
    }

    void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}