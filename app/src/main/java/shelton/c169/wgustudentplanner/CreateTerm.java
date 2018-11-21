package shelton.c169.wgustudentplanner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class CreateTerm extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.termlistsql.REPLY";

    private EditText etTermTitle;
    private EditText etTermStartDate;
    private EditText etTermEndDate;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etTermTitle = findViewById(R.id.etTermTitle);
        etTermStartDate = findViewById(R.id.etTermStartDate);
        etTermEndDate = findViewById(R.id.etTermEndDate);

        etTermStartDate.setKeyListener(null);
        etTermStartDate.setOnClickListener(new DateOnClickListener
                (CreateTerm.this, etTermStartDate));

        etTermEndDate.setKeyListener(null);
        etTermEndDate.setOnClickListener(new DateOnClickListener
                (CreateTerm.this, etTermEndDate));


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(etTermTitle.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {

                    String termTitle = etTermTitle.getText().toString();
                    String startDate = etTermStartDate.getText().toString();
                    String endDate = etTermEndDate.getText().toString();

                    String termParams[] = new String[]{termTitle, startDate, endDate};

                    replyIntent.putExtra(EXTRA_REPLY, termParams);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}
