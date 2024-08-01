package com.example.myapplication2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_table")
public class SaveTask {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskName;
    private String priority;
    private String category;
    private String description;
    private int color;

    public SaveTask(String taskName, String priority, String category, String description, int color) {
        this.taskName = taskName;
        this.priority = priority;
        this.category = category;
        this.description = description;
        this.color = color;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
