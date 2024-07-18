package com.example.myapplication2;

public class TaskData {
    public String title;
    public String priority;
    public String category;
    public String description;
    public int color;

    public TaskData(String title, String priority, String category, String description, int color) {
        this.title = title;
        this.priority = priority;
        this.category = category;
        this.description = description;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskData taskData = (TaskData) obj;
        return color == taskData.color &&
                title.equals(taskData.title) &&
                priority.equals(taskData.priority) &&
                category.equals(taskData.category) &&
                description.equals(taskData.description);
    }
}
