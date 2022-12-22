/*
 * (C) Copyright 2022 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.example.stb;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Tasks extends AppCompatActivity {
    private TasksAdapter dbaddTasks;
    private ListView listView;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    fillData();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        dbaddTasks = new TasksAdapter(this);
        dbaddTasks.open();

        listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int pos, long id) { //when click the tasks
                Intent intent = new Intent(view.getContext(), EditTasks.class);
                intent.putExtra(TasksAdapter.KEY_ROWID, id); //it sends the information of the id of the row
                activityResultLauncher.launch(intent);
            }
        });

        fillData();
    }

    public void action_insert(View view) { //the function that works with the Add task button
        Intent intent = new Intent(this, EditTasks.class);
        startActivity(intent);
    }

    public void action_insert_calendar(View view) { //the function that works with the Add event to calendar button
        Intent intent = new Intent(this, EditCalendar.class);
        startActivity(intent);
    }

    private void fillData() {
        Cursor notesCursor = dbaddTasks.fetchAllNotes();
        String[] from = new String[]{TasksAdapter.KEY_TITLE};
        int[] to = new int[]{R.id.text1};

        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(this, R.layout.tasks_row, notesCursor, from, to, 0);
        listView.setAdapter(notes);
    }
}

