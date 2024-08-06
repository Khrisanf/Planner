package com.example.myapplication2;

public class TaskData {
    public int id; // Добавлено поле ID
    public String title;
    public String priority;
    public String category;
    public String description;
    public int color;

    // Конструктор с ID для инициализации при редактировании
    public TaskData(int id, String title, String priority, String category, String description, int color) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.category = category;
        this.description = description;
        this.color = color;
    }

    // Конструктор без ID для создания новой задачи
    public TaskData(String title, String priority, String category, String description, int color) {
        this(0, title, priority, category, description, color); // ID будет установлен в 0 для новых задач
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TaskData taskData = (TaskData) obj;
        return id == taskData.id && // Сравнение ID
                color == taskData.color &&
                title.equals(taskData.title) &&
                priority.equals(taskData.priority) &&
                category.equals(taskData.category) &&
                description.equals(taskData.description);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
