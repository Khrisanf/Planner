package com.example.myapplication2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class SaveTask {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String priority;

}
