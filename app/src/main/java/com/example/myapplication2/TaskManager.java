package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskManager {

    private final Context context;
    private final LinearLayout container;
    private final List<View> taskViews = new ArrayList<>();

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
                TaskData data = (TaskData) taskButton.getTag();
                showEditTaskDialog(taskButton, data);
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(2, 2, 2, 2);
        newTaskView.setLayoutParams(params);

        taskViews.add(newTaskView);
        container.addView(newTaskView);
    }

    public void searchTasks(String query) {
        Pattern pattern = Pattern.compile(Pattern.quote(query), Pattern.CASE_INSENSITIVE);
        for (View taskView : taskViews) {
            TaskData taskData = (TaskData) taskView.findViewById(R.id.taskButton).getTag();
            Matcher titleMatcher = pattern.matcher(taskData.title);
            Matcher descriptionMatcher = pattern.matcher(taskData.description);
            if (titleMatcher.find() || descriptionMatcher.find()) {
                taskView.setVisibility(View.VISIBLE);
            } else {
                taskView.setVisibility(View.GONE);
            }
        }
    }

    // Метод для сброса фильтра поиска
    public void resetSearch() {
        for (View taskView : taskViews) {
            taskView.setVisibility(View.VISIBLE);
        }
    }

    // Метод для обновления поиска
    public void updateSearch(String query) {
        searchTasks(query);
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
                taskViews.remove(child);
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
