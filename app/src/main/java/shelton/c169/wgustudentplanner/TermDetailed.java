package shelton.c169.wgustudentplanner;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class TermDetailed extends AppCompatActivity {

    EditText termTitleView, termStartDate, termEndDate;
    Button coursesButton;
    TermViewModel mTermViewModel;
    int thisTermID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detailed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Term");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        termTitleView = findViewById(R.id.etTermTitle);
        termStartDate = findViewById(R.id.etTermStartDate);
        termEndDate = findViewById(R.id.etTermEndDate);
        coursesButton = findViewById(R.id.coursesButton);

        termStartDate.setKeyListener(null);
        termStartDate.setOnClickListener(new DateOnClickListener
                (TermDetailed.this, termStartDate));

        termEndDate.setKeyListener(null);
        termEndDate.setOnClickListener(new DateOnClickListener
                (TermDetailed.this, termEndDate));

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        Intent intent = getIntent();
        final int termID = intent.getIntExtra("termID", -1);
        thisTermID = termID;

        new Thread(new Runnable() {
            @Override
            public void run() {

                Term term = mTermViewModel.selectTermByID(termID);

                termTitleView.setText(term.getTermTitle());
                termStartDate.setText(term.getTermStartDate());
                termEndDate.setText(term.getTermEndDate());

            }
        }).start();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Term term = mTermViewModel.selectTermByID(thisTermID);
                        term.setTermTitle(termTitleView.getText().toString());
                        term.setTermStartDate(termStartDate.getText().toString());
                        term.setTermEndDate(termEndDate.getText().toString());
                        mTermViewModel.updateTerm(term);
                        finish();
                    }
                }).start();
            }
        });

        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermDetailed.this, CourseList.class);
                intent.putExtra("termID", thisTermID);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_term) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    int courseCount = mTermViewModel.getCourseCount(thisTermID);

                    if (courseCount == 0) {

                        Term term = mTermViewModel.selectTermByID(thisTermID);
                        mTermViewModel.deleteTerm(term);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TermDetailed.this,
                                        "Term cannot be deleted as courses are assigned to it.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
        return super.onOptionsItemSelected(item);
    }
}