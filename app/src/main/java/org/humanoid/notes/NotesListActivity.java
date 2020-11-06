package org.humanoid.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.humanoid.notes.adapters.NoteAdapter;
import org.humanoid.notes.models.Note;
import org.humanoid.notes.persistence.NoteRepository;
import org.humanoid.notes.util.SpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity implements NoteAdapter.NoteClickListener {
    private static final String TAG = "NotesListActivity";

    private RecyclerView mRecyclerView;
    private FloatingActionButton mAddNoteButton;

    private ArrayList<Note> mNotes = new ArrayList<>();
    private NoteAdapter mAdapter;
    private NoteRepository mNoteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        Toolbar toolbar = findViewById(R.id.note_list_toolbar);
        setSupportActionBar(toolbar);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        mRecyclerView = findViewById(R.id.recycler_view);
        mAddNoteButton = findViewById(R.id.fab);

        mAddNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        mNoteRepository = new NoteRepository(this);
//        getDummyNotes();
        retrieveNotes();
        initRecyclerView();
    }

    private void retrieveNotes() {
        mNoteRepository.retrieveNotesTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (mNotes.size() > 0) {
                    mNotes.clear();
                }
                if (notes != null) {
                    mNotes.addAll(notes);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onNoteClick(int position) {
//        Toast.makeText(this,"Testing clicks " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("Note", mNotes.get(position));
        startActivity(intent);
    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(4);
        mRecyclerView.addItemDecoration(itemDecorator);
        ItemTouchHelper helper = new ItemTouchHelper(mItemTouchHelperCallback);
        helper.attachToRecyclerView(mRecyclerView);
        mAdapter = new NoteAdapter(mNotes, this);
        mRecyclerView.setAdapter(mAdapter);
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

    private ItemTouchHelper.SimpleCallback mItemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                @NonNull RecyclerView.ViewHolder viewHolder,
                @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            removeNote(viewHolder.getAdapterPosition());
        }
    };

    private void removeNote(int noteIndex) {
        mNoteRepository.deleteNote(mNotes.get(noteIndex));

        mNotes.remove(noteIndex);
        mAdapter.notifyItemRemoved(noteIndex);
        mAdapter.notifyItemRangeChanged(noteIndex, mNotes.size());
    }
}
