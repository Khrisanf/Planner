package com.example.myapplication2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SaveTaskDao {
    @Insert
    void insert(SaveTask task);

    @Update
    void update(SaveTask task);

    @Delete
    void delete(SaveTask task);

    @Query("SELECT * FROM SaveTask")
    List<SaveTask> getAllTasks();
}