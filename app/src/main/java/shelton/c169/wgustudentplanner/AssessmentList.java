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

public class AssessmentList extends AppCompatActivity {

    private TermViewModel mTermViewModel;
    int thisCourseID;
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
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
        final int courseID = intent.getIntExtra("courseID", -1);
        thisCourseID = courseID;

        RecyclerView recyclerView = findViewById(R.id.rvAssessmentList);
        final AssessmentListAdapter adapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.getAssessments(courseID).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable final List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentList.this, CreateAssessment.class);
                startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String[] assessmentParams = data.getStringArrayExtra(CreateNote.EXTRA_REPLY);
            String assessmentTitle = assessmentParams[0];
            String assessmentDueDate = assessmentParams[1];
            Assessment assessment = new Assessment(assessmentTitle, assessmentDueDate, thisCourseID);
            mTermViewModel.insert(assessment);

        }
    }
}