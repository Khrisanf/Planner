package com.example.myapplication2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TaskDialogFragment.TaskDialogListener {

    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText searchInput = findViewById(R.id.searchEditText); // Обновленный ID

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Не используем, но метод нужно переопределить
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    taskManager.resetSearch(); // Сброс фильтра, если текстовое поле пустое
                } else {
                    taskManager.updateSearch(s.toString()); // Обновление поиска с текущим текстом
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Не используем, но метод нужно переопределить
            }
        });

        LinearLayout container = findViewById(R.id.container);
        taskManager = new TaskManager(this, container);

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
