package shelton.c169.wgustudentplanner;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class CourseList extends AppCompatActivity {

    private TermViewModel mTermViewModel;
    int thisTermID;
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        final int termID = intent.getIntExtra("termID", -1);
        thisTermID = termID;

        RecyclerView recyclerView = findViewById(R.id.rvCourseList);
        final CourseListAdapter adapter = new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.getCourses(termID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable final List<Course> courses) {
                adapter.setCourses(courses);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseList.this, CreateCourse.class);
                startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String[] courseParams = data.getStringArrayExtra(CreateTerm.EXTRA_REPLY);
            String title = courseParams[0];
            String startDate = courseParams[1];
            String endDate = courseParams[2];
            String mentorName = courseParams[3];
            String mentorEmail = courseParams[4];
            String mentorPhone = courseParams[5];

            Course course = new Course(title, startDate, endDate, mentorName,
                    mentorEmail, mentorPhone, thisTermID);
            mTermViewModel.insert(course);
        }
    }

}
