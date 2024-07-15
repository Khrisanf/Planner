package com.example.myapplication2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;

public class TaskManager {

    private final Context context;
    private final LinearLayout container;

    public TaskManager(Context context, LinearLayout container) {
        this.context = context;
        this.container = container;
    }

    public void addTask(String title, String priority, String category, String description, int color) {
        View newTaskView = LayoutInflater.from(context).inflate(R.layout.task_item, container, false);

        Button taskButton = newTaskView.findViewById(R.id.taskButton);
        taskButton.setText(title);
        taskButton.setBackgroundColor(color);

        TaskData taskData = new TaskData(title, priority, category, description, color);
        taskButton.setTag(taskData);

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskData taskData = (TaskData) taskButton.getTag();
                showEditTaskDialog(taskButton, taskData);
            }
        });

        container.addView(newTaskView);
    }

    private void showEditTaskDialog(Button taskButton, TaskData taskData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_element, null);

        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        EditText editDescription = dialogView.findViewById(R.id.editDescription);
        Spinner editPrioritySpinner = dialogView.findViewById(R.id.editPrioritySpinner);
        Spinner editCategorySpinner = dialogView.findViewById(R.id.editCategorySpinner);
        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        editTitle.setText(taskData.title);
        editDescription.setText(taskData.description);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(context,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPrioritySpinner.setAdapter(priorityAdapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(context,
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCategorySpinner.setAdapter(categoryAdapter);

        int priorityPosition = priorityAdapter.getPosition(taskData.priority);
        int categoryPosition = categoryAdapter.getPosition(taskData.category);
        editPrioritySpinner.setSelection(priorityPosition);
        editCategorySpinner.setSelection(categoryPosition);

        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(v -> {
            taskData.title = editTitle.getText().toString();
            taskData.description = editDescription.getText().toString();
            taskData.priority = editPrioritySpinner.getSelectedItem().toString();
            taskData.category = editCategorySpinner.getSelectedItem().toString();
            taskData.color = getPriorityColor(taskData.priority);

            taskButton.setText(taskData.title);
            taskButton.setBackgroundColor(taskData.color);
            taskButton.setTag(taskData);

            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        deleteButton.setOnClickListener(v -> {
            container.removeView((View) taskButton.getParent());
            dialog.dismiss();
        });

        dialog.show();
    }

    private int getPriorityColor(String priority) {
        switch (priority) {
            case "Очень высокий":
                return Color.parseColor("#59080E"); // black-red
            case "Высокий":
                return Color.parseColor("#AA313A"); // red
            case "Средний":
                return Color.parseColor("#FA9F4C"); // orange
            case "Низкий":
                return Color.parseColor("#8EB49E"); // green
            default:
                return Color.parseColor("#D7D7E4"); // grey
        }
    }
}
