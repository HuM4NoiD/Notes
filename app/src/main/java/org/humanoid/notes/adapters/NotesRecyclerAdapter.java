package org.humanoid.notes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.humanoid.notes.R;
import org.humanoid.notes.models.Note;

import java.util.ArrayList;

/**
 * Created by Jugal Mistry on 2/1/2019.
 */
public class NotesRecyclerAdapter extends RecyclerView.Adapter <NotesRecyclerAdapter.NotesViewHolder>{

    private ArrayList<Note> mNotes = new ArrayList<>();

    public NotesRecyclerAdapter(ArrayList<Note> notes) {
        mNotes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_note_list_item, parent, false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.setData(mNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView title, content, timestamp;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            content = itemView.findViewById(R.id.note_content);
            timestamp = itemView.findViewById(R.id.note_timestamp);
        }

        public void setData(Note note) {
            title.setText(note.getTitle());
            content.setText(note.getContent());
            timestamp.setText(note.getTimestamp());
        }
    }
}
