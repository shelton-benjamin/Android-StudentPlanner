package shelton.c169.wgustudentplanner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static shelton.c169.wgustudentplanner.Status.getStatusArray;

public class CourseDetailed extends AppCompatActivity {

    EditText courseTitleView, courseStartDate, courseEndDate,
            courseMentorName, courseMentorEmail, courseMentorPhone;
    Spinner courseStatus;
    Button assessmentsButton, notesButton;
    TermViewModel mTermViewModel;
    int thisCourseID;
    String[] statusArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detailed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Course");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        courseTitleView = findViewById(R.id.etCourseTitle);
        courseStartDate = findViewById(R.id.etCourseStartDate);
        courseEndDate = findViewById(R.id.etCourseEndDate);
        courseMentorName = findViewById(R.id.etCourseMentorName);
        courseMentorEmail = findViewById(R.id.etCourseMentorEmail);
        courseMentorPhone = findViewById(R.id.etCourseMentorPhone);
        courseStatus = findViewById(R.id.spCourseStatus);
        assessmentsButton = findViewById(R.id.assessmentsButton);
        notesButton = findViewById(R.id.notesButton);

        courseStartDate.setKeyListener(null);
        courseStartDate.setOnClickListener(new DateOnClickListener
                (CourseDetailed.this, courseStartDate));

        courseEndDate.setKeyListener(null);
        courseEndDate.setOnClickListener(new DateOnClickListener
                (CourseDetailed.this, courseEndDate));

        statusArray = getStatusArray();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.coursestatus_item, statusArray);
        courseStatus.setAdapter(arrayAdapter);


        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        Intent intent = getIntent();
        final int courseID = intent.getIntExtra("courseID", -1);
        thisCourseID = courseID;

        new Thread(new Runnable() {
            @Override
            public void run() {

                Course course = mTermViewModel.selectCourseByID(courseID);

                courseTitleView.setText(course.getCourseTitle());
                courseStartDate.setText(course.getCourseStartDate());
                courseEndDate.setText(course.getCourseEndDate());
                courseMentorName.setText(course.getCourseMentorName());
                courseMentorEmail.setText(course.getCourseMentorEmail());
                courseMentorPhone.setText(course.getCourseMentorPhone());
                courseStatus.setSelection(arrayAdapter.getPosition(course.getCourseStatus()));
            }
        }).start();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Course course = mTermViewModel.selectCourseByID(courseID);

                        course.setCourseTitle(courseTitleView.getText().toString());
                        course.setCourseStartDate(courseStartDate.getText().toString());
                        course.setCourseEndDate(courseEndDate.getText().toString());
                        course.setCourseMentorName(courseMentorName.getText().toString());
                        course.setCourseMentorEmail(courseMentorEmail.getText().toString());
                        course.setCourseMentorPhone(courseMentorPhone.getText().toString());
                        course.setCourseStatus(courseStatus.getSelectedItem().toString());
                        mTermViewModel.updateCourse(course);
                        finish();
                    }
                }).start();
            }
        });

        assessmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailed.this, AssessmentList.class);
                intent.putExtra("courseID", thisCourseID);
                startActivity(intent);
            }
        });

        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CourseDetailed.this, NoteList.class);
                intent.putExtra("courseID", thisCourseID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.delete_course) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Course course = mTermViewModel.selectCourseByID(thisCourseID);
                    mTermViewModel.deleteCourse(course);
                    finish();
                }
            }).start();
        }

        if (id == R.id.action_notification) {

            try {
                setNotification();
                Toast.makeText(this, "Notifications set for course start and end date.",
                Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNotification() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = dateFormat.parse(courseStartDate.getText().toString());
        Date endDate = dateFormat.parse(courseEndDate.getText().toString());

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(startTime);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(endTime);

        String courseTitle = courseTitleView.getText().toString();
        String startString = "This course starts today.";
        String endString = "This course ends today.";

        String[] startArray = new String[]{courseTitle, startString};
        String[] endArray = new String[]{courseTitle, endString};


        Intent intent = new Intent(CourseDetailed.this, AlarmReceiver.class);
        intent.putExtra(Intent.EXTRA_TEXT, startArray);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (CourseDetailed.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);

        Intent intent2 = new Intent(CourseDetailed.this, AlarmReceiver.class);
        intent2.putExtra(Intent.EXTRA_TEXT, endArray);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast
                (CourseDetailed.this, 1, intent2, 0);
        AlarmManager alarmManager2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager2.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
    }


}