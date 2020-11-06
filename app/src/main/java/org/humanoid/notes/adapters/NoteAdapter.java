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
public class NoteAdapter extends RecyclerView.Adapter <NoteAdapter.NoteViewHolder>{

    private ArrayList<Note> mNotes = new ArrayList<>();
    private NoteClickListener mNoteClickListener;

    public NoteAdapter(ArrayList<Note> notes, NoteClickListener listener) {
        mNotes = notes;
        mNoteClickListener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_note_list_item, parent, false);

        return new NoteViewHolder(view, mNoteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setData(mNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, content, timestamp;

        private NoteClickListener mNoteClickListener;

        public NoteViewHolder(@NonNull View itemView, NoteClickListener noteClickListener) {
            super(itemView);
            mNoteClickListener = noteClickListener;
            title = itemView.findViewById(R.id.note_title);
            content = itemView.findViewById(R.id.note_content);
            timestamp = itemView.findViewById(R.id.note_timestamp);
            itemView.setOnClickListener(this);
        }

        public void setData(Note note) {
            title.setText(note.getTitle());
            content.setText(note.getContent());
            timestamp.setText(note.getTimestamp());
        }


        @Override
        public void onClick(View view) {
            mNoteClickListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface NoteClickListener {
        void onNoteClick(int position);
    }
}
