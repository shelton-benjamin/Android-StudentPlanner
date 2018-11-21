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
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AssessmentDetailed extends AppCompatActivity {

    EditText assessmentTitleView, assessmentDueDate;
    TermViewModel mTermViewModel;
    int thisAssessmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detailed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assessment");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        assessmentTitleView = findViewById(R.id.etAssessmentTitle);
        assessmentDueDate = findViewById(R.id.etAssessmentDueDate);

        assessmentDueDate.setKeyListener(null);
        assessmentDueDate.setOnClickListener(new DateOnClickListener
                (AssessmentDetailed.this, assessmentDueDate));

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        Intent intent = getIntent();
        final int assessmentID = intent.getIntExtra("assessmentID", -1);
        thisAssessmentID = assessmentID;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Assessment assessment = mTermViewModel.selectAssessmentByID(assessmentID);
                assessmentTitleView.setText(assessment.getAssessmentTitle());
                assessmentDueDate.setText(assessment.getAssessmentDueDate());
            }
        }).start();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Assessment assessment = mTermViewModel.selectAssessmentByID(assessmentID);
                        assessment.setAssessmentTitle(assessmentTitleView.getText().toString());
                        assessment.setAssessmentDueDate(assessmentDueDate.getText().toString());
                        mTermViewModel.updateAssessment(assessment);
                        finish();
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.delete_assessment) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Assessment assessment = mTermViewModel.selectAssessmentByID(thisAssessmentID);
                    mTermViewModel.deleteAssessment(assessment);
                    finish();
                }
            }).start();
        }

        if(id == R.id.action_notification) {

            try {
                setNotification();
                Toast.makeText(this, "Notification set for assessment due date.",
                        Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void setNotification() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = dateFormat.parse(assessmentDueDate.getText().toString());

        long time = date.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        String assessmentTitle = assessmentTitleView.getText().toString();
        String dueDateString = "This assessment is due today.";

        String[] alarmArray = new String[]{assessmentTitle, dueDateString};

        Intent intent = new Intent(AssessmentDetailed.this, AlarmReceiver.class);
        intent.putExtra(Intent.EXTRA_TEXT, alarmArray);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (AssessmentDetailed.this, 2, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
}