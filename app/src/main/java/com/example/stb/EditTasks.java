package com.example.stb;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditTasks extends AppCompatActivity {

    private TasksAdapter dbAdapter;

    private EditText titleText;
    private EditText bodyText;
    private Long mRowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_edit);

        dbAdapter = new TasksAdapter(this);
        dbAdapter.open();

        titleText = findViewById(R.id.title);
        bodyText = findViewById(R.id.body);

        mRowId = (savedInstanceState == null) ? null :
                (Long) savedInstanceState.getSerializable(TasksAdapter.KEY_ROWID);
        if (mRowId == null) {
            Bundle extras = getIntent().getExtras();
            mRowId = extras != null ? extras.getLong(TasksAdapter.KEY_ROWID) : null;
        }

        if (mRowId != null) {
            Cursor note = dbAdapter.fetchNote(mRowId);
            titleText.setText(note.getString(
                    note.getColumnIndexOrThrow(TasksAdapter.KEY_TITLE)));
            bodyText.setText(note.getString(
                    note.getColumnIndexOrThrow(TasksAdapter.KEY_BODY)));
        }
    }

    public void saveNote(View view) { //the function that the confirm button calls
        String title = titleText.getText().toString();
        String body = bodyText.getText().toString();

        if (mRowId == null) {
            long id = dbAdapter.createNote(title, body);
            if (id > 0) {
                mRowId = id;
            }
            Toast.makeText(EditTasks.this, R.string.t_created, Toast.LENGTH_SHORT).show();
        } else {
            dbAdapter.updateNote(mRowId, title, body);
            Toast.makeText(EditTasks.this, R.string.t_updated, Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);
        dbAdapter.close();
        finish();
    }

    public void deleteNote(View view) { //the function to remove task
        dbAdapter.deleteNote(mRowId);
        Toast.makeText(EditTasks.this, R.string.t_removed, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        dbAdapter.close();
        finish();
    }
}

