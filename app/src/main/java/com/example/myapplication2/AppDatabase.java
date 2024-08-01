package com.example.myapplication2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Укажите сущности, которые входят в вашу базу данных, и версию базы данных
@Database(entities = {SaveTask.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // Метод для получения DAO
    public abstract SaveTaskDao saveTaskDao();
}
