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

public class NoteList extends AppCompatActivity {

    private TermViewModel mTermViewModel;
    int thisCourseID;
    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
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

        RecyclerView recyclerView = findViewById(R.id.rvNoteList);
        final NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.getNotes(courseID).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteList.this, CreateNote.class);
                startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String text = data.getStringExtra(CreateNote.EXTRA_REPLY);
            Note note = new Note(text, thisCourseID);
            mTermViewModel.insert(note);
        }
    }
}