package org.humanoid.notes.async;

import android.os.AsyncTask;

import org.humanoid.notes.models.Note;
import org.humanoid.notes.persistence.NoteDAO;

/**
 * Created by Jugal Mistry on 6/19/2019.
 */
public class DeleteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDAO mNoteDAO;

    public DeleteAsyncTask(NoteDAO noteDAO) {
        this.mNoteDAO = noteDAO;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDAO.deleteNotes(notes);
        return null;
    }
}
