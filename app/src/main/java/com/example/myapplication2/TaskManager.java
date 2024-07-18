package com.example.myapplication2;

import android.content.Context;
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

        // Устанавливаем отступы программно для всех сторон
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(2, 2, 2, 2);  // Отступы в 2dp для всех сторон
        newTaskView.setLayoutParams(params);

        container.addView(newTaskView);
    }
    
    public void updateTask(TaskData taskData) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            Button taskButton = child.findViewById(R.id.taskButton);
            if (taskButton != null && taskData.equals(taskButton.getTag())) {
                taskButton.setText(taskData.title);
                taskButton.setBackgroundColor(taskData.color);
                taskButton.setTag(taskData);
                break;
            }
        }
    }

    public void removeTask(TaskData taskData) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            Button taskButton = child.findViewById(R.id.taskButton);
            if (taskButton != null && taskData.equals(taskButton.getTag())) {
                container.removeView(child);
                break;
            }
        }
    }

    private void showEditTaskDialog(Button taskButton, TaskData taskData) {
        TaskDialogFragment dialogFragment = new TaskDialogFragment();
        dialogFragment.setTaskData(taskData);
        dialogFragment.show(((MainActivity) context).getSupportFragmentManager(), "taskDialog");
    }
}
