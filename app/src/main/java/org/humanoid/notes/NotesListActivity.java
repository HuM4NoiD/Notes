package org.humanoid.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.github.javafaker.Faker;

import org.humanoid.notes.adapters.NotesRecyclerAdapter;
import org.humanoid.notes.models.Note;
import org.humanoid.notes.util.SpacingItemDecorator;

import java.util.ArrayList;
import java.util.Date;

public class NotesListActivity extends AppCompatActivity {
    private static final String TAG = "NotesListActivity";

    private RecyclerView mRecyclerView;

    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        mRecyclerView = findViewById(R.id.recycler_view);
        getDummyNotes();
        initRecyclerView();
    }

    private void initRecyclerView(){
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(8);
        mAdapter = new NotesRecyclerAdapter(mNotes);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getDummyNotes() {
        for (int i = 0; i <= 19; ++i) {
            Note note = new Note();
            Faker faker = new Faker();
            note.setTitle(faker.rickAndMorty().character());
            note.setContent(faker.rickAndMorty().quote());
            note.setTimestamp(faker.date()
                    .between(new Date(2018, 12, 20),
                            new Date(2019, 1, 30)).toString());
            mNotes.add(note);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
