package org.humanoid.notes;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.humanoid.notes.models.Note;
import org.humanoid.notes.persistence.NoteRepository;
import org.humanoid.notes.util.Utility;

public class NoteActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    public static final int EDIT_MODE_ENABLED = 1;
    public static final int EDIT_MODE_DISABLED = 0;
    private static final String TAG = "NoteActivity";

    TextInputLayout layout;
    private TextInputEditText mLinedEditText;
    private TextInputEditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mSaveContainer, mBackContainer;
    private ImageButton mSave, mBack;

    private KeyListener mKeyListener;
    private boolean mIsNewNote;
    private Note mInitialNote, mFinalNote;
    private GestureDetector mDetector;
    private int mCurrentMode;
    private NoteRepository mNoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        mLinedEditText = findViewById(R.id.note_text);
        layout = findViewById(R.id.note_edit_title_layout);
        mEditTitle = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mLinedEditText.setOnTouchListener(this);
        mSave = findViewById(R.id.toolbar_save);
        mBack = findViewById(R.id.toolbar_back);

        setListeners();

        mNoteRepository = new NoteRepository(this);
        if (getIncomingIntent()) {
            // This is a new note, Edit Mode
            setNewNoteProperties();
            enableEditMode();
        } else {
            // This note is already created, view Mode
            setNoteProperties();
            disableEditMode();
        }

    }

    private void enableEditMode() {
        mBack.setVisibility(View.GONE);
        mSave.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);

        mCurrentMode = EDIT_MODE_ENABLED;
        enableInteraction();
    }

    private void disableEditMode() {
        mBack.setVisibility(View.VISIBLE);
        mSave.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);

        mCurrentMode = EDIT_MODE_DISABLED;
        disableInteraction();

        String t = mLinedEditText.getText().toString()
                .replace("\n", "")
                .replace(" ", "");

        if (t.length() > 0) {
            mFinalNote.setTitle(mEditTitle.getText().toString());
            mFinalNote.setContent(mLinedEditText.getText().toString());
            String timestamp = Utility.getCurrentTimeStamp();
            mFinalNote.setTimestamp(timestamp);

            if (!mFinalNote.getContent().equals(mInitialNote.getContent())
                || !mFinalNote.getTitle().equals(mInitialNote.getContent())) {
                saveChanges();
            }
        }

        //Hide Soft Keyboard
        InputMethodManager manager = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        manager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    private void setListeners() {
        mDetector = new GestureDetector(this, this);
        mLinedEditText.setOnTouchListener(this);
        mSave.setOnClickListener(this);
        mViewTitle.setOnClickListener(this);
        mKeyListener = mLinedEditText.getKeyListener();
        mBack.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);
    }

    private boolean getIncomingIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("Note")) {
            mInitialNote = intent.getParcelableExtra("Note");
            mFinalNote = new Note();
            mFinalNote.setId  (mInitialNote.getId());
            mFinalNote.setTitle(mInitialNote.getTitle());
            mFinalNote.setContent(mInitialNote.getContent());
            mFinalNote.setTimestamp(mInitialNote.getTimestamp());
            mCurrentMode = EDIT_MODE_DISABLED;

            mIsNewNote = false;
            return false;
        }
        mCurrentMode = EDIT_MODE_ENABLED;
        mIsNewNote = true;
        return true;
    }

    private void disableInteraction(){
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableInteraction(){
        mLinedEditText.setKeyListener(mKeyListener);
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    private void setNewNoteProperties() {
        mViewTitle.setText("Title");
        mEditTitle.setText("Title");

        mInitialNote = new Note();
        mFinalNote = new Note();

        mInitialNote.setTitle("Title");
        mFinalNote.setTitle("Title");
    }

    private void setNoteProperties() {
        mViewTitle.setText(mInitialNote.getTitle());
        mEditTitle.setText(mInitialNote.getTitle());
        mLinedEditText.setText(mInitialNote.getContent());
    }

    private void updateNoteObject() {
        String newContent = mLinedEditText.getText().toString();
        String newTitle = mViewTitle.getText().toString();

        if (!mInitialNote.getTitle().equals(newTitle) ||
                !mInitialNote.getTitle().equals(newContent)){
            mInitialNote.setTitle(newTitle);
            mInitialNote.setContent(newContent);
            mInitialNote.setTimestamp(Utility.getCurrentTimeStamp());
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v,
            float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v,
            float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: double tap");
        enableEditMode();
        enableInteraction();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if(mCurrentMode == EDIT_MODE_ENABLED)
            onClick(mSave);
        else
            super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.toolbar_save:
                disableEditMode();
                break;

            case R.id.note_text_title:
                enableEditMode();
                mEditTitle.setSelection(0, mEditTitle.length());
                break;

            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    private void saveChanges() {
        if (mIsNewNote) {
            saveNewNote();
        } else {
            updateNoteObject();
            updateNote();
        }
    }

    public void updateNote() {
        mNoteRepository.updateNoteTask(mInitialNote);
    }

    private void saveNewNote() {
        mNoteRepository.insertNoteTask(mFinalNote);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("mode",mCurrentMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentMode = savedInstanceState.getInt("mode");
        if (mCurrentMode == EDIT_MODE_ENABLED) {
            enableEditMode();
            enableInteraction();
        } else {
            disableEditMode();
            disableInteraction();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mViewTitle.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
