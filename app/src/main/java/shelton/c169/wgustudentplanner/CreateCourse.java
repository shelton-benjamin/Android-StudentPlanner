package shelton.c169.wgustudentplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class CreateCourse extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.termlistsql.REPLY";

    private EditText etCourseTitle, etCourseStartDate, etCourseEndDate,
            etCourseMentorName, etCourseMentorEmail, etCourseMentorPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etCourseTitle = findViewById(R.id.etCourseTitle);
        etCourseStartDate = findViewById(R.id.etCourseStartDate);
        etCourseEndDate = findViewById(R.id.etCourseEndDate);
        etCourseMentorName = findViewById(R.id.etCourseMentorName);
        etCourseMentorEmail = findViewById(R.id.etCourseMentorEmail);
        etCourseMentorPhone = findViewById(R.id.etCourseMentorPhone);

        etCourseStartDate.setKeyListener(null);
        etCourseStartDate.setOnClickListener(new DateOnClickListener
                (CreateCourse.this, etCourseStartDate));

        etCourseEndDate.setKeyListener(null);
        etCourseEndDate.setOnClickListener(new DateOnClickListener
                (CreateCourse.this, etCourseEndDate));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(etCourseTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String courseTitle = etCourseTitle.getText().toString();
                    String startDate = etCourseStartDate.getText().toString();
                    String endDate = etCourseEndDate.getText().toString();
                    String mentorName = etCourseMentorName.getText().toString();
                    String mentorEmail = etCourseMentorEmail.getText().toString();
                    String mentorPhone = etCourseMentorPhone.getText().toString();

                    String courseParams[] = new String[]{courseTitle, startDate, endDate,
                            mentorName, mentorEmail, mentorPhone};

                    replyIntent.putExtra(EXTRA_REPLY, courseParams);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}