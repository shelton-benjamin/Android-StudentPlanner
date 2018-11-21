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

public class TermList extends AppCompatActivity {

    private TermViewModel mTermViewModel;

    public static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rvTermList);
        final TermListAdapter adapter = new TermListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        mTermViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable final List<Term> terms) {
                adapter.setTerms(terms);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermList.this, CreateTerm.class);
                startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
            }
        });


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String[] termParams = data.getStringArrayExtra(CreateTerm.EXTRA_REPLY);
            String title = termParams[0];
            String startDate = termParams[1];
            String endDate = termParams[2];

            Term term = new Term(title, startDate, endDate);
            mTermViewModel.insert(term);
        }
    }

}
