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
import android.widget.EditText;

public class NoteDetailed extends AppCompatActivity {

    EditText noteText;
    TermViewModel mTermViewModel;
    int thisNoteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detailed);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noteText = findViewById(R.id.etNoteBody);

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);

        Intent intent = getIntent();
        final int noteID = intent.getIntExtra("noteID", -1);
        thisNoteID = noteID;

        new Thread(new Runnable() {
            @Override
            public void run() {

                Note note = mTermViewModel.selectNoteByID(noteID);
                noteText.setText(note.getNoteText());

            }
        }).start();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Note note = mTermViewModel.selectNoteByID(noteID);
                        note.setNoteText(noteText.getText().toString());
                        mTermViewModel.updateNote(note);
                        finish();

                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_detailed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.share) {

            String text = noteText.getText().toString();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share Note"));
        }

        if (id == R.id.note_delete) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Note note = mTermViewModel.selectNoteByID(thisNoteID);
                    mTermViewModel.deleteNote(note);
                    finish();
                }
            }).start();
        }
        return super.onOptionsItemSelected(item);
    }
}