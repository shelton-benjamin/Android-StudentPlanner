package shelton.c169.wgustudentplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class CreateNote extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.termlistsql.REPLY";

    EditText noteBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noteBody = findViewById(R.id.etNoteBody);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(noteBody.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {

                    String text = noteBody.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, text);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

}