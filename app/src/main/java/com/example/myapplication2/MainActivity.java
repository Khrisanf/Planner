package com.example.myapplication2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TaskDialogFragment.TaskDialogListener {

    private TaskManager taskManager;
    private EditText searchEditText;
    private Button searchButton;
    private Button clearSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout container = findViewById(R.id.container);
        taskManager = new TaskManager(this, container);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        clearSearchButton = findViewById(R.id.clearSearchButton);

        searchButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString();
            taskManager.searchTasks(query);
        });

        clearSearchButton.setOnClickListener(v -> {
            searchEditText.setText("");
            taskManager.clearSearch();
        });

        FloatingActionButton buttonAdd = findViewById(R.id.floatingActionButton);
        buttonAdd.setOnClickListener(v -> {
            TaskDialogFragment taskDialog = new TaskDialogFragment();
            taskDialog.show(getSupportFragmentManager(), "taskDialog");
        });
    }

    @Override
    public void onTaskSaved(TaskData taskData, boolean isEditing) {
        if (isEditing) {
            taskManager.updateTask(taskData);
        } else {
            taskManager.addTask(taskData.title, taskData.priority, taskData.category, taskData.description, taskData.color);
        }
    }
}
