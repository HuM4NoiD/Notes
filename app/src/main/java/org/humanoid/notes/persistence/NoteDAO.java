package org.humanoid.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.humanoid.notes.models.Note;

import java.util.List;


@Dao
public interface NoteDAO {

    @Insert // INSERT INTO notes VALUES(id, "Title", "Body")
    long[] insertNotes(Note... notes);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    @Delete // DELETE FROM notes WHERE notes.id = id
    int deleteNotes(Note... notes);

    @Update // UPDATE notes SET data where notes.id = id
    int updateNotes(Note... notes);
}
