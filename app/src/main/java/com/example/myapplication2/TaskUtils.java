package com.example.myapplication2;

import android.graphics.Color;

public class TaskUtils {
    public static int getPriorityColor(String priority) {
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
