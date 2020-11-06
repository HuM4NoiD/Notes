package org.humanoid.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.humanoid.notes.async.DeleteAsyncTask;
import org.humanoid.notes.async.InsertAsyncTask;
import org.humanoid.notes.async.UpdateAsyncTask;
import org.humanoid.notes.models.Note;

import java.util.List;

/**
 * Created by Jugal Mistry on 6/19/2019.
 */
public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }

    public void insertNoteTask(Note note) {
        new InsertAsyncTask(mNoteDatabase.getNoteDAO()).execute(note);
    }

    public void updateNoteTask(Note note) {
        new UpdateAsyncTask(mNoteDatabase.getNoteDAO()).execute(note);
    }

    public LiveData<List<Note>> retrieveNotesTask() {
        return mNoteDatabase.getNoteDAO().getAllNotes();
    }

    public void deleteNote(Note note) {
        new DeleteAsyncTask(mNoteDatabase.getNoteDAO()).execute(note);
    }
}
