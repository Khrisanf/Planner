package com.example.myapplication2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {SaveTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SaveTaskDao saveTaskDao();
}
