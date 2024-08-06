package com.example.myapplication2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements TaskDialogFragment.TaskDialogListener, FilterDialogFragment.FilterDialogListener {

    private TaskManager taskManager;
    private String currentCategoryFilter = "Все";
    private String currentPriorityFilter = "Все";
    private String currentSearchQuery = "";
    private SaveTaskViewModel saveTaskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveTaskViewModel = new ViewModelProvider(this).get(SaveTaskViewModel.class);

        EditText searchInput = findViewById(R.id.searchEditText);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearchQuery = s.toString();
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        LinearLayout container = findViewById(R.id.container);
        taskManager = new TaskManager(this, container);

        // Подписка на изменения данных в базе данных
        saveTaskViewModel.getAllTasks().observe(this, tasks -> {
            taskManager.setTasks(tasks);
            applyFilters();
        });

        FloatingActionButton buttonAdd = findViewById(R.id.floatingActionButton);
        buttonAdd.setOnClickListener(v -> {
            TaskDialogFragment taskDialog = new TaskDialogFragment();
            taskDialog.show(getSupportFragmentManager(), "taskDialog");
        });

        FloatingActionButton buttonSort = findViewById(R.id.sort_button);
        buttonSort.setOnClickListener(v -> {
            FilterDialogFragment filterDialog = new FilterDialogFragment();
            filterDialog.setFilterDialogListener(this);
            filterDialog.show(getSupportFragmentManager(), "filterDialog");
        });
    }

    @Override
    public void onTaskSaved(TaskData taskData, boolean isEditing) {
        SaveTask newTask = new SaveTask(taskData.title, taskData.priority, taskData.category, taskData.description, taskData.color);
        if (isEditing) {
            newTask.setId(taskData.id); // Установка ID существующей задачи для обновления
            taskManager.updateTask(taskData);
            saveTaskViewModel.update(newTask);
        } else {
            // Передаем id как первый параметр
            taskManager.addTask(taskData.id, taskData.title, taskData.priority, taskData.category, taskData.description, taskData.color);
            saveTaskViewModel.insert(newTask);
        }
        applyFilters();
    }


    @Override
    public void onApplyFilters(String category, String priority) {
        currentCategoryFilter = category;
        currentPriorityFilter = priority;
        applyFilters();
    }

    @Override
    public void onResetFilters() {
        currentCategoryFilter = "Все";
        currentPriorityFilter = "Все";
        applyFilters();
    }

    private void applyFilters() {
        taskManager.filterTasks(currentSearchQuery, currentCategoryFilter, currentPriorityFilter);
    }
}
