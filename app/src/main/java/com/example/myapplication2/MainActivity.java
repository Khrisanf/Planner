package com.example.myapplication2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements TaskDialogFragment.TaskDialogListener {

    private LinearLayout container;
    private int elementCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton buttonAdd = findViewById(R.id.floatingActionButton);
        container = findViewById(R.id.container);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDialogFragment taskDialog = new TaskDialogFragment();
                taskDialog.show(getSupportFragmentManager(), "taskDialog");
            }
        });
    }

    @Override
    public void onTaskSaved(String title, String priority, String category, int color, String description) {
        // Логика обработки сохраненной задачи
        createNewTaskButton(title, priority, category, color, description);
    }

    private void createNewTaskButton(String title, String priority, String category, int color, String description) {
        View newTaskView = getLayoutInflater().inflate(R.layout.task_item, container, false);

        Button taskButton = newTaskView.findViewById(R.id.taskButton);
        taskButton.setText(title);
        taskButton.setBackgroundColor(color);

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showElementDialog(newTaskView, title, priority, category, description, color);
            }
        });

        container.addView(newTaskView);
    }

    private void showElementDialog(View taskView, String title, String priority, String category, String description, int color) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_element, null);

        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        EditText editDescription = dialogView.findViewById(R.id.editDescription);
        Spinner editPrioritySpinner = dialogView.findViewById(R.id.editPrioritySpinner);
        Spinner editCategorySpinner = dialogView.findViewById(R.id.editCategorySpinner);
        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        editTitle.setText(title);
        editDescription.setText(description);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPrioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategorySpinner.setAdapter(categoryAdapter);

        // Set initial selection for priority and category spinners
        int priorityPosition = priorityAdapter.getPosition(priority);
        int categoryPosition = categoryAdapter.getPosition(category);
        editPrioritySpinner.setSelection(priorityPosition);
        editCategorySpinner.setSelection(categoryPosition);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        Button taskButton = taskView.findViewById(R.id.taskButton);

        saveButton.setOnClickListener(v -> {
            taskButton.setText(editTitle.getText().toString());
            taskButton.setBackgroundColor(color);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getX() - e1.getX() > 100) {
                    if (editTitle.getText().toString().equals(title) &&
                            editDescription.getText().toString().equals(description)) {
                        dialog.dismiss();
                    } else {
                        new AlertDialog.Builder(MainActivity.this)
                                .setMessage("Сохранить изменения?")
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        taskButton.setText(editTitle.getText().toString());
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                    return true;
                }
                return false;
            }
        });

        dialogView.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));

        deleteButton.setOnClickListener(v -> {
            container.removeView(taskView);
            dialog.dismiss();
        });

        dialog.show();
    }
}
