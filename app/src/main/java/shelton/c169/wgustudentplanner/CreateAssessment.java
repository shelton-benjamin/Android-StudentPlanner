package shelton.c169.wgustudentplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class CreateAssessment extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.termlistsql.REPLY";

    private EditText etAssessmentTitle, etAssessmentDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etAssessmentTitle = findViewById(R.id.etAssessmentTitle);
        etAssessmentDueDate = findViewById(R.id.etAssessmentDueDate);

        etAssessmentDueDate.setKeyListener(null);
        etAssessmentDueDate.setOnClickListener(new DateOnClickListener
                (CreateAssessment.this, etAssessmentDueDate));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(etAssessmentTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {

                    String assessmentTitle = etAssessmentTitle.getText().toString();
                    String assessmentDueDate = etAssessmentDueDate.getText().toString();
                    String assessmentParams[] = new String[]{assessmentTitle, assessmentDueDate};

                    replyIntent.putExtra(EXTRA_REPLY, assessmentParams);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}
